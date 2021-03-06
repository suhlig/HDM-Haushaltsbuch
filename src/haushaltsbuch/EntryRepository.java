package haushaltsbuch;

import java.util.List;

public interface EntryRepository
{
  Entry delete(String parameter) throws DeleteException;

  Entry lookup(String id) throws LookupException;

  List<Entry> all();

  String insert(Entry entry) throws InsertException;
}
