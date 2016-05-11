package haushaltsbuch.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;

@WebServlet("/insert")
public class InsertController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = getEntry(request);
    request.setAttribute("entry", entry);

    response.setContentType("text/html; charset=utf-8");
    request.getRequestDispatcher("WEB-INF/jsp/add.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = getEntry(request);

    try
    {
      getRepository().insert(entry);
      request.setAttribute("entry", entry);
      request.setAttribute("message", "Successfully inserted " + entry);
    }
    catch (SQLException e)
    {
      e.printStackTrace(System.err);
      request.setAttribute("message", "Error inserting entry: " + e.getMessage());
    }

    response.setContentType("text/html; charset=utf-8");
    request.getRequestDispatcher("WEB-INF/jsp/show.jsp").include(request, response);
  }

  private Entry getEntry(HttpServletRequest request)
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
        return UUID.randomUUID().toString();
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
          return BigDecimal.ZERO;
        else
          return new BigDecimal(value);
      }
    };
  }
}
