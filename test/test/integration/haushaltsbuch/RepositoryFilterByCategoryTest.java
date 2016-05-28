package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestEntry;

public class RepositoryFilterByCategoryTest extends RepositoryTest
{
  private JdbcRepository _subject;

  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    _subject = getRepository();
  }

  @Test
  public void testBasics() throws InsertException
  {
    insertWithCategory("category one");
    insertWithCategory("category two-two");
    insertWithCategory("category two-two");
    insertWithCategory("category three");

    List<Entry> categories = _subject.by_category("category two-two");

    assertNotNull(categories);
    assertEquals(2, categories.size());

    for (Entry entry : categories)
      assertEquals("category two-two", entry.getCategory());
  }

  /** the category of the opening balance should always be there */
  @Test
  public void testSystemCategory()
  {
    List<Entry> categories = _subject.by_category("System");

    assertNotNull(categories);
    assertEquals(1, categories.size());
  }

  protected void insertWithCategory(String category) throws InsertException
  {
    TestEntry testEntry = new TestEntry();
    testEntry.setCategory(category);

    _subject.insert(testEntry);
  }
}
