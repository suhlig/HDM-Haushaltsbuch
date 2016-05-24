package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.EntryRepository;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestDatabase;
import test.helpers.TestEntry;

public class RepositoryListTest
{
  private EntryRepository _subject;
  private TestDatabase _database;
  private TestEntry _testEntry;

  @Before
  public void setUp() throws Exception
  {
    _database = new TestDatabase("jdbc:postgresql:///");
    _subject = new JdbcRepository(_database.getURL(), null, null);
    _testEntry = new TestEntry();
  }

  @After
  public void tearDown() throws Exception
  {
    _subject.close();
    _database.tearDown();
  }

  @Test
  public void testEmpty()
  {
    List<Entry> all = _subject.getAll();

    assertNotNull(all);
    assertFalse(all.isEmpty());
    assertEquals("must only contain the opening statement", 1, all.size());
    assertEquals(BigDecimal.ZERO, all.get(0).getValue());
  }

  @Test
  public void testNonEmpty() throws InsertException
  {
    _subject.insert(_testEntry);
    List<Entry> all = _subject.getAll();

    assertEquals("must contain one more after the opening statement", 2, all.size());
    assertEquals(_testEntry.getValue(), all.get(1).getValue());
  }
}
