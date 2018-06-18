import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class ViewParticularContractorDetails
 */
@WebServlet("/ViewParticularContractorDetails")
public class ViewParticularContractorDetails extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewParticularContractorDetails() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		String id = request.getParameter("id");
		
		Methods methods = new Methods();
		
		if ((request.getSession().getAttribute("engineerLog") != null) || (request.getSession().getAttribute("adminLog") != null))
		{
			
			out.println("<html>");
			out.println("<head><title>Field Engineers</title>");
		    
		    out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">");
		    out.println("<script src=\"js/bootstrap.min.js\"></script>");
		    out.println("<script src=\"jquery.min.js\"></script>");
		    
		    out.println("</head>");
		    
		    out.println("<body>");
			
			try
			{
				con = Methods.getConnection();
				
				int id_value = methods.intPinConversion(id);
				
				String contractors = "select * from managerDetails inner join contractorDetails on managerDetails.id= contractorDetails.manager_id where managerDetails.id = ?";
				ps = con.prepareStatement(contractors);
				ps.setInt(1, id_value); 
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				out.print("<table width=25% border=1>");
		    	  
		    	
				if(!rs.next())
				{
					request.getSession().setAttribute("no_Details", "No Details to display");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					String pfname = rs.getString("fname");
					String psname = rs.getString("oname");
					String pemail = rs.getString("email");
					String pstaff_id = rs.getString("staffid");
					String pregistration_date = df_day.format(rs.getTimestamp("registration_date"));
					
					String pcname = rs.getString("cname");
					String pcontact_phone = rs.getString("contact_phone");
					String pkra_pin = rs.getString("kra_pin");
					String pvat_no = rs.getString("vat_no");
					
					String pname = pfname + " " + psname;
					

					request.getSession().setAttribute("pname", pname);
					request.getSession().setAttribute("pemail", pemail);
					request.getSession().setAttribute("pstaff_id", pstaff_id);
					request.getSession().setAttribute("pregistration_date", pregistration_date);
					request.getSession().setAttribute("pcname", pcname);
					request.getSession().setAttribute("pcontact_phone", pcontact_phone);
					request.getSession().setAttribute("pkra_pin", pkra_pin);
					request.getSession().setAttribute("pvat_no", pvat_no);
					
					response.sendRedirect("/jobcard/contractorDetails.jsp");
				}
				out.print("</table>");
				
			}
			catch (Exception e) 
			{
				request.getSession().setAttribute("DatabaseError", "Database Error...");
				
				response.sendRedirect(request.getHeader("Referer"));
			}
		}
		else
		{
			request.getSession().setAttribute("priv", "No Privilege to view the registered Field Engineers");
            
			response.sendRedirect(request.getHeader("Referer"));
		}
		
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
