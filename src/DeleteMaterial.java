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
 * Servlet implementation class DeleteMaterial
 */
@WebServlet("/DeleteMaterial")
public class DeleteMaterial extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMaterial() 
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
			out.println("<head><title>Delete Materials</title>");
		    
		    out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">");
		    out.println("<script src=\"js/bootstrap.min.js\"></script>");
		    out.println("<script src=\"jquery.min.js\"></script>");
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    try
			{
				con = Methods.getConnection();
				
				int idd = methods.intPinConversion(id);
				
				
				String material_Delete = "delete from materials where id = ?";
				ps = con.prepareStatement(material_Delete);
				ps.setInt(1, idd);
				int i = ps.executeUpdate();
				
				
				if(i == 1)
				{
					request.getSession().setAttribute("successDelete", "Material Deleted Successfully");
					
					response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					request.getSession().setAttribute("ErrorDelete", "Error Deleting the Material");
					
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
			request.getSession().setAttribute("loginFirst", "Please login to update materials...");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
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
