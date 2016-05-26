package test.integration.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;
import test.helpers.TestEntry;

public class RepositoryAllTest extends RepositoryTest
{
  private TestEntry _testEntry;

  @Before
  public void setUp() throws Exception
  {
    super.setUp();
    _testEntry = new TestEntry();
  }

  @Test
  public void testEmpty()
  {
    List<Entry> all = getRepository().all();

    assertNotNull(all);
    assertFalse(all.isEmpty());
    assertEquals("must only contain the opening statement", 1, all.size());
    assertEquals(BigDecimal.ZERO, all.get(0).getValue());
  }

  @Test
  public void testNonEmpty() throws InsertException
  {

    getRepository().insert(_testEntry);
    List<Entry> all = getRepository().all();

    assertEquals("must contain one more after the opening statement", 2, all.size());
    assertEquals(_testEntry.getValue(), all.get(1).getValue());
  }
}
