

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
 * Servlet implementation class ProformaContractor
 */
@WebServlet("/ProformaContractor")
public class ProformaContractor extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProformaContractor() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		RequestDispatcher menu = request.getRequestDispatcher("menucontractor.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		PrintWriter out = response.getWriter();
		
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String contractor = request.getParameter("contractor");
		String datefrom = request.getParameter("datefrom");
		String dateto = request.getParameter("dateto");
		
		if (request.getSession().getAttribute("contractorLog") != null)
		{
			
			if ((contractor == null || contractor.isEmpty()) || (datefrom == null || datefrom.isEmpty()) || (dateto == null || dateto.isEmpty()) )
			{
				request.getSession().setAttribute("nullError", "All Fields are required");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				
				try
				{
					con = Methods.getConnection();
					
					DateFormat datefromformat = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat datetoformat = new SimpleDateFormat("yyyy-MM-dd");
					
					java.util.Date datefromdate = datefromformat.parse(datefrom);
					java.util.Date datetodate = datetoformat.parse(dateto);
					
					datetodate = Methods.addDays(datetodate, 1);
					datetodate = Methods.subtractSecond(datetodate, -1);
					 
					java.sql.Timestamp sqldatefromdate = new Timestamp(datefromdate.getTime());
					java.sql.Timestamp sqldatetodate = new Timestamp(datetodate.getTime());
					
					
					double total_vat = 0.0;
					String vat_value_query = "select * from vat where from_date >= ? and to_date <= ?";
					ps = con.prepareStatement(vat_value_query);
					
					ps.setTimestamp(1, sqldatefromdate);
					ps.setTimestamp(2, sqldatetodate);
					
					ResultSet rsss = ps.executeQuery();
					
					while(rsss.next())
					{
						double vat = rsss.getDouble("vat_value");
						total_vat += vat;
					}
					
					
					String get_contractor_id = "select * from contractorDetails where cname=?";
					ps = con.prepareStatement(get_contractor_id);
					
					ps.setString(1, contractor);
					
					ResultSet rss = (ResultSet) ps.executeQuery();
					
					while(rss.next())
					{
						int contractorid = rss.getInt("id");
						String pin = rss.getString("kra_pin");
						String vat_no = rss.getString("vat_no");
						
						out.println("<html>");
						
					    out.println("<head><title>Proforma</title>");
					    
					    cdnpage.include(request, response);
					    
					    out.println("</head>");
					    
					    out.println("<body>");
					    
					    out.print("<div class=container>");
					    menu.include(request, response);
					    
					    out.print("<header><h1>"+contractor+"</h1></header>");
					    out.print("<header><h2><center>PROFORMA INVOICE</center></h2></header>");
					    
					    out.println("<hr/>");
					    
					    out.print("<table width=25% frame=\"box\">");
					    out.print("<tr><td nowrap style=\"text-align: right\">Pin No.</td><td>"+pin+"</td></tr>");
					    out.print("<tr><td style=\"text-align: right\">VAT No.</td><td nowrap>"+vat_no+"</td></tr>");
					    out.print("</table>");
					    
					    
					    out.println("<hr/>");
					    
					    out.print("<table width=25% border=1>");
					  
						
						String proforma_details = "select distinct jobcard_details.jobcard_no,jobcard_details.company,jobcard_details.date_filled,jobcard_details.ticket,jobcard_details.report,jobcard_details.location,jobcard_used_items.pay,user_id,billable,reschedule from jobcard_details inner join jobcard_used_items on jobcard_details.jobcard_no = jobcard_used_items.jobcard_no inner join jobcard_charges on jobcard_details.jobcard_no = jobcard_charges.jobcard_no where jobcard_details.date_filled >= ? and jobcard_details.date_filled <= ? and jobcard_details.contractor_id=?";
						ps = con.prepareStatement(proforma_details);
						
						ps.setTimestamp(1, sqldatefromdate);
						ps.setTimestamp(2, sqldatetodate);
						ps.setInt(3, contractorid);
						
						ResultSet rs = (ResultSet) ps.executeQuery();
						
						int counter = 1;
						double total_Amount_Used = 0;
						
						out.print("<tr><th>No.</th><th>Description</th><th>Date</th><th>Ticket</th><th>Comment</th><th nowrap>Job Card</th><th>Engineer</th><th>Location</th><th>Total</th></tr>");
						
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						
						if (!rs.isBeforeFirst())
						{
							request.getSession().setAttribute("noRecord", "No record found");
							
							response.sendRedirect(request.getHeader("Referer"));
						}
						else
						{
							while(rs.next())
					    	{
								String company = rs.getString("jobcard_details.company");
					    		String date_raised = df.format(rs.getTimestamp("jobcard_details.date_filled"));
					    		String ticket = rs.getString("jobcard_details.ticket");
					    		String comment = rs.getString("jobcard_details.report");
					    		int jobcard = rs.getInt("jobcard_details.jobcard_no");
					    		String location = rs.getString("jobcard_details.location");
					    		double total = rs.getDouble("jobcard_used_items.pay") + rs.getDouble("jobcard_charges.billable") + rs.getDouble("jobcard_charges.reschedule");
					    		
					    		//double billable = rs.getDouble("jobcard_charges.billable");
					    		//double reschedule = rs.getDouble("jobcard_charges.reschedule");
					    		
					    		int user_id = rs.getInt("user_id");
					    		
					    		total_Amount_Used += total;
					    		  
					    		methods.getFieldEngineerDetails(user_id, request, response);
					    		String fieldfname = (String) request.getSession(false).getAttribute("fieldfname");
					    		  
					    		
					    		out.print("<tr><td nowrap>"+counter+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp;</td><td nowrap>"+comment+"&nbsp;</td><td nowrap>"+jobcard+"&nbsp;</td><td nowrap>"+fieldfname+"&nbsp;</td><td nowrap>"+location+"&nbsp;</td><td  align=\"right\"  nowrap>"+total+"0</td></tr>");
					    		  
					    		counter++;
					    	 }
							
							double grand_total = total_Amount_Used + total_vat;
							out.print("<tr><td colspan=\"8\" align=\"right\">Total</td><td align=\"right\">"+total_Amount_Used+"0</td></tr>");
							out.print("<tr><td colspan=\"8\" align=\"right\"><b>VAT</b></td><td align=\"right\"><b>"+total_vat+"0</b></td></tr>");
							out.print("<tr><td colspan=\"8\" align=\"right\"><b>Grand Total</b></td><td align=\"right\"><b>"+grand_total+"0</b></td></tr>");
							
							out.print("</table>");
						}
						
					}
				}
				catch (Exception e)
				{
					request.getSession().setAttribute("dateError", "Date Error");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				
			}
			
			out.print("</div>");
		    out.println("</body>");
		    out.println("</html>");
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to print a proforma");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
		}
	
	}

}
