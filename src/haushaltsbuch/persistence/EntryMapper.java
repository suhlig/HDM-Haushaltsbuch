package haushaltsbuch.persistence;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import haushaltsbuch.Entry;

public class EntryMapper
{
  public String map(Entry entry, PreparedStatement stmt) throws SQLException
  {
    String id = UUID.randomUUID().toString();

    stmt.setString(1, id);
    stmt.setString(2, entry.getSrcDst());
    stmt.setString(3, entry.getDescription());
    stmt.setBigDecimal(4, entry.getValue());
    stmt.setString(5, entry.getCategory());
    stmt.setString(6, entry.getPaymentType());

    return id;
  }

  public Entry map(ResultSet rs) throws SQLException
  {
    String id = rs.getString("id");
    Date entry_date = rs.getTimestamp("created_at");
    String src_dest = rs.getString("src_dest");
    String description = rs.getString("description");
    BigDecimal value = rs.getBigDecimal("value");
    String category = rs.getString("category");
    String paymentType = rs.getString("payment_type");

    return new AbstractEntry()
    {
      @Override
      public String getCategory()
      {
        return category;
      }

      @Override
      public String getDescription()
      {
        return description;
      }

      @Override
      public Date getEntryDate()
      {
        return entry_date;
      }

      @Override
      public String getId()
      {
        return id;
      }

      @Override
      public String getPaymentType()
      {
        return paymentType;
      }

      @Override
      public String getSrcDst()
      {
        return src_dest;
      }

      @Override
      public BigDecimal getValue()
      {
        return value;
      }
    };
  }
}
