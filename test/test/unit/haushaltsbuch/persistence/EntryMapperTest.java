package test.unit.haushaltsbuch.persistence;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.AdditionalMatchers;
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
  public void testGeneratesNewID() throws SQLException
  {
    PreparedStatement stmt = mock(PreparedStatement.class);

    _subject.map(_testEntry, stmt);

    // id must be different from the supplied one because it is generated
    // by the mapper
    verify(stmt).setString(eq(1), AdditionalMatchers.not(eq(_testEntry.getId())));
    verify(stmt).setString(eq(1), anyString());

    // the rest is a 1:1 mapping
    verify(stmt).setString(eq(2), eq(_testEntry.getSrcDst()));
    verify(stmt).setString(eq(3), eq(_testEntry.getDescription()));
    verify(stmt).setBigDecimal(eq(4), eq(_testEntry.getValue()));
    verify(stmt).setString(eq(5), eq(_testEntry.getCategory()));
    verify(stmt).setString(eq(6), eq(_testEntry.getPaymentType()));
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

    // Since Entry is an entity, equals only tests for the id. Here we also want to ensure
    // that all attributes were assigned correctly:
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
