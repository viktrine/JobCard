import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminLogin
 */
@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLogin() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		Methods methods = new Methods();
		
		
		
		String useremail = request.getParameter("useremail");
		String password = request.getParameter("password");
		
		
		if ((useremail == null || useremail.isEmpty()) || (password == null || password.isEmpty()))
		{
			
			request.getSession().setAttribute("nullError", "Please enter your email and password");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			int adminLog = methods.Admin_login("/jobcard/homeadmin.jsp", "/jobcard/adminChangePass.jsp", useremail, password, request, response);
			
			request.getSession(true).setAttribute("adminLog", adminLog);
			
			methods.getAdminDetails(adminLog, request, response);
		}
			
	}
	

}
