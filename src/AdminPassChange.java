import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class AdminPassChange
 */
@WebServlet("/AdminPassChange")
public class AdminPassChange extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPassChange() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		Methods methods = new Methods();
		
		String newpass = request.getParameter("newpass");
		String repeatnewpass = request.getParameter("repeatnewpass");
		
		int idn = (int) request.getSession().getAttribute("adminLog");
		
		methods.changePass(idn, "adminDetailsid", newpass, repeatnewpass, "adminPass", "/jobcard/homeadmin.jsp",request, response);
		
	}

}
