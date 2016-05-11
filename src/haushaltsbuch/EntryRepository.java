package haushaltsbuch;

import java.util.List;

public interface EntryRepository {
	String CTX_ATTR_NAME = "repository";

	List<Entry> getAll();
}
