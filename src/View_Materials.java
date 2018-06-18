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
 * Servlet implementation class View_Materials
 */
@WebServlet("/View_Materials")
public class View_Materials extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public View_Materials() 
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
		
		RequestDispatcher menu = request.getRequestDispatcher("menuadmin.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			String materialError = (String) request.getSession().getAttribute("materialError");
			String materialedited = (String) request.getSession().getAttribute("materialedited");
			String successDelete = (String) request.getSession().getAttribute("successDelete");
			String ErrorDelete = (String) request.getSession().getAttribute("ErrorDelete");
			
			
			out.println("<html>");
		    out.println("<head><title>Materials</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    if(materialError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+materialError+"</font>");
		    	request.getSession().removeAttribute("materialError");
		    }
		    
		    if(materialedited != null)
		    {
		    	out.println("<font color=\"00EE76\">"+materialedited+"</font>");
		    	request.getSession().removeAttribute("materialedited");
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
		    
			try
			{
				con = Methods.getConnection();
				
				String materials = "select * from materials order by id";
				ps = con.prepareStatement(materials);
				
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
					 int counter = 1;
					 
					 out.println("<center><h1>Materials</h1>");
					 
					 out.print("<table width=25% border=1>");
					 
					 out.print("<tr><td nowrap><b>No.</b>&nbsp; &nbsp; &nbsp; &nbsp;</td><td nowrap><b>Materials</b></td><td nowrap>&nbsp; &nbsp; &nbsp; &nbsp;<b>Unit Price</b>&nbsp; &nbsp; &nbsp; &nbsp;</td><td nowrap>&nbsp; &nbsp; &nbsp; &nbsp;<b>Action</b></td></tr>");
					 
					 while(rs.next())
					{
						 int id = rs.getInt("id");
						 String materialss = rs.getString("materials");
						 Double unit_price = rs.getDouble("unit_price");
						 
						 out.print("<tr><td nowrap>"+counter+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+materialss+" &nbsp; &nbsp; &nbsp; &nbsp;</td><td nowrap style=\"text-align: right\">"+unit_price+"</td>"
						 		+ "<td nowrap>"
						 		+ "&nbsp; &nbsp; &nbsp; &nbsp; <a href='PreviewEditMaterial?id="+id+"' title='Update Material' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
						 		+ "<a href='DeleteMaterial?id="+id+"' onclick=\"return confirm('Are you sure you want to delete the material?')\" title='Delete Material' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a></td></tr>");
						 
						 counter++;
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
