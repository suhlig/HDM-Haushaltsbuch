package haushaltsbuch.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;
import haushaltsbuch.InsertException;

@WebServlet("/insert")
public class InsertController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = produce(request);
    request.setAttribute("entry", entry);
    request.getRequestDispatcher("WEB-INF/jsp/add.jsp").include(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = produce(request);
    request.setAttribute("entry", entry);

    try
    {
      String id = getRepository().insert(entry);

      request.setAttribute("id", id);
      request.setAttribute("message", "Successfully inserted " + entry);
      request.getRequestDispatcher("WEB-INF/jsp/show.jsp").include(request, response);
    }
    catch (InsertException e)
    {
      e.printStackTrace(System.err);
      request.setAttribute("error", "Fehler beim Anlegen: " + e.getMessage());
      request.getRequestDispatcher("WEB-INF/jsp/add.jsp").include(request, response);
    }
  }

  private Entry produce(HttpServletRequest request)
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
        return null; // mapper will set this upon insert
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
