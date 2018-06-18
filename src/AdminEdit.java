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
 * Servlet implementation class AdminEdit
 */
@WebServlet("/AdminEdit")
public class AdminEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminEdit() 
    {
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
		Connection con = null;
		PreparedStatement ps = null;
		
		Methods methods = new Methods();
		
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String idpp = request.getParameter("idpp");
		String staffid = request.getParameter("staffid");
		String company = request.getParameter("company");

		if (request.getSession().getAttribute("adminLog") != null)
		{
			if ((fname == null || fname.isEmpty()) || (oname == null || oname.isEmpty()) || (email == null || email.isEmpty()) || (tel == null || tel.isEmpty()) || (idpp == null || idpp.isEmpty()) 
					|| (staffid == null || staffid.isEmpty()) || (company == null || company.isEmpty()))
			{
				
				
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				int idnn = (int) request.getSession().getAttribute("adminLog");
				
				System.out.println(idnn);
				
				try
				{
					con = Methods.getConnection();
					
					try 
					{
						
						String sql = "update adminDetails set fname=?,oname=?,email=?,phone=?,idpp_no=?,staff_no=?,company_name=? where id=?";
						ps = con.prepareStatement(sql);
						
						ps.setString(1, fname);
						ps.setString(2, oname);
						ps.setString(3, email);
						ps.setString(4, tel);
						ps.setString(5, idpp);
						ps.setString(6, staffid);
						ps.setString(7, company);
						ps.setInt(8, idnn);
						
						int i = ps.executeUpdate();
						
						if (i == 1)
						{
							methods.getAdminDetails(idnn, request, response);		
							
							request.getSession().setAttribute("Success", "Profile Updated Successfully");
				            
				    		response.sendRedirect("/jobcard/homeadmin.jsp");
						}
						else
						{
							request.getSession().setAttribute("Error", "Error  Updating Profile");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						
						System.out.println(i);
						
					} 
					catch (SQLException e) 
					{
						request.getSession().setAttribute("registerError", "Error during registration");
		            
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
			request.getSession().setAttribute("loginFirst", "Please login to register a contractor");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
	}

}
