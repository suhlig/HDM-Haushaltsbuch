package haushaltsbuch;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DatabaseInitializer implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent ignored)  {
    }

    public void contextInitialized(ServletContextEvent event)  {
		try {
			Credentials credentials = new Credentials(System.getenv("VCAP_SERVICES"));
			JdbcRepository repository = new JdbcRepository(credentials.getURI(), credentials.getUser(), credentials.getPassword());
		    event.getServletContext().setAttribute(EntryRepository.CTX_ATTR_NAME, repository);
		} catch (Exception e) {
			System.err.println("Error initializing: ");
			e.printStackTrace(System.err);
		}
    }
}
