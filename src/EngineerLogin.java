import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class EngineerLogin
 */
@WebServlet("/EngineerLogin")
public class EngineerLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EngineerLogin() 
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
			int engineerLog = methods.Engineer_login("/jobcard/homeengineer.jsp", "/jobcard/engineerPassChange.jsp" ,useremail, password, request, response);
			
			request.getSession(true).setAttribute("engineerLog", engineerLog);
			
			methods.getEngineerDetails(engineerLog, request, response);
		}
			
	}
	
}
