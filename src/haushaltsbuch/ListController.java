package haushaltsbuch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for listing entries
 */
@WebServlet("/list")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Repository _repository;

	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			Credentials credentials = new Credentials(System.getenv("VCAP_SERVICES"));
			_repository = new JdbcRepository(credentials.getURI(), credentials.getUser(), credentials.getPassword());
		} catch (Exception e) {
			System.err.println("Error initializing: ");
			e.printStackTrace(System.err);
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setAttribute("entries", _repository.getEntries());
	    response.setContentType("text/html; charset=utf-8");
	    request.getRequestDispatcher("WEB-INF/jsp/list.jsp").include(request, response);
	}
}
