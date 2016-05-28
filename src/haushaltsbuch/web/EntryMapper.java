package haushaltsbuch.web;

import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import haushaltsbuch.Entry;

public class EntryMapper
{
  private class BlankEntry implements Entry
  {
    @Override
    public String getCategory()
    {
      return "";
    }

    @Override
    public String getDescription()
    {
      return "";
    }

    @Override
    public Date getEntryDate()
    {
      return null; // database will set this upon insert
    }

    @Override
    public String getId()
    {
      return null; // persistence layer will generate this upon insert
    }

    @Override
    public String getPaymentType()
    {
      return "";
    }

    @Override
    public String getSrcDst()
    {
      return "";
    }

    @Override
    public BigDecimal getValue()
    {
      return BigDecimal.ZERO;
    }
  }

  public Entry getBlankEntry()
  {
    return new BlankEntry();
  }

  public Entry map(HttpServletRequest request)
  {
    return new BlankEntry()
    {
      @Override
      public String getCategory()
      {
        return request.getParameter("category");
      }

      @Override
      public String getDescription()
      {
        return request.getParameter("description");
      }

      @Override
      public String getPaymentType()
      {
        return request.getParameter("paymentType");
      }

      @Override
      public String getSrcDst()
      {
        return request.getParameter("srcDst");
      }

      @Override
      public BigDecimal getValue()
      {
        String value = request.getParameter("value");

        if (null == value || value.isEmpty())
          return super.getValue();
        else
          return new BigDecimal(value);
      }
    };
  }
}
