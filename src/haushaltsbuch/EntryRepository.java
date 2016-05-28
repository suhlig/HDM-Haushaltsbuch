package haushaltsbuch;

import java.util.List;

public interface EntryRepository
{
  List<Entry> all();

  List<String> categories();

  Entry delete(String parameter) throws DeleteException;

  String insert(Entry entry) throws InsertException;

  Entry lookup(String id) throws LookupException;
}
