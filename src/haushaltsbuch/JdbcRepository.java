package haushaltsbuch;

import java.util.ArrayList;
import java.util.List;

public class JdbcRepository implements Repository {

	public JdbcRepository(String connectionString) {
		System.out.println("Not connecting to database yet: " + connectionString);
	}

	@Override
	public List<Entry> getEntries() {
		ArrayList<Entry> entries = new ArrayList<Entry>();
		
		return entries;
	}
}
