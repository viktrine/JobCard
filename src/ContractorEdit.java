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
 * Servlet implementation class ContractorEdit
 */
@WebServlet("/ContractorEdit")
public class ContractorEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractorEdit() 
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
		
 		String name = request.getParameter("namee");
		String tel = request.getParameter("pno");
		String fname = request.getParameter("fname");
		String oname = request.getParameter("oname");
		String email = request.getParameter("email");
		String staffid = request.getParameter("staffid");
		String pin = request.getParameter("pin");
		String vat_no = request.getParameter("vat_no");
		
		
		if (request.getSession().getAttribute("contractorLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("contractorLog");
			
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
			
				try
				{
					con = Methods.getConnection();
					
					try 
					{
						String sqlmanager = "update managerDetails set fname=?,oname=?,email=?,staffid=? where id=?";
						ps = con.prepareStatement(sqlmanager);
						
						ps.setString(1, fname);
						ps.setString(2, oname);
						ps.setString(3, email);
						ps.setString(4, staffid);
						ps.setInt(5, idn);
						
						int i = ps.executeUpdate();
						
						String sqlcontractor = "update contractorDetails set cname=?,contact_phone=?,kra_pin=?,vat_no=? where manager_id=?";
						ps = con.prepareStatement(sqlcontractor);
						
						ps.setString(1, name);
						ps.setString(2, tel);
						ps.setString(3, pin);
						ps.setString(4, vat_no);
						ps.setInt(5, idn);
						
						int m = ps.executeUpdate();
						
						if (i ==1 && m == 1)
						{
							methods.getContractorDetails(idn, request, response);
							
							request.getSession().setAttribute("updateSucess", "Profile Updated Successfully");
				            
				    		response.sendRedirect("/jobcard/homecontractor.jsp");
						}
						
					} 
					catch (SQLException e) 
					{
						request.getSession().setAttribute("Error", "Profile update unsuccessful");
		            
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
			request.getSession().setAttribute("loginFirst", "Please login to edit your profile");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
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
