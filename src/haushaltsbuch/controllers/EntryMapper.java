package haushaltsbuch.controllers;

import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import haushaltsbuch.Entry;

public class EntryMapper
{
  public Entry map(HttpServletRequest request)
  {
    return new Entry()
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
        String paymentType = request.getParameter("paymentType");
        System.err.println("Inserting entry with PT " + paymentType + " with encoding " + request.getCharacterEncoding());
        return paymentType;
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
          return BigDecimal.ZERO;
        else
          return new BigDecimal(value);
      }
    };
  }
}
