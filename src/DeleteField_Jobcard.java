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
 * Servlet implementation class DeleteField_Jobcard
 */
@WebServlet("/DeleteField_Jobcard")
public class DeleteField_Jobcard extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteField_Jobcard() 
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
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement psss = null;
		PreparedStatement pssss = null;
		
		String jobcard = request.getParameter("job_card_no");
		Methods methods = new Methods();
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
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
				
				String jobcard_Details = "delete from jobcard_details where jobcard_no = ?";
				ps = con.prepareStatement(jobcard_Details);
				ps.setInt(1, jobcard_no);
				int i = ps.executeUpdate();
				
				String jobcard_used_items = "delete from jobcard_used_items where jobcard_no = ?";
				pss = con.prepareStatement(jobcard_used_items);
				pss.setInt(1, jobcard_no);
				int j = pss.executeUpdate();
				
				String jobcard_charges = "delete from jobcard_charges where jobcard_no = ?";
				psss = con.prepareStatement(jobcard_charges);
				psss.setInt(1, jobcard_no);
				int k = psss.executeUpdate();
				
				String jobcard_time = "delete from jobcard_time where jobcard_no = ?";
				pssss = con.prepareStatement(jobcard_time);
				pssss.setInt(1, jobcard_no);
				int m = pssss.executeUpdate();
				
				
				out.print("<table width=25% border=1>");
				  
				
				if(i == 1 || j == 1 || m == 1 || k == 1)
				{
					request.getSession().setAttribute("successDelete", "Jobcard Deleted Successfully");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					request.getSession().setAttribute("ErrorDelete", "Error Deleting Jobcard");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
			}
			catch (Exception e) 
			{
				
				request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
				
				response.sendRedirect(request.getHeader("Referer"));
			}
			
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Login to Delete the jobcard");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
		
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

}
