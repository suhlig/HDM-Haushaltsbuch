package test.unit.haushaltsbuch.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import haushaltsbuch.Entry;
import haushaltsbuch.web.EntryMapper;
import test.helpers.TestEntry;

public class EntryMapperTest
{
  private EntryMapper _subject;
  private TestEntry _testEntry;

  @Before
  public void setUp() throws Exception
  {
    _subject = new EntryMapper();
    _testEntry = new TestEntry();
  }

  @Test
  public void testEntryDateNotMapped()
  {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter(eq("entryDate"))).thenReturn(Date.from(Instant.now()).toString());

    Entry mapped = _subject.map(request);

    // Even if present as parameters, the entry date must not be set
    // The repository will override it anyway, but there is no point in setting it here
    // so we ensure it's null.
    assertEquals(null, mapped.getEntryDate());
  }

  @Test
  public void testIdNotMapped()
  {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter(eq("id"))).thenReturn(_testEntry.getId());

    Entry mapped = _subject.map(request);

    // Even if present as parameters, the id must not be set
    // The repository will override it anyway, but there is no point in setting it here
    // so we ensure it's null.
    assertEquals(null, mapped.getId());
  }

  @Test
  public void testMap()
  {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    when(request.getParameter(eq("category"))).thenReturn(_testEntry.getCategory());
    when(request.getParameter(eq("description"))).thenReturn(_testEntry.getDescription());
    when(request.getParameter(eq("paymentType"))).thenReturn(_testEntry.getPaymentType());
    when(request.getParameter(eq("srcDst"))).thenReturn(_testEntry.getSrcDst());
    when(request.getParameter(eq("value"))).thenReturn(_testEntry.getValue().toString());

    Entry mapped = _subject.map(request);

    assertEquals(_testEntry.getCategory(), mapped.getCategory());
    assertEquals(_testEntry.getDescription(), mapped.getDescription());
    assertEquals(_testEntry.getPaymentType(), mapped.getPaymentType());
    assertEquals(_testEntry.getSrcDst(), mapped.getSrcDst());
    assertEquals(_testEntry.getValue(), mapped.getValue());
  }

  @Test
  public void testValueEmpty()
  {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter(eq("value"))).thenReturn("");

    Entry mapped = _subject.map(request);

    assertEquals(BigDecimal.ZERO, mapped.getValue());
  }

  @Test
  public void testValueNull()
  {
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    when(request.getParameter(eq("value"))).thenReturn(null);

    Entry mapped = _subject.map(request);

    assertEquals(BigDecimal.ZERO, mapped.getValue());
  }
}
