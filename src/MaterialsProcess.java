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
 * Servlet implementation class MaterialsProcess
 */
@WebServlet("/MaterialsProcess")
public class MaterialsProcess extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaterialsProcess() 
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
		
		String[] material = request.getParameterValues("material");
		String[] unit_price = request.getParameterValues("unit_price");
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("adminLog");
			
			if(material == null|| unit_price == null)
			{
				request.getSession().setAttribute("nullError", "Please provide atleast one the materials and the unit price");
				
				response.sendRedirect(request.getHeader("Referer"));
			}
			else
			{
				try
				{
					con = Methods.getConnection();
					
					for (int i = 0; i < material.length; i++)
					{
						try
						{
							double unit_prices = methods.doublePinConversion(unit_price[i]);
							
							String materials_enter = "insert into materials (materials,unit_price,admin_id) values(?,?,?)";
							ps = con.prepareStatement(materials_enter);
							
							ps.setString(1, material[i]);
							ps.setDouble(2, unit_prices);
							ps.setInt(3, idn);
							
							int m = ps.executeUpdate();
							
							if (m < 1)
							{
								request.getSession().setAttribute("materialError", "Error posting the materials.");
								
								response.sendRedirect(request.getHeader("Referer"));
							}
						
						}
						catch (Exception e)
						{
							request.getSession().setAttribute("doubleError", "Double Error");
							
							response.sendRedirect(request.getHeader("Referer"));
						}
					}
					
					
					request.getSession().setAttribute("materialPosted", "Materials saved successfully");
					
					response.sendRedirect(request.getHeader("Referer"));
					
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
			request.getSession().setAttribute("loginFirst", "Please login to enter materials...");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
		
	}

}
