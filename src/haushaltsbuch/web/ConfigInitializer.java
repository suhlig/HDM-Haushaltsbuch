package haushaltsbuch.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Makes configuration available to servlets and JSPs
 */
@WebListener
public class ConfigInitializer implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent event)
  {
    // this space intentionally left blank
  }

  public void contextInitialized(ServletContextEvent event)
  {
    event.getServletContext().setAttribute("SSE_URL", System.getenv("SSE_URL"));
  }
}
