package test.unit.haushaltsbuch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.ElephantSqlConfig;
import haushaltsbuch.ElephantSqlConfig.ParseException;

public class ElephantSqlConfigTest
{
  // @formatter:off
   private final static String VCAP_SERVICES = "{"
     + "   \"elephantsql\": ["
     + "      {"
     + "         \"name\": \"elephantsql\","
     + "         \"label\": \"elephantsql\","
     + "         \"plan\": \"turtle\","
     + "         \"credentials\": {"
     + "            \"uri\": \"postgres://foo:bar@example.com:5432/everything\","
     + "            \"max_conns\": \"5\""
     + "         }"
     + "      }"
     + "   ]"
     + "}";
   // @formatter:on

  private ElephantSqlConfig _subject;

  @Before
  public void setup() throws ParseException
  {
    _subject = new ElephantSqlConfig(VCAP_SERVICES);
  }

  @Test
  public void testBogus()
  {
    try
    {
      new ElephantSqlConfig("this is not proper JSON");
      fail("Did not throw exception");
    }
    catch (ParseException e)
    {
      // ok
    }
  }

  @Test
  public void testEmpty()
  {
    try
    {
      new ElephantSqlConfig("");
      fail("Did not throw exception");
    }
    catch (ParseException e)
    {
      // ok
    }
  }

  @Test
  public void testGetPassword()
  {
    assertEquals("bar", _subject.getPassword());
  }

  @Test
  public void testGetURI()
  {
    assertEquals("jdbc:postgresql://example.com:5432/everything", _subject.getURI());
  }

  @Test
  public void testGetUser()
  {
    assertEquals("foo", _subject.getUser());
  }

  @Test
  public void testUDS() throws ParseException
  {
    ElephantSqlConfig credentials = new ElephantSqlConfig("{\"elephantsql\":[{\"credentials\":{\"uri\":\"postgres:///haushaltsbuch\"}}]}");

    assertEquals("jdbc:postgresql:///haushaltsbuch", credentials.getURI());
  }
}
