import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
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
public class AdminRegister extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRegister() 
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
		String tel = request.getParameter("tel");
		String password = request.getParameter("password");
		String password_confirm = request.getParameter("password_confirm");
		String pin = request.getParameter("pin");
		String idpp = request.getParameter("idpp");
		String staffid = request.getParameter("staffid");
		String company = request.getParameter("company");
		
		
		request.getSession(true).setAttribute("fname", fname);
		request.getSession(true).setAttribute("oname", oname);
		request.getSession(true).setAttribute("email", email);
		request.getSession(true).setAttribute("tel", tel);
		request.getSession(true).setAttribute("idpp", idpp);
		request.getSession(true).setAttribute("staffid", staffid);
		request.getSession(true).setAttribute("company", company);

		if (request.getSession().getAttribute("adminLog") != null)
		{
			if ((fname == null || fname.isEmpty()) || (oname == null || oname.isEmpty()) 
					|| (email == null || email.isEmpty()) || (tel == null || tel.isEmpty()) || (password == null || password.isEmpty()) 
					|| (password_confirm == null || password_confirm.isEmpty()) || (pin == null || pin.isEmpty()) || (idpp == null || idpp.isEmpty()) 
					|| (staffid == null || staffid.isEmpty()) || (company == null || company.isEmpty()))
			{
				
				
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				int idnn = (int) request.getSession().getAttribute("adminLog");
				
				if (password.equals(password_confirm))
				{
					try
					{
						password = methods.tohash(password);
						try
						{
							int pinn = methods.intPinConversion(pin);
							
							try
							{
								con = Methods.getConnection();
								
								String selects = "select * from adminDetails where email=?";
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
										
										String sql = "insert into adminDetails (fname,oname,email,phone,idpp_no,staff_no,company_name) values(?,?,?,?,?,?,?)";
										ps = con.prepareStatement(sql);
										
										ps.setString(1, fname);
										ps.setString(2, oname);
										ps.setString(3, email);
										ps.setString(4, tel);
										ps.setString(5, idpp);
										ps.setString(6, staffid);
										ps.setString(7, company);
										
										
										ps.executeUpdate();
										
										//Get the latest Admin id
										String checkadmin = "select * from adminDetails where email=?";
										ps = con.prepareStatement(checkadmin);
										ps.setString(1, email);
										
										ResultSet rs = (ResultSet) ps.executeQuery();
										
										while(rs.next())
										{
											int idn = rs.getInt("id");
											
											//Creating admin pass tables
											String adminpasssql = "insert into adminPass (pass,passpin,adminDetailsid) values(?,?,?)";
											ps = con.prepareStatement(adminpasssql);
											
											ps.setString(1, password);
											ps.setInt(2, pinn);
											ps.setInt(3, idn);
											
											ps.executeUpdate();
											
											
											request.getSession().setAttribute("registerSuccess", "Registration Successful");
								            
								    		response.sendRedirect(request.getHeader("Referer"));
											
								    		
								    		request.getSession().removeAttribute("fname");
								    		request.getSession().removeAttribute("oname");
								    		request.getSession().removeAttribute("email");
								    		request.getSession().removeAttribute("tel");
								    		request.getSession().removeAttribute("idpp");
								    		request.getSession().removeAttribute("staffid");
								    		request.getSession().removeAttribute("company");
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
						catch (Exception e)
						{
							request.getSession().setAttribute("pinError", "Give an integer PIN");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
					}
					catch (Exception e)
					{
						request.getSession().setAttribute("passError", "Pass Hashing Error");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					
					
				}
				else
				{
					request.getSession().setAttribute("passDiffError", "Passwords do not match");
		            
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
