import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class PreviewEditMaterial
 */
@WebServlet("/PreviewEditMaterial")
public class PreviewEditMaterial extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreviewEditMaterial() 
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
		Methods methods = new Methods();
		
		String id = request.getParameter("id");
		
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			out.println("<html>");
		    out.println("<head><title>Materials</title>");
		    
		    out.println("<link rel=\"stylesheet\" href=\"css/bootstrap.min.css\">");
		    out.println("<script src=\"js/bootstrap.min.js\"></script>");
		    out.println("<script src=\"jquery.min.js\"></script>");
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    out.println("<center><h1>Materials</h1>");
			
			try
			{
				con = Methods.getConnection();
				int idd = methods.intPinConversion(id);
				
				
				String materials = "select * from materials where id=?";
				ps = con.prepareStatement(materials);
				ps.setInt(1, idd);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				 if (!rs.isBeforeFirst())
				 {
					 out.println("<script> type=\"text/javascript\">");
		    		 out.print("alert('No Materials Saved');");
		    		 out.print("location='homeadmin.jsp';");
		    		 out.println("</script>");
				 }
				 
				 else
				 {
					 while(rs.next())
						{
							 int iddd = rs.getInt("id");
							 String materialss = rs.getString("materials");
							 Double unit_price = rs.getDouble("unit_price");
							 
							 request.getSession(true).setAttribute("iddd", iddd);
							 request.getSession(true).setAttribute("materialss", materialss);
							 request.getSession(true).setAttribute("unit_price", unit_price);
							 
							 response.sendRedirect("/jobcard/materialsEdit.jsp");
						}
				 }
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homeadmin.jsp';");
	    		out.println("</script>");
	    		  
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst","Need to be Admin to Proceed");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
		
		out.println("</center>");
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
