package test.unit.haushaltsbuch.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import haushaltsbuch.Entry;
import haushaltsbuch.persistence.EntryMapper;
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
  public void testMapResultSet() throws SQLException
  {
    ResultSet rs = mock(ResultSet.class);
    Timestamp created_at = Timestamp.from(Instant.now());

    when(rs.getString("id")).thenReturn(_testEntry.getId());
    when(rs.getTimestamp("created_at")).thenReturn(created_at);
    when(rs.getString("src_dest")).thenReturn(_testEntry.getSrcDst());
    when(rs.getString("description")).thenReturn(_testEntry.getDescription());
    when(rs.getBigDecimal("value")).thenReturn(_testEntry.getValue());
    when(rs.getString("category")).thenReturn(_testEntry.getCategory());
    when(rs.getString("payment_type")).thenReturn(_testEntry.getPaymentType());

    Entry mapped = _subject.map(rs);

    assertEquals(_testEntry, mapped);

    // Since Entry is an entity, equals only tests for the id. But we also want to ensure
    // that all attributes were assigned correctly, hence these additional assertions.
    assertEquals(_testEntry.getId(), mapped.getId());
    assertEquals(_testEntry.getSrcDst(), mapped.getSrcDst());
    assertEquals(_testEntry.getDescription(), mapped.getDescription());
    assertEquals(_testEntry.getValue(), mapped.getValue());
    assertEquals(_testEntry.getCategory(), mapped.getCategory());
    assertEquals(_testEntry.getPaymentType(), mapped.getPaymentType());

    // This one is special because created_at is a database-generated field
    assertEquals(created_at, mapped.getEntryDate());
  }
}
