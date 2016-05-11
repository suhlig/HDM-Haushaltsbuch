package haushaltsbuch;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class JdbcRepository implements EntryRepository {
	private Connection _connection;
	private EntryMapper _entryMapper;
	private static final String TABLE_NAME = "entries";

	public JdbcRepository(String url, String user, String password) throws ClassNotFoundException, SQLException {
		_entryMapper = new EntryMapper();
		_connection = connect(url, user, password);

		if (!isTableExisting()) {
			createTable();
			insertOpeningBalance();
		}
	}

	private void insertOpeningBalance() throws SQLException {
		insert(new Entry() {
			@Override
			public String getId() {
				return UUID.randomUUID().toString();
			}

			@Override
			public Date getEntryDate() {
				return null;
			}

			@Override
			public String getSrcDst() {
				return "";
			}

			@Override
			public String getDescription() {
				return "Kontoeröffnung";
			}

			@Override
			public BigDecimal getValue() {
				return BigDecimal.ZERO;
			}

			@Override
			public String getCategory() {
				return "System";
			}

			@Override
			public String getPaymentType() {
				return "System";
			}
		});
	}

	private static Connection connect(String url, String user, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(url, user, password);

		System.out.println("Successfully connected to " + connection.getMetaData().getDatabaseProductName() + " v."
				+ connection.getMetaData().getDatabaseProductVersion());

		return connection;
	}

	public void insert(Entry entry) throws SQLException {
		PreparedStatement stmt = null;
		try {
			stmt = _connection.prepareStatement(MessageFormat.format(
					"INSERT INTO {0} (id, src_dest, description, value, category, payment_type) VALUES (?, ?, ?, ?, ?, ?)",
					TABLE_NAME));

			_entryMapper.map(stmt, entry);

			int count = stmt.executeUpdate();

			System.out.println("Successfully inserted " + count + " record");
		} finally {
			tryClose(stmt);
		}
	}

	private void createTable() throws SQLException {
		executeUpdate(MessageFormat.format(
				"CREATE TABLE {0} (id character varying(36) NOT NULL, created_at timestamp without time zone DEFAULT now() NOT NULL, src_dest character varying(255) NOT NULL, description character varying(255) NOT NULL, value bigint NOT NULL, category character varying(255) NOT NULL, payment_type character varying(255) NOT NULL);",
				TABLE_NAME));

		executeUpdate(
				MessageFormat.format("ALTER TABLE ONLY {0}  ADD CONSTRAINT {0}_pkey PRIMARY KEY (id);", TABLE_NAME));
	}

	private void executeUpdate(String sql) throws SQLException {
		Statement stmt = null;

		try {
			stmt = _connection.createStatement();
			stmt.executeUpdate(sql);
		} finally {
			tryClose(stmt);
		}
	}

	private boolean isTableExisting() throws SQLException {
		return _connection.getMetaData().getTables(null, null, TABLE_NAME, null).next();
	}

	@Override
	public List<Entry> getAll() {
		ArrayList<Entry> entries = new ArrayList<Entry>();

		Statement st = null;
		ResultSet rs = null;

		try {
			st = _connection.createStatement();
			rs = st.executeQuery(MessageFormat.format(
					"SELECT id, created_at, src_dest, description, value, category, payment_type FROM {0}",
					TABLE_NAME));

			while (rs.next()) {
				entries.add(_entryMapper.map(rs));
			}
		} catch (SQLException e) {
			System.err.println("Error fetching rows of " + TABLE_NAME);
			e.printStackTrace(System.err);
		} finally {
			tryClose(rs);
			tryClose(st);
		}

		return entries;
	}

	private void tryClose(Statement st) {
		if (null != st)
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
	}

	private void tryClose(ResultSet rs) {
		if (null != rs)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
	}
}
