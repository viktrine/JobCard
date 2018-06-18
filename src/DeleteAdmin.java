import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class DeleteAdmin
 */
@WebServlet("/DeleteAdmin")
public class DeleteAdmin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteAdmin() 
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
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		
		String id = request.getParameter("id");
		Methods methods = new Methods();
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			out.println("<html>");
			out.println("<head><title>Delete Account</title>");
		    
		    out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">");
		    out.println("<script src=\"js/bootstrap.min.js\"></script>");
		    out.println("<script src=\"jquery.min.js\"></script>");
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    try
			{
				con = Methods.getConnection();
				
				int idd = methods.intPinConversion(id);
				
				
				String admin_DetailsDelete = "delete from adminDetails where id = ?";
				ps = con.prepareStatement(admin_DetailsDelete);
				ps.setInt(1, idd);
				int i = ps.executeUpdate();
				
				String adminDetailsDelete = "delete from adminPass where adminDetailsid = ?";
				pss = con.prepareStatement(adminDetailsDelete);
				pss.setInt(1, idd);
				int j = pss.executeUpdate();
				
				
				out.print("<table width=25% border=1>");
				  
				
				if(i == 1 || j == 1)
				{
					request.getSession().setAttribute("successDelete", "Account Deleted Successfully");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					request.getSession().setAttribute("ErrorDelete", "Error Deleting Account");
					
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
			request.getSession().setAttribute("priv", "Not allowed to delete the Account");
            
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
