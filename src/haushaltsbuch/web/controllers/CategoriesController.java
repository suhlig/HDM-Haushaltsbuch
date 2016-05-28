package haushaltsbuch.web.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("categories/all")
public class CategoriesController extends BaseController
{
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    request.setAttribute("categories", getRepository().categories());
    setTitle(request, "Alle Kategorien");
    setView(request);
  }
}
