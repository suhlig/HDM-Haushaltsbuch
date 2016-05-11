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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setAttribute("entries", getRepository().getAll());
	    response.setContentType("text/html; charset=utf-8");
	    request.getRequestDispatcher("WEB-INF/jsp/list.jsp").include(request, response);
	}

	private EntryRepository getRepository() throws ServletException {
		EntryRepository repository = (EntryRepository) getServletContext().getAttribute(EntryRepository.CTX_ATTR_NAME);

		if (null == repository)
			throw new ServletException("Unable to find the repository");

		return repository;
	}
}
