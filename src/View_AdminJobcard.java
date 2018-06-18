import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class View_AdminJobcard
 */
@WebServlet("/View_AdminJobcard")
public class View_AdminJobcard extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_AdminJobcard() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setIntHeader("Refresh", 5);
		
		response.setContentType("text/html");
		
		RequestDispatcher menu = request.getRequestDispatcher("menuadmin.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		PrintWriter out = response.getWriter();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			out.println("<html>");
		    out.println("<head><title>Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    
		    menu.include(request, response);
		    
			
			try
			{
				con = Methods.getConnection();
				
				Methods methods = new Methods();
				
				methods.get_Jobcard_Status(request, response);
				
				String jobcard_details = "select DISTINCT jobcard_details.jobcard_no,jobcard_details.company,jobcard_details.ticket,jobcard_details.service,jobcard_details.report,jobcard_details.date_filled from jobcard_details group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				
				 if (!rs.isBeforeFirst())
				 {
					 out.println("<script> type=\"text/javascript\">");
		    		 out.print("alert('No Jobcards Found');");
		    		 out.print("location='homeadmin.jsp';");
		    		 out.println("</script>");
				 }
				 
				 else
				 {
					 out.println("<center><h1>Job Cards</h1>");
					 out.print("<table width=25% border=1>");
					 
					 out.print("<tr><td nowrap><b>Job Card</b>&nbsp; &nbsp; &nbsp;</td><td nowrap><b>Company</b>&nbsp; &nbsp; &nbsp;</td><td nowrap><b>Ticket</b>&nbsp; &nbsp; &nbsp;</td><td nowrap><b>Service</b>&nbsp; &nbsp; &nbsp;</td><td nowrap><b>Report</b>&nbsp; &nbsp; &nbsp;</td><td nowrap><b>Date Raised</b>&nbsp; &nbsp; &nbsp;</td>"
						 		+ "<td nowrap><b>Action</b>&nbsp; &nbsp; &nbsp;</td></tr>");
					 
					 while(rs.next())
						{
							 int job_card_no = rs.getInt("jobcard_details.jobcard_no");
							 String company = rs.getString("jobcard_details.company");
							 String ticket = rs.getString("jobcard_details.ticket");
							 String service = rs.getString("jobcard_details.service");
							 String report = rs.getString("jobcard_details.report");
							 String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));

							 /* double billable = rs.getDouble("jobcard_charges.billable");
							 double reschedule = rs.getDouble("jobcard_charges.reschedule");
							

							String date_raiseed = df_day.format(rs.getTimestamp("left_office"));
							 
							 String left_office = df_day.format(rs.getTimestamp("jobcard_time.left_office"));
							 String arrive_client = df_day.format(rs.getTimestamp("jobcard_time.arrive_client"));
							 String left_client = df_day.format(rs.getTimestamp("jobcard_time.left_client"));
							 String arrive_office = df_day.format(rs.getTimestamp("jobcard_time.arrive_office"));
							*/
							 out.print("<tr><td nowrap>"+job_card_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+service+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+report+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td>"
							 		+ "<td nowrap>"
							 		+ "<a href='ViewParticularJobcardAdmin?job_card_no="+job_card_no+"' title='View Record' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
							 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Record' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
							 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a></td></tr>");
								}
				 }
				 
				 
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homeadmin.jsp';");
	    		out.println("</script>");
	    		
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst","Need to be Admin to Proceed");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
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
