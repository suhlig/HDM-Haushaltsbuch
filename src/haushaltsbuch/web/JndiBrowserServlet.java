package haushaltsbuch.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jndi")
public class JndiBrowserServlet extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    PrintWriter writer = response.getWriter();
    writer.println("<pre>");

    try
    {
      Context ctx = new InitialContext();
      printContext(ctx, "", writer);
      ctx.close();
    }
    catch (NamingException e)
    {
      writer.println(e.getMessage());
    }

    writer.println("</pre>");
  }

  private String indent(int depth)
  {
    StringBuffer result = new StringBuffer(depth);

    for (int i = 0; i < depth; i++)
      result.append(" ");

    return result.toString();
  }

  private void printContext(Context ctx, String name, PrintWriter writer)
  {
    printContext(ctx, name, writer, 0);
  }

  private void printContext(Context ctx, String name, PrintWriter writer, int depth)
  {
    try
    {
      NamingEnumeration<Binding> en = ctx.listBindings("");

      while (en != null && en.hasMoreElements())
      {
        Binding binding = en.next();
        String path = name + (name.length() > 0 ? "/" : "") + binding.getName();
        Object namedObject = binding.getObject();

        if (namedObject instanceof Context)
          printContext((Context) namedObject, path, writer, depth + 1);
        else
        {
          writer.println(path);
          writer.println(indent(depth) + namedObject.toString() + " (" + binding.getClassName() + ")");
        }
      }
    }
    catch (NamingException e)
    {
      writer.println(e.getMessage());
    }
  }
}
