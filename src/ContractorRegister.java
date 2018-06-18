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
public class ContractorRegister extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractorRegister() 
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
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String name = request.getParameter("namee");
		String tel = request.getParameter("pno");
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String email = request.getParameter("email");
		String staffid = request.getParameter("staffid");
		String pin = request.getParameter("pin");
		String vat_no = request.getParameter("vat_no");
		
		Methods methods = new Methods();
		
		
		
		if (request.getSession().getAttribute("engineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("engineerLog");
			
			if ((name == null || name.isEmpty()) || (oname == null || oname.isEmpty()) 
					|| (email == null || email.isEmpty()) || (tel == null || tel.isEmpty())
					|| (fname == null || fname.isEmpty()) || (staffid == null || staffid.isEmpty()) 
					|| (staffid == null || staffid.isEmpty()) || (pin == null || pin.isEmpty()) || (vat_no == null || vat_no.isEmpty()))
			{
				
				
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				request.getSession().setAttribute("name", name);
				request.getSession().setAttribute("tel", tel);
				request.getSession().setAttribute("oname", oname);
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("fname", fname);
				request.getSession().setAttribute("staffid", staffid);
				request.getSession().setAttribute("pin", pin);
				request.getSession().setAttribute("vat_no", vat_no);
				
				try
				{
					con = Methods.getConnection();
					
					String selects = "select * from managerDetails where email=?";
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
							//creating Manager Details.
							String sqlmanager = "insert into managerDetails (fname,oname,email,staffid,engineer_id) values(?,?,?,?,?)";
							ps = con.prepareStatement(sqlmanager);
							
							ps.setString(1, fname);
							ps.setString(2, oname);
							ps.setString(3, email);
							ps.setString(4, staffid);
							ps.setInt(5, idn);
							
							ps.executeUpdate();
							
							//Get the latest manager id
							String checkmanager = "select * from managerDetails where email=?";
							ps = con.prepareStatement(checkmanager);
							ps.setString(1, email);
							
							ResultSet rs = (ResultSet) ps.executeQuery();
							
							while(rs.next())
							{
								int idnn = rs.getInt("id");
								
								//Creating contractor Details
								String sqlcontractor = "insert into contractorDetails (cname,contact_phone,manager_id,engineer_id,kra_pin,vat_no) values(?,?,?,?,?,?)";
								ps = con.prepareStatement(sqlcontractor);
								
								ps.setString(1, name);
								ps.setString(2, tel);
								ps.setInt(3, idnn);
								ps.setInt(4, idn);
								ps.setString(5, pin);
								ps.setString(6, vat_no);
								ps.executeUpdate();
								
								//Creating contractor Pass
								String passwordrand = methods.randPass(10);
								String password = methods.tohash(passwordrand);

							
								request.getSession().setAttribute("passwordrand", passwordrand); 
								
								String contractorPass = "insert into contractorPass (pass,manager_id) values(?,?)";
								ps = con.prepareStatement(contractorPass);
								
								ps.setString(1, password);
								ps.setInt(2, idnn);
								
								ps.executeUpdate();
								
								request.getSession().setAttribute("registerSuccess", "Registration Successful");
					            
					    		response.sendRedirect(request.getHeader("Referer"));
					    		
					    		
					    		request.getSession().removeAttribute("name");
					    		request.getSession().removeAttribute("tel");
					    		request.getSession().removeAttribute("oname");
					    		request.getSession().removeAttribute("email");
					    		request.getSession().removeAttribute("fname");
					    		request.getSession().removeAttribute("staffid");
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
			request.getSession().setAttribute("loginFirst", "Please login to register a contractor");
            
    		response.sendRedirect("/jobcard/engineerlogin.jsp");
		}
		
		
			
	}
	

}
