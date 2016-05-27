package haushaltsbuch.persistence;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import haushaltsbuch.Entry;

public class EntryMapper
{
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
