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
 * Servlet implementation class FieldRegister
 */
@WebServlet("/FieldRegister")
public class FieldRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FieldRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		
	}

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
		String pno = request.getParameter("pno");
		String email = request.getParameter("email");
		String staffid = request.getParameter("staffid");
		
		request.getSession().setAttribute("fname", fname);
		request.getSession().setAttribute("oname", oname);
		request.getSession().setAttribute("pno", pno);
		request.getSession().setAttribute("email", email);
		request.getSession().setAttribute("staffid", staffid);
		
		if (request.getSession().getAttribute("contractorLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("contractorLog");
			
			if ((fname == null || fname.isEmpty()) || (oname == null || oname.isEmpty()) || (pno == null || pno.isEmpty()) || (email == null || email.isEmpty()) || (staffid == null || staffid.isEmpty()))
			{
				
				
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				
				try
				{
					con = Methods.getConnection();
					
					String selects = "select * from fieldEngineerDetails where email=?";
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
							String get_contractorid = "select * from contractorDetails where manager_id=?";
							ps = con.prepareStatement(get_contractorid);
							
							ps.setInt(1, idn);
							
							ResultSet rss = (ResultSet) ps.executeQuery();
							
							while(rss.next())
							{
								int contractorid = rss.getInt("id");
								int engineerid = rss.getInt("engineer_id");
							
							
								//creating FieldEngineer Details.
								String sqlengineer = "insert into fieldEngineerDetails (fname,oname,phone_no,email,staff_id,contractor_id,engineer_id) values(?,?,?,?,?,?,?)";
								ps = con.prepareStatement(sqlengineer);
								
								ps.setString(1, fname);
								ps.setString(2, oname);
								ps.setString(3, pno);
								ps.setString(4, email);
								ps.setString(5, staffid);
								ps.setLong(6, contractorid);
								ps.setInt(7, engineerid);
								
								ps.executeUpdate();
								
								//Get the latest field manager id
								
								String checkfield_engineer = "select * from fieldEngineerDetails where email=?";
								ps = con.prepareStatement(checkfield_engineer);
								ps.setString(1, email);
								
								ResultSet rs = (ResultSet) ps.executeQuery();
								
								while(rs.next())
								{
									int idnn = rs.getInt("id");
									
									//create the default password
									//Create the engineer password
									String passwordrand = methods.randPass(10);
									String password = methods.tohash(passwordrand);
									
									//Creating field engineer credentials
									String sqlfieldengineer = "insert into fieldEngineerPass (pass,fieldEngineerDetails_id) values(?,?)";
									ps = con.prepareStatement(sqlfieldengineer);
									
									ps.setString(1, password);
									ps.setInt(2, idnn);
									
									
									request.getSession().setAttribute("passwordrand", passwordrand); 
									
								
									ps.executeUpdate();
									
									request.getSession().removeAttribute("fname");
									request.getSession().removeAttribute("oname");
									request.getSession().removeAttribute("pno");
									request.getSession().removeAttribute("email");
									request.getSession().removeAttribute("staffid");
									
									request.getSession().setAttribute("registerSuccess", "Registration Successful");	
									response.sendRedirect(request.getHeader("Referer"));
								}
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
			request.getSession().setAttribute("loginFirst", "Please login to register a field manager");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
		}
	}

}
