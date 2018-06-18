

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FieldEngineerPassChange
 */
@WebServlet("/FieldEngineerPassChange")
public class FieldEngineerPassChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FieldEngineerPassChange() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		Methods methods = new Methods();
		
		String newpass = request.getParameter("newpass");
		String repeatnewpass = request.getParameter("repeatnewpass");
		
		int idn = (int) request.getSession().getAttribute("fieldengineerLog");
		
		methods.changePass(idn, "fieldEngineerDetails_id", newpass, repeatnewpass, "fieldEngineerPass", "/jobcard/homefieldengineer.jsp",request, response);
		
	}

}
