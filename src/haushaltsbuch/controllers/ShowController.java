package haushaltsbuch.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import haushaltsbuch.Entry;

@WebServlet("/show")
public class ShowController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Entry entry = getRepository().find(request.getQueryString());
    request.setAttribute("entry", entry);
    response.setContentType("text/html; charset=utf-8");
    request.getRequestDispatcher("WEB-INF/jsp/show.jsp").include(request, response);
  }
}
