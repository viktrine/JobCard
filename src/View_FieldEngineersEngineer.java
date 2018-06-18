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
 * Servlet implementation class View_FieldEngineersEngineer
 */
@WebServlet("/View_FieldEngineersEngineer")
public class View_FieldEngineersEngineer extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_FieldEngineersEngineer() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		RequestDispatcher menu = request.getRequestDispatcher("menuengineer.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		if (request.getSession().getAttribute("engineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("engineerLog");
			
			
			String no_Details = (String) request.getSession().getAttribute("no_Details");
			String DatabaseError = (String) request.getSession().getAttribute("DatabaseError");
			String successDelete = (String) request.getSession().getAttribute("successDelete");
			String ErrorDelete = (String) request.getSession().getAttribute("ErrorDelete");
			String updateSucess = (String) request.getSession().getAttribute("updateSucess");
			String priv = (String) request.getSession().getAttribute("priv");
			String ResetError = (String) request.getSession().getAttribute("ResetError");
			String EmailError = (String) request.getSession().getAttribute("EmailError");
			
			out.println("<html>");
		    out.println("<head><title>Field Engineers</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    if(no_Details != null)
		    {
		    	out.println("<font color=\"FF0000\">"+no_Details+"</font>");
		    	request.getSession().removeAttribute("no_Details");
		    }
		    
		    if(DatabaseError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+DatabaseError+"</font>");
		    	request.getSession().removeAttribute("DatabaseError");
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
		    
		    if(updateSucess != null)
		    {
		    	out.println("New Password: "+"<font color=\"FF0000\">"+updateSucess+"</font>");
		    	request.getSession().removeAttribute("updateSucess");
		    }
		    if(priv != null)
		    {
		    	out.println("<font color=\"FF0000\">"+priv+"</font>");
		    	request.getSession().removeAttribute("priv");
		    }
		    
		    if(ResetError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+ResetError+"</font>");
		    	request.getSession().removeAttribute("ResetError");
		    }
		    if(EmailError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+EmailError+"</font>");
		    	request.getSession().removeAttribute("EmailError");
		    }
		    
			try
			{
				con = Methods.getConnection();
				
				String field_engineers = "select * from fieldEngineerDetails where engineer_id = ?";
				ps = con.prepareStatement(field_engineers);
				ps.setInt(1, idn); 
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				out.print("<table width=25% border=1>");
		    	  
		    	
				if(!rs.isBeforeFirst())
				{
					out.println("<script> type=\"text/javascript\">");
		    		out.print("alert('No Field Engineers to Display');");
		    		out.print("location='homeengineer.jsp';");
		    		out.println("</script>");
		    		  
				}
				else
				{
					out.println("<center><h1>Field Engineers</h1>"); 
					
					int counter = 1;
					
					out.print("<tr><td nowrap align=\"center\"><b>Entry</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>First Name</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>Second Name</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>Phone Number</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>E-Mail</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>Staff ID</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>Registration Date</b>&nbsp; &nbsp; &nbsp;</td><td nowrap align=\"center\"><b>Action</b>&nbsp; &nbsp; &nbsp;</td></tr>");
					 
					while(rs.next())
					{
						int id = rs.getInt("id");
						String fname = rs.getString("fname");
						String sname = rs.getString("oname");
						String phone_no = rs.getString("phone_no");
						String email = rs.getString("email");
						String staff_id = rs.getString("staff_id");
						String registration_date = df_day.format(rs.getTimestamp("registration_date"));
						
						out.print("<tr><td nowrap>"+counter+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+fname+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+sname+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+phone_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+email+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+staff_id+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+registration_date+"&nbsp; &nbsp; &nbsp;</td>"
						 		+ "<td nowrap>"
						 		+ "&nbsp; &nbsp; &nbsp;<a href='ViewParticularFieldEngineerDetailsEngineer?id="+id+"' title='View Pofile' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
						 		+ "<a href='DeleteFieldEngineer?id="+id+"' onclick=\"return confirm('Are you sure you want to delete the account?')\" title='Delete Account' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a> &nbsp; &nbsp; &nbsp;"
						 		+ "<a href='FieldEngineerPassRecovery?useremail="+email+"' title='Reset Password' data-toggle='tooltip'> Reset Password</a> &nbsp; &nbsp; &nbsp;</td></tr>");
						counter = counter + 1;
					}
					
					
				}
				
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homeengineer.jsp';");
	    		out.println("</script>");
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Login to view Field Engineers");
            
    		response.sendRedirect("/jobcard/engineerlogin.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
