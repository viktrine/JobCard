import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class AdminPassrecovery
 */
@WebServlet("/AdminPassrecovery")
public class AdminPassrecovery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminPassrecovery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psss = null;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		String useremail = request.getParameter("useremail");
		
		if ((useremail == null || useremail.isEmpty()))
		{
			
			request.getSession().setAttribute("nullError", "Please enter your email");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from adminDetails where email=?";
				ps = con.prepareStatement(selects);
				ps.setString(1, useremail);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				if(!rs.isBeforeFirst())
				{
					request.getSession().setAttribute("EmailError", "Wrong user email provided");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					while(rs.next())
					{
						int idn = rs.getInt("id");
						
						String passwordrand = methods.randPass(10);
						String password = methods.tohash(passwordrand);
						
						String updates = "update adminPass set pass=?, expire_date=? where adminDetailsid=?";
						psss = con.prepareStatement(updates);
						
						psss.setString(1, password);
						psss.setTimestamp(2, currentTime);
						psss.setInt(3, idn);
						
						int i = psss.executeUpdate();
						
						if(i != 0)
						{
							request.getSession().setAttribute("updateSucess", passwordrand);
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						else
						{
							request.getSession().setAttribute("ResetError", "Error Reseting the password");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						
					}
					
				}
				
			}
				
			catch (Exception e)
			{
				request.getSession().setAttribute("databaseConnectionError", "Error connecting to the database");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
	}
}
