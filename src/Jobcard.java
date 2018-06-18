import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Jobcard
 */
@WebServlet("/Jobcard")
public class Jobcard extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jobcard() 
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
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		
		PreparedStatement psss = null;
		
		
		String company = request.getParameter("company");
		String ticket = request.getParameter("ticket");
		String service = request.getParameter("service");
		String report = request.getParameter("report");
		String location = request.getParameter("location");
		String asiigned_email = request.getParameter("asiigned_email");
		
		
		String[] item = request.getParameterValues("item");
		String[] desc = request.getParameterValues("desc");
		String[] Serial = request.getParameterValues("Serial");
		String[] units = request.getParameterValues("units");
		
		String leftOffice = request.getParameter("leftOffice");
		String atPremise = request.getParameter("atPremise");
		String offPremise = request.getParameter("offPremise");
		String backOffice = request.getParameter("backOffice");
		
		String billable = request.getParameter("billable");
		String reschedule = request.getParameter("reschedule");
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("fieldengineerLog");
			
			
			if((item == null))
			{
				request.getSession().setAttribute("itemnullError", "Atleast provide an item used");
				
				response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				try
				{
					con = Methods.getConnection();
					
					String get_engineer = "select * from engineerDetails where email=?";
					ps = con.prepareStatement(get_engineer);
					ps.setString(1, asiigned_email);
					
					ResultSet rsss = (ResultSet) ps.executeQuery();
					
					while(rsss.next())
					{
						int engineerid = rsss.getInt("id");
						
						String get_contractorid = "select * from fieldEngineerDetails where id=?";
						ps = con.prepareStatement(get_contractorid);
						
						ps.setInt(1, idn);
						
						ResultSet rs = (ResultSet) ps.executeQuery();
						
						while(rs.next())
						{
							int contractorid = rs.getInt("contractor_id");
							
							//Save the jobcard details
							
							String sql = "insert into jobcard_details (user_id,contractor_id,engineer_id,company,ticket,service,report,location) values(?,?,?,?,?,?,?,?)";
							ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
						
							ps.setInt(1, idn);
							ps.setInt(2, contractorid);
							ps.setInt(3, engineerid);
							ps.setString(4, company);
							ps.setString(5, ticket);
							ps.setString(6, service);
							ps.setString(7, report);
							ps.setString(8, location);
							
							ps.executeUpdate();
							
							
							//Get the last inserted id in jobcard_details
							ResultSet rss = ps.getGeneratedKeys();
						
							int jobcard_no = 0;
							
							if (rss.next())
							{
								jobcard_no = rss.getInt(1);
								
								for (int i = 0; i < item.length; i++)
								{
									String select_price = "select * from materials where materials=?";
									ps = con.prepareStatement(select_price);
									ps.setString(1, item[i]);
									
									ResultSet price_rs = (ResultSet) ps.executeQuery();
									
									while(price_rs.next())
									{
										double price = price_rs.getDouble(3);
										
										String used_items = "insert into jobcard_used_items (type,description,serial,units,pay,jobcard_no) values(?,?,?,?,?,?)";
										ps = con.prepareStatement(used_items);
										
										double unit = methods.doublePinConversion(units[i]);
										double total_pay =  unit * price;
										
										ps.setString(1, item[i]);
										ps.setString(2, desc[i]);
										ps.setString(3, Serial[i]);
										ps.setDouble(4, unit);
										ps.setDouble(5, total_pay);
										ps.setInt(6, jobcard_no);
										
										ps.executeUpdate();
										
									}
								}
								
								if ((leftOffice == null || (leftOffice.isEmpty())) || (atPremise == null || atPremise.isEmpty()) || (offPremise == null || offPremise.isEmpty()) || (backOffice == null || backOffice.isEmpty()))
								{
									String timecard = "insert into jobcard_time (jobcard_no) values(?)";
									psss = con.prepareStatement(timecard);
									
									psss.setInt(1, jobcard_no);
									
									psss.executeUpdate();
								}
								else
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
											
										
										java.sql.Timestamp sqlleftOfficetime = new Timestamp(leftOfficetime.getTime());
										java.sql.Timestamp sqlatPremisetime = new Timestamp(atPremisetime.getTime());
										java.sql.Timestamp sqloffPremisetime = new Timestamp(offPremisetime.getTime());
										java.sql.Timestamp sqlbackOfficetime = new Timestamp(backOfficetime.getTime());
										
										
										String timecard = "insert into jobcard_time (jobcard_no, left_office, arrive_client, left_client, arrive_office) values(?,?,?,?,?)";
										ps = con.prepareStatement(timecard);
										
										ps.setInt(1, jobcard_no);
										ps.setTimestamp(2, (Timestamp) sqlleftOfficetime);
										ps.setTimestamp(3, (Timestamp) sqlatPremisetime);
										ps.setTimestamp(4, (Timestamp) sqloffPremisetime);
										ps.setTimestamp(5, (Timestamp) sqlbackOfficetime);
										
										ps.executeUpdate();
										
									}
									catch (Exception e)
									{
										request.getSession().setAttribute("timeconversionerror", "Error time conversion");
										
										response.sendRedirect(request.getHeader("Referer"));
									}
								}
								
								if ((billable == null || billable.isEmpty()) || (reschedule == null || reschedule.isEmpty()))
								{
									String billablecharges = "insert into jobcard_charges (jobcard_no) values(?)";
									pss = con.prepareStatement(billablecharges);
									
									pss.setInt(1, jobcard_no);
									
									pss.executeUpdate();
								}
								else
								{
									Double bill = Double.parseDouble(billable);
									Double schedule = Double.parseDouble(reschedule);
									
									String billablecharges = "insert into jobcard_charges (billable,reschedule,jobcard_no) values(?,?,?)";
									ps = con.prepareStatement(billablecharges);
									
									ps.setDouble(1, bill);
									ps.setDouble(2, schedule);
									ps.setInt(3, jobcard_no);
									
									ps.executeUpdate();
								}
								
								request.getSession().setAttribute("materialPosted", "Materials saved successfully");	
								
								response.sendRedirect(request.getHeader("Referer"));
							}
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
			request.getSession().setAttribute("loginFirst", "Please login to fill the jobcard");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
		
	}

}
