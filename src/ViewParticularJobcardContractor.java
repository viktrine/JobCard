

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
 * Servlet implementation class ViewParticularJobcardContractor
 */
@WebServlet("/ViewParticularJobcardContractor")
public class ViewParticularJobcardContractor extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewParticularJobcardContractor() 
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
		PreparedStatement pss = null;
		PreparedStatement psss = null;
		PreparedStatement pssss = null;
		
		String jobcard = request.getParameter("job_card_no");
		
		Methods methods = new Methods();
		
		if (request.getSession().getAttribute("contractorLog") != null)
		{
			out.println("<html>");
			out.println("<head><title>Job Card Details</title>");
		    
		    out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">");
		    out.println("<script src=\"js/bootstrap.min.js\"></script>");
		    out.println("<script src=\"jquery.min.js\"></script>");
		    
		    out.println("</head>");
		    
		    out.println("<body>");
			
			try
			{
				con = Methods.getConnection();
				
				int jobcard_no = methods.intPinConversion(jobcard);
				
				String jobcard_Details = "select * from jobcard_details where jobcard_no = ?";
				ps = con.prepareStatement(jobcard_Details);
				ps.setInt(1, jobcard_no);
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				String jobcard_used_items = "select * from 	jobcard_used_items where jobcard_no = ?";
				pss = con.prepareStatement(jobcard_used_items);
				pss.setInt(1, jobcard_no);
				ResultSet rss = pss.executeQuery();
				
				String jobcard_charges = "select * from jobcard_charges where jobcard_no = ?";
				psss = con.prepareStatement(jobcard_charges);
				psss.setInt(1, jobcard_no);
				ResultSet rsss = psss.executeQuery();
				
				String jobcard_time = "select * from jobcard_time where jobcard_no = ?";
				pssss = con.prepareStatement(jobcard_time);
				pssss.setInt(1, jobcard_no);
				ResultSet rssss = pssss.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				out.print("<table width=25% border=1>");
		    	  
		    	
				if(!rs.next() || !rss.next() || !rsss.next() || !rssss.next())
				{
					request.getSession().setAttribute("no_Details", "No Details to display");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					String type = rss.getString("jobcard_used_items.type");
					Double unit = rss.getDouble("jobcard_used_items.units");
					Double pay = rss.getDouble("jobcard_used_items.pay");					 
					
					out.print("<tr><th>DESCRIPTION</th><th>VALUE</th></tr>");
					 
					while (rss.next())
					{
						type = type + ", " + rss.getString("jobcard_used_items.type");
						unit = unit + rss.getDouble("jobcard_used_items.units");
						pay = pay + rss.getDouble("jobcard_used_items.pay");
					}
							 
					int job_card_no = rs.getInt("jobcard_details.jobcard_no");
					int jobcard_user_id  = rs.getInt("jobcard_details.user_id");
					String company = rs.getString("jobcard_details.company");
					String ticket = rs.getString("jobcard_details.ticket");
					String location = rs.getString("jobcard_details.location");
					String service = rs.getString("jobcard_details.service");
					String report = rs.getString("jobcard_details.report");
					String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));
					
					
					double billable = rsss.getDouble("jobcard_charges.billable");
					double reschedule = rsss.getDouble("jobcard_charges.reschedule");
					
					double summ = billable + reschedule + pay;
					 
					methods.getFieldEngineerDetails(jobcard_user_id, request, response);
					
					java.sql.Timestamp left_office = rssss.getTimestamp("jobcard_time.left_office");
					java.sql.Timestamp arrive_client = rssss.getTimestamp("jobcard_time.arrive_client");
					java.sql.Timestamp left_client = rssss.getTimestamp("jobcard_time.left_client");
					java.sql.Timestamp arrive_office = rssss.getTimestamp("jobcard_time.arrive_office");
					
					
					String engineer_fname = (String) request.getSession(false).getAttribute("fieldfname");
					String engineer_oname = (String) request.getSession(false).getAttribute("fieldoname");
					String fieldname = engineer_fname + " " + engineer_oname;
					
					String contractor_jobcard_name = (String) request.getSession(false).getAttribute("contractorcname");
					
					request.getSession(true).setAttribute("job_card_noo", job_card_no); 
					request.getSession(true).setAttribute("fieldname", fieldname); 
					request.getSession(true).setAttribute("contractor_jobcard_name", contractor_jobcard_name);
					request.getSession(true).setAttribute("companyy", company); 
					request.getSession(true).setAttribute("tickett", ticket); 
					request.getSession(true).setAttribute("locationn", location);
					request.getSession(true).setAttribute("servicee", service); 
					request.getSession(true).setAttribute("reportt", report); 
					request.getSession(true).setAttribute("date_raisedd", date_raised); 
					 
					request.getSession(true).setAttribute("typee", type);
					request.getSession(true).setAttribute("unitt", unit);
					request.getSession(true).setAttribute("payy", pay);
					request.getSession(true).setAttribute("summ", summ);
					
					request.getSession(true).setAttribute("billablee", billable);
					request.getSession(true).setAttribute("reschedulee", reschedule);
					 
					request.getSession(true).setAttribute("left_officee", left_office);
					request.getSession(true).setAttribute("arrive_clientt", arrive_client);
					request.getSession(true).setAttribute("left_clientt", left_client);
					request.getSession(true).setAttribute("arrive_officee", arrive_office);
					
					response.sendRedirect("/jobcard/jobcardDetailContractor.jsp");
				}
				out.print("</table>");
				
			}
			catch (Exception e) 
			{
				request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
				
				response.sendRedirect(request.getHeader("Referer"));
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to view the Job Card details");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
		}
		
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
