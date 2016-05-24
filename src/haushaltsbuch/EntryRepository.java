package haushaltsbuch;

import java.util.List;

public interface EntryRepository
{
  void close();

  Entry delete(String parameter) throws DeleteException;

  Entry find(String id) throws FindException;

  List<Entry> getAll();

  String insert(Entry entry) throws InsertException;
}
