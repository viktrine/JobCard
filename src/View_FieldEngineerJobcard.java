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
 * Servlet implementation class View_FieldEngineerJobcard
 */
@WebServlet("/View_FieldEngineerJobcard")
public class View_FieldEngineerJobcard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_FieldEngineerJobcard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	doGet(request, response);
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setIntHeader("Refresh", 5);
		
		

		response.setContentType("text/html");
		
		RequestDispatcher menu = request.getRequestDispatcher("menufield.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("fieldengineerLog");
			
			String itemnullError = (String) request.getSession().getAttribute("itemnullError");
			String successDelete = (String) request.getSession().getAttribute("successDelete");
			String ErrorDelete = (String) request.getSession().getAttribute("ErrorDelete");
			String DatabaseError = (String) request.getSession().getAttribute("DatabaseError");
			String no_Details = (String) request.getSession().getAttribute("no_Details");
			
			out.println("<html>");
		    out.println("<head><title>My Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    if(itemnullError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+itemnullError+"</font>");
		    	request.getSession().removeAttribute("itemnullError");
		    }
		    
		    if(successDelete != null)
		    {
		    	out.println("<font color=\"00EE76\">"+successDelete+"</font>");
		    	request.getSession().removeAttribute("successDelete");
		    }
		    if(ErrorDelete != null)
		    {
		    	out.println("<font color=\"FF0000\">"+ErrorDelete+"</font>");
		    	request.getSession().removeAttribute("ErrorDelete");
		    }
		    if(DatabaseError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+DatabaseError+"</font>");
		    	request.getSession().removeAttribute("DatabaseError");
		    }
		    if(no_Details != null)
		    {
		    	out.println("<font color=\"FF0000\">"+no_Details+"</font>");
		    	request.getSession().removeAttribute("no_Details");
		    }
		    
		    out.println("<center><h1>Job Cards</h1>");
			
			try
			{
				con = Methods.getConnection();
				
				Methods methods = new Methods();
				
				methods.get_Jobcard_Status(request, response);
				
				String jobcard_details = "select DISTINCT jobcard_details.jobcard_no,jobcard_details.company,jobcard_details.ticket,jobcard_details.service,jobcard_details.report,jobcard_details.date_filled from jobcard_details  where user_id = ? group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				ps.setInt(1, idn);
				
				
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				
				 if (!rs.isBeforeFirst())
				 {
					 out.println("<script> type=\"text/javascript\">");
		    		 out.print("alert('No Jobcards Found');");
		    		 out.print("location='homefieldengineer.jsp';");
		    		 out.println("</script>");
				 }
				 
				 else
				 {
					 out.print("<table width=25% border=1>");
					 
					 out.print("<tr><td nowrap><b>Job Card</b></td><td nowrap><b>Company</b></td><td nowrap><b>Ticket</b></td><td nowrap><b>Service</b></td><td nowrap><b>Report</b></td><td nowrap><b>Date Raised</b></td>"
						 		+ "<td nowrap><b>Action</b></td></tr>");
					 
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
							 		+ "<a href='ViewParticularJobcardField?job_card_no="+job_card_no+"' title='View Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
							 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Job Card' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
							 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a></td></tr>");
						}
				 }
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homefieldengineer.jsp';");
	    		out.println("</script>");
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Login to view your jobcards");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	}

}
