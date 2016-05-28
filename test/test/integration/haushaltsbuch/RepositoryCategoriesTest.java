package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.InsertException;
import haushaltsbuch.LookupException;
import haushaltsbuch.persistence.JdbcRepository;
import test.helpers.TestEntry;

public class RepositoryCategoriesTest extends RepositoryTest
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
    insertWithCategory("category two");
    insertWithCategory("category three");
    assertEquals(3 + 1, _subject.all().size()); // +1 for the opening balance

    List<String> categories = _subject.categories();

    assertNotNull(categories);
    assertEquals(3 + 1, categories.size());

    assertTrue(categories.contains("category one"));
    assertTrue(categories.contains("category two"));
    assertTrue(categories.contains("category three"));
    assertFalse(categories.contains("category four"));
  }

  /** must not return a category more than once */
  @Test
  public void testDistinctOnly() throws InsertException, LookupException
  {
    insertWithCategory("category once");
    insertWithCategory("category twice");
    insertWithCategory("category twice");
    assertEquals(3 + 1, _subject.all().size());

    List<String> categories = _subject.categories();

    assertNotNull(categories);
    assertEquals(2 + 1, categories.size());

    assertTrue(categories.contains("category once"));
    assertTrue(categories.contains("category twice"));

  }

  /** the category of the opening balance should always be there */
  @Test
  public void testEmpty() throws InsertException
  {
    List<String> categories = _subject.categories();

    assertNotNull(categories);
    assertEquals(1, categories.size());
  }

  /** more often used categories should appear before less used */
  @Test
  public void testOrder() throws InsertException, LookupException
  {
    insertWithCategory("first place");
    insertWithCategory("first place");
    insertWithCategory("second place");
    insertWithCategory("first place");
    insertWithCategory("second place");
    insertWithCategory("third place");
    insertWithCategory("first place");
    insertWithCategory("second place");
    insertWithCategory("third place");

    List<String> categories = _subject.categories();
    assertEquals(9 + 1, _subject.all().size());

    assertEquals("first place", categories.get(0));
    assertEquals("second place", categories.get(1));
    assertEquals("third place", categories.get(2));

    // We don't care about the actual category of the opening balance, but want to be sure there is one.
    assertFalse(categories.get(3).isEmpty());
  }

  protected void insertWithCategory(String category) throws InsertException
  {
    TestEntry testEntry = new TestEntry();
    testEntry.setCategory(category);

    _subject.insert(testEntry);
  }
}
