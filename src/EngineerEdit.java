import java.io.IOException;
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
 * Servlet implementation class EngineerEdit
 */
@WebServlet("/EngineerEdit")
public class EngineerEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EngineerEdit() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String email = request.getParameter("email");
		String idpp = request.getParameter("idpp");
		String staffid = request.getParameter("staffid");
		String company = request.getParameter("company");
		String pno = request.getParameter("pno");
		
		
		
		if (request.getSession().getAttribute("engineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("engineerLog");
			
			if ((fname == null || fname.isEmpty()) || (pno == null || pno.isEmpty()) || (oname == null || oname.isEmpty()) 
					|| (email == null || email.isEmpty()) || (idpp == null || idpp.isEmpty()) 
					|| (staffid == null || staffid.isEmpty()) || (company == null || company.isEmpty()))
			{
				
				
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));	
			}
			else
			{
				try
				{
					con = Methods.getConnection();
					
					try 
					{
						
						String sql = "update engineerDetails set fname=?,oname=?,email=?,phone_no=?,idpp_no=?,staff_id=?,company=? where id=?";
						ps = con.prepareStatement(sql);
						
						ps.setString(1, fname);
						ps.setString(2, oname);
						ps.setString(3, email);
						ps.setString(4, pno);
						ps.setString(5, idpp);
						ps.setString(6, staffid);
						ps.setString(7, company);
						ps.setInt(8, idn);
						
						
						ps.executeUpdate();
						
						methods.getEngineerDetails(idn, request, response);
						
						request.getSession().setAttribute("updateSucess", "Profile Updated Successfully");
						
						response.sendRedirect("/jobcard/homeengineer.jsp");
						
					} 
					catch (SQLException e) 
					{
						request.getSession().setAttribute("Error", "Error Updating Profile");
		            
			    		response.sendRedirect(request.getHeader("Referer"));	
					}
				}
				catch (Exception e)
				{
					request.getSession().setAttribute("databaseConnectionError", "Error Establishing database connection");
					
					response.sendRedirect(request.getHeader("Referer"));	
				}
				
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to update profile");
            
    		response.sendRedirect("/jobcard/engineerlogin.jsp");
		}
	}

}
