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
    String _id = rs.getString("id");
    Date _entry_date = rs.getTimestamp("created_at");
    String _src_dest = rs.getString("src_dest");
    String _description = rs.getString("description");
    BigDecimal value = rs.getBigDecimal("value");
    String _category = rs.getString("category");
    String _paymentType = rs.getString("payment_type");

    return new Entry()
    {
      @Override
      public String getCategory()
      {
        return _category;
      }

      @Override
      public String getDescription()
      {
        return _description;
      }

      @Override
      public Date getEntryDate()
      {
        return _entry_date;
      }

      @Override
      public String getId()
      {
        return _id;
      }

      @Override
      public String getPaymentType()
      {
        return _paymentType;
      }

      @Override
      public String getSrcDst()
      {
        return _src_dest;
      }

      @Override
      public BigDecimal getValue()
      {
        return value;
      }
    };
  }
}
