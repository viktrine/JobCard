import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class AdminRegister
 */
@WebServlet("/AdminRegister")
public class EngineerRegister extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EngineerRegister() 
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
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String email = request.getParameter("email");
		String idpp = request.getParameter("idpp");
		String staffid = request.getParameter("staffid");
		String company = request.getParameter("company");
		String pno = request.getParameter("pno");
		
		request.getSession().setAttribute("fname", fname);
		request.getSession().setAttribute("oname", oname);
		request.getSession().setAttribute("email", email);
		request.getSession().setAttribute("idpp", idpp);
		request.getSession().setAttribute("staffid", staffid);
		request.getSession().setAttribute("company", company);
		request.getSession().setAttribute("pno", pno);
		
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("adminLog");
			
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
					
					String selects = "select * from engineerDetails where email=?";
					ps = con.prepareStatement(selects);
					ps.setString(1, email);
					
					if(ps.executeQuery().next())
					{
						request.getSession().setAttribute("UserExist", "The email user already Exist in the database");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					else
					{
						try 
						{
							
							String sql = "insert into engineerDetails(fname,oname,email,phone_no,idpp_no,staff_id,admin_id,company) values(?,?,?,?,?,?,?,?)";
							ps = con.prepareStatement(sql);
							
							ps.setString(1, fname);
							ps.setString(2, oname);
							ps.setString(3, email);
							ps.setString(4, pno);
							ps.setString(5, idpp);
							ps.setString(6, staffid);
							ps.setInt(7, idn);
							ps.setString(8, company);
							
							ps.executeUpdate();
							
							//Create the engineer password
							String passwordrand = methods.randPass(10);
							String password = methods.tohash(passwordrand);
							
							String checkengineer = "select * from engineerDetails where email=?";
							ps = con.prepareStatement(checkengineer);
							ps.setString(1, email);
							
							ResultSet rs = (ResultSet) ps.executeQuery();
							
							while(rs.next())
							{
								int idnn = rs.getInt("id");
								
								//Creating Engineer Details
								String sqlengineer = "insert into engineerPass(pass,engineer_id) values(?,?)";
								ps = con.prepareStatement(sqlengineer);
								
								ps.setString(1, password);
								ps.setInt(2, idnn);
								
								ps.executeUpdate();
								
								request.getSession().setAttribute("registerSuccess", "Registration Successful");
								
								request.getSession().setAttribute("passwordrand", passwordrand); 
								
								response.sendRedirect(request.getHeader("Referer"));
								
								
								request.getSession().removeAttribute("fname");
								request.getSession().removeAttribute("oname");
								request.getSession().removeAttribute("email");
								request.getSession().removeAttribute("idpp");
								request.getSession().removeAttribute("staffid");
								request.getSession().removeAttribute("company");
								request.getSession().removeAttribute("pno");

							}
							
						} 
						catch (SQLException e) 
						{
							request.getSession().setAttribute("registerError", "Error during registration");
			            
				    		response.sendRedirect(request.getHeader("Referer"));	
						}
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
			request.getSession().setAttribute("loginFirst", "Please login to register an engineer");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
	}
	

}
