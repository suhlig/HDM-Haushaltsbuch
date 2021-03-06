package haushaltsbuch.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import haushaltsbuch.ArgumentException;
import haushaltsbuch.DeleteException;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;

public class JdbcRepository implements EntryRepository
{
  private interface Block
  {
    void call(Entry result, ResultSet rs) throws SQLException;
  }

  private final class NullBlock implements Block
  {
    @Override
    public void call(Entry result, ResultSet rs) throws SQLException
    {
      // nothing
    }
  }

  public static final String TABLE_NAME = "entries";

  private static final String CREATE_TABLE =
    // @formatter:off
		"CREATE TABLE {0} ("
  		+ "id uuid DEFAULT uuid_generate_v4() PRIMARY KEY, "
  		+ "created_at timestamp without time zone DEFAULT now() NOT NULL, "
  		+ "src_dest character varying(255) NOT NULL CHECK (char_length(src_dest) >= 3), "
  		+ "description character varying(255) NOT NULL CHECK (char_length(description) >= 3), "
  		+ "value bigint NOT NULL, "
  		+ "category character varying(255) NOT NULL, "
  		+ "payment_type character varying(255) NOT NULL CHECK (char_length(payment_type) >= 3)"
		+ ")";
		// @formatter:on

  private static final String CREATE_NOTIFIER =
    // @formatter:off
    "CREATE OR REPLACE FUNCTION entry_notifier() RETURNS trigger as $$"
    + "BEGIN"
    + "  PERFORM pg_notify(''entry_updates'', row_to_json(NEW)::text);"
    + "  RETURN NEW;"
    + "END;"
    + "$$ LANGUAGE plpgsql";
    // @formatter:on

  private static final String CREATE_TRIGGER =
    // @formatter:off
    "CREATE TRIGGER entry_upsert_trigger AFTER INSERT OR UPDATE ON {0} "
      + "  FOR EACH ROW EXECUTE PROCEDURE entry_notifier()";
    // @formatter:on

  private static final String SELECT_BY_ID = "SELECT id, created_at, src_dest, description, value, category, payment_type FROM {0} WHERE id=''{1}''";
  private static final String SELECT_ALL = "SELECT id, created_at, src_dest, description, value, category, payment_type FROM {0}";
  private static final String INSERT = "INSERT INTO {0} (src_dest, description, value, category, payment_type) VALUES (?, ?, ?, ?, ?)";

  private final Connection _connection;
  private final EntryMapper _entryMapper;

  public JdbcRepository(Connection connection) throws SQLException, InsertException
  {
    _connection = connection;
    _entryMapper = new EntryMapper();
    primeDatabase();
  }

  @Override
  public List<Entry> all()
  {
    ArrayList<Entry> result = new ArrayList<Entry>();

    Statement st = null;
    ResultSet rs = null;

    try
    {
      st = _connection.createStatement();
      rs = st.executeQuery(MessageFormat.format(SELECT_ALL, TABLE_NAME));

      while (rs.next())
        result.add(_entryMapper.map(rs));
    }
    catch (SQLException e)
    {
      System.err.println("Error fetching all rows of " + TABLE_NAME);
      e.printStackTrace(System.err);
    }
    finally
    {
      tryClose(rs);
      tryClose(st);
    }

    System.out.println(MessageFormat.format("Fetched all {0} entries", result.size()));

    return result;
  }

  @Override
  public Entry delete(String id) throws DeleteException
  {
    if (null == id || id.isEmpty())
      throw new ArgumentException("Identification missing");

    try
    {
      return find(id, new Block()
      {
        @Override
        public void call(Entry result, ResultSet rs) throws SQLException
        {
          if (result.getId().equals(id))
          {
            rs.deleteRow();
            System.out.println(MessageFormat.format("Deleted {0}", result.getId()));
          }
        }
      });
    }
    catch (LookupException e)
    {
      throw new DeleteException(id, e);
    }
  }

