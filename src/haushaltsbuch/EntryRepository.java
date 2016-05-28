package haushaltsbuch;

import java.util.List;

public interface EntryRepository
{
  List<Entry> all();

  List<Entry> by_category(String category);

  List<String> categories();

  Entry delete(String parameter) throws DeleteException;

  String insert(Entry entry) throws InsertException;

  Entry lookup(String id) throws LookupException;
}
