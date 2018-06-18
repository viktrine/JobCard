import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class JobcardEdit
 */
@WebServlet("/JobcardEdit")
public class JobcardEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobcardEdit()
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
		
		
		String company = request.getParameter("company");
		String ticket = request.getParameter("ticket");
		String service = request.getParameter("service");
		String report = request.getParameter("report");
		String location = request.getParameter("location");
		
		
		String leftOffice = request.getParameter("leftOffice");
		String atPremise = request.getParameter("atPremise");
		String offPremise = request.getParameter("offPremise");
		String backOffice = request.getParameter("backOffice");
		
		String billable = request.getParameter("billable");
		String reschedule = request.getParameter("reschedule");
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
		{
			
			try
			{
				
				con = Methods.getConnection();
				
				int job_card_noo = (int) request.getSession(false).getAttribute("job_card_noo"); 
				System.out.println(job_card_noo);
				
				String sql = "update jobcard_details set company=?,ticket=?,service=?,report=?,location=? where jobcard_no = ?";
				ps = (PreparedStatement) con.prepareStatement(sql);
			
				ps.setString(1, company);
				ps.setString(2, ticket);
				ps.setString(3, service);
				ps.setString(4, report);
				ps.setString(5, location);
				ps.setInt(6, job_card_noo);
				
				ps.executeUpdate();
				
				
				if (leftOffice != null || atPremise != null || offPremise != null || backOffice != null )
				{
					try
					{
						DateFormat leftOfficeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
						DateFormat atPremiseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
						DateFormat offPremiseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
						DateFormat backOfficeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
						
						
						java.util.Date leftOfficetime = leftOfficeFormat.parse(leftOffice);
						java.util.Date atPremisetime = atPremiseFormat.parse(atPremise);
						java.util.Date offPremisetime = offPremiseFormat.parse(offPremise);
						java.util.Date backOfficetime = backOfficeFormat.parse(backOffice);
							
						
						Timestamp sqlleftOfficetime = new Timestamp(leftOfficetime.getTime());
						java.sql.Timestamp sqlatPremisetime = new Timestamp(atPremisetime.getTime());
						java.sql.Timestamp sqloffPremisetime = new Timestamp(offPremisetime.getTime());
						java.sql.Timestamp sqlbackOfficetime = new Timestamp(backOfficetime.getTime());
						
						
						String timecard = "update jobcard_time set left_office=?, arrive_client=?, left_client=?, arrive_office=? where jobcard_no=?";
						ps = con.prepareStatement(timecard);
						
						ps.setTimestamp(1, (Timestamp) sqlleftOfficetime);
						ps.setTimestamp(2, (Timestamp) sqlatPremisetime);
						ps.setTimestamp(3, (Timestamp) sqloffPremisetime);
						ps.setTimestamp(4, (Timestamp) sqlbackOfficetime);
						ps.setInt(5, job_card_noo);
						
						ps.executeUpdate();
						
					}
					catch (Exception e)
					{
						request.getSession().setAttribute("timeconversionerror", "Error time conversion");
						
						response.sendRedirect(request.getHeader("Referer"));
					}
				}
				
				if ((billable != null || reschedule != null ))
				{
					Double bill = Double.parseDouble(billable);
					Double schedule = Double.parseDouble(reschedule);
					
					String billablecharges = "update jobcard_charges set billable=?,reschedule=? where jobcard_no=?";
					ps = con.prepareStatement(billablecharges);
					
					ps.setDouble(1, bill);
					ps.setDouble(2, schedule);
					ps.setInt(3, job_card_noo);
					
					ps.executeUpdate();
				}
				
				request.getSession().setAttribute("materialPosted", "Update successfully");	
				
				response.sendRedirect(request.getHeader("Referer"));
					
				}

			catch (Exception e)
			{
				request.getSession().setAttribute("databaseConnectionError", "Error Establishing database connection");
				
				response.sendRedirect(request.getHeader("Referer"));	
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Need to be Field Engineer");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
	}

}
