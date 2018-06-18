

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FieldEngineerLogin
 */
@WebServlet("/FieldEngineerLogin")
public class FieldEngineerLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FieldEngineerLogin() {
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
		
		String useremail = request.getParameter("useremail");
		String password = request.getParameter("password");
		
		
		if ((useremail == null || useremail.isEmpty()) || (password == null || password.isEmpty()))
		{
			
			
			request.getSession().setAttribute("nullError", "Please enter your email and password");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			
			int fieldengineerLog = methods.field_login("/jobcard/homefieldengineer.jsp", "/jobcard/fieldengineerPassChange.jsp" ,useremail, password, request, response);
			
			request.getSession(true).setAttribute("fieldengineerLog", fieldengineerLog);
			
			methods.getFieldEngineerDetails(fieldengineerLog, request, response);
			
			
		}
	}

}
