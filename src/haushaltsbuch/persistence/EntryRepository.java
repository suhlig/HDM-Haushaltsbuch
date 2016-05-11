package haushaltsbuch.persistence;

import java.sql.SQLException;
import java.util.List;
import haushaltsbuch.Entry;

public interface EntryRepository
{
  String CTX_ATTR_NAME = "repository";

  Entry delete(String parameter);

  Entry find(String id);

  List<Entry> getAll();

  void insert(Entry entry) throws SQLException;
}
