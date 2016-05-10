package haushaltsbuch;

import java.util.ArrayList;
import java.util.List;

public class TransientRepository implements Repository {

	@Override
	public List<Entry> getEntries() {
		ArrayList<Entry> entries = new ArrayList<Entry>();
		
		return entries;
	}
}
