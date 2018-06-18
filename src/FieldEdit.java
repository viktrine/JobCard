import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class FieldEdit
 */
@WebServlet("/FieldEdit")
public class FieldEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FieldEdit() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		Connection con = null;
		PreparedStatement ps = null;
		
		Methods methods = new Methods();
		
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String pno = request.getParameter("pno");
		String email = request.getParameter("email");
		String staffid = request.getParameter("staffid");
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
		{
			int idn = (int) request.getSession(true).getAttribute("fieldengineerLog");
			
			
			if ((fname == null || fname.isEmpty()) || (oname == null || oname.isEmpty()) || (pno == null || pno.isEmpty()) || (email == null || email.isEmpty()) || (staffid == null || staffid.isEmpty()))
			{
				request.getSession(true).setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				try
				{
					con = Methods.getConnection();
					
					try
					{
						String sql = "update fieldEngineerDetails set fname=?,oname=?,phone_no=?,email=?,staff_id=? where id=?";
						ps = (PreparedStatement) con.prepareStatement(sql);
					
						ps.setString(1, fname);
						ps.setString(2, oname);
						ps.setString(3, pno);
						ps.setString(4, email);
						ps.setString(5, staffid);
						ps.setInt(6, idn);
						
						ps.executeUpdate();
						
						methods.getFieldEngineerDetails(idn, request, response);
						
						request.getSession().setAttribute("Success", "profile updated successfully");

						response.sendRedirect("/jobcard/homefieldengineer.jsp");
					}
					catch (Exception e)
					{
						request.getSession(true).setAttribute("Error", "Error updating your profile");
						
						response.sendRedirect(request.getHeader("Referer"));
					}
				}
				catch (Exception e)
				{
					request.getSession(true).setAttribute("databaseConnectionError", "Error Establishing database connection");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
			}
		}
		else
		{
			request.getSession(true).setAttribute("loginFirst", "Please login to update your profile");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