  @Override
  public String insert(Entry entry) throws InsertException
  {
    if (null == entry)
      throw new ArgumentException("Identification missing");

    final String id;
    PreparedStatement stmt = null;

    try
    {
      stmt = _connection.prepareStatement(MessageFormat.format(INSERT, TABLE_NAME), Statement.RETURN_GENERATED_KEYS);

      stmt.setString(1, entry.getSrcDst());
      stmt.setString(2, entry.getDescription());
      stmt.setBigDecimal(3, entry.getValue());
      stmt.setString(4, entry.getCategory());
      stmt.setString(5, entry.getPaymentType());

      stmt.executeUpdate();

      ResultSet generatedKeys = stmt.getGeneratedKeys();

      if (!generatedKeys.next())
        throw new InsertException(entry, new Exception("Could not read the auto-generated key"));
      else
      {
        id = generatedKeys.getString(1);
        System.out.println(MessageFormat.format("Inserted record with id = {0} ", id));

        return id;
      }
    }
    catch (SQLException e)
    {
      throw new InsertException(entry, e);
    }
    finally
    {
      tryClose(stmt);
    }
  }

  @Override
  public Entry lookup(String id) throws LookupException
  {
    if (null == id || id.isEmpty())
      throw new ArgumentException("Identification missing");

    Entry result = find(id, new NullBlock());

    if (null == result)
      System.err.println("Could not find an entry with id=" + id);
    else
      System.out.println("Found entry with id=" + result.getId());

    return result;
  }

  private void createNotifier() throws SQLException
  {
    executeUpdate(MessageFormat.format(CREATE_NOTIFIER, TABLE_NAME));
  }

  private void createTable() throws SQLException
  {
    executeUpdate("CREATE EXTENSION IF NOT EXISTS \"uuid-ossp\"");
    executeUpdate(MessageFormat.format(CREATE_TABLE, TABLE_NAME));
  }

  private void createTrigger() throws SQLException
  {
    executeUpdate(MessageFormat.format(CREATE_TRIGGER, TABLE_NAME));
  }

  private void executeUpdate(String sql) throws SQLException
  {
    Statement stmt = null;

    try
    {
      stmt = _connection.createStatement();
      stmt.executeUpdate(sql);
    }
    finally
    {
      tryClose(stmt);
    }
  }

  private Entry find(String id, Block block) throws LookupException
  {
    Statement st = null;
    ResultSet rs = null;

    try
    {
      st = _connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
      rs = st.executeQuery(MessageFormat.format(SELECT_BY_ID, TABLE_NAME, id));

      if (rs.next())
      {
        Entry result = _entryMapper.map(rs);

        block.call(result, rs);

        return result;
      }
    }
    catch (SQLException e)
    {
      System.err.println(MessageFormat.format("Error fetching row with id {0} from {1}", id, TABLE_NAME));
      e.printStackTrace(System.err);

      throw new LookupException(id, e);
    }
    finally
    {
      tryClose(rs);
      tryClose(st);
    }

    return null;
  }

  private void insertOpeningBalance() throws InsertException
  {
    insert(new Entry()
    {
      @Override
      public String getCategory()
      {
        return "System";
      }

      @Override
      public String getDescription()
      {
        return "Kontoeröffnung";
      }

      @Override
      public Date getEntryDate()
      {
        return null;
      }

      @Override
      public String getId()
      {
        return null;
      }

      @Override
      public String getPaymentType()
      {
        return "System";
      }

      @Override
      public String getSrcDst()
      {
        return "thin air";
      }

      @Override
      public BigDecimal getValue()
      {
        return BigDecimal.ZERO;
      }
    });
  }

  private boolean isTableExisting() throws SQLException
  {
    return _connection.getMetaData().getTables(null, null, TABLE_NAME, null).next();
  }

  private void primeDatabase() throws SQLException, InsertException
  {
    if (!isTableExisting())
    {
      createTable();
      createNotifier();
      createTrigger();
      insertOpeningBalance();
    }
  }

  private void tryClose(ResultSet rs)
  {
    if (null != rs)
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace(System.err);
      }
  }

  private void tryClose(Statement st)
  {
    if (null != st)
      try
      {
        st.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace(System.err);
      }
  }
}
