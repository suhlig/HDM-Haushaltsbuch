package haushaltsbuch;

import java.util.List;

public interface EntryRepository
{
  String CTX_ATTR_NAME = "repository";

  void close();

  Entry delete(String parameter) throws DeleteException;

  Entry find(String id);

  List<Entry> getAll();

  String insert(Entry entry) throws InsertException;
}
