package haushaltsbuch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRepository implements Repository {
	private Connection _conn;

	public JdbcRepository(String url, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		_conn = DriverManager.getConnection(url, user, password);
		
		System.out.println(
		    "Successfully connected to " 
		  + _conn.getMetaData().getDatabaseProductName() 
		  + " v." + _conn.getMetaData().getDatabaseProductVersion()
		);
	}

	@Override
	public List<Entry> getEntries() {
		ArrayList<Entry> entries = new ArrayList<Entry>();
		
		// TODO Fetch entries and map to Java objects
		
		return entries;
	}
}
