package haushaltsbuch.web;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MenuInitializer implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent event)
  {
    // this space intentionally left blank
  }

  public void contextInitialized(ServletContextEvent event)
  {
    event.getServletContext().setAttribute("menu", getMenu());
  }

  private Map<String, String> getMenu()
  {
    Map<String, String> menu = new LinkedHashMap<String, String>();

    menu.put("Home", ".");
    menu.put("Einträge", "entries/all");
    menu.put("Eintrag nachschlagen", "entries/lookup");
    menu.put("Neuer Eintrag…", "entries/new");

    return menu;
  }
}
