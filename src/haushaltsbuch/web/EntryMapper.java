package haushaltsbuch.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import haushaltsbuch.Entry;
import haushaltsbuch.web.controllers.Candidate;

public class EntryMapper
{
  public Candidate<Entry> map(HttpServletRequest request)
  {
    return new Candidate<Entry>()
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
        return request.getParameter("paymentType");
      }

      @Override
      public String getSrcDst()
      {
        return request.getParameter("srcDst");
      }

      @Override
      public Map<String, String> getValidationErrors()
      {
        Map<String, String> errors = new HashMap<String, String>();

        if (null == getSrcDst() || getSrcDst().isEmpty())
          errors.put("srcDst", "Quelle oder Ziel der Buchung muß angegeben werden");

        return errors;
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

      @Override
      public boolean isValid()
      {
        return getValidationErrors().isEmpty();
      }
    };
  }
}
