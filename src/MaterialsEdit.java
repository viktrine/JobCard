import java.io.IOException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class MaterialsEdit
 */
@WebServlet("/MaterialsEdit")
public class MaterialsEdit extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaterialsEdit() 
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
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String material = request.getParameter("material");
		String unit_price = request.getParameter("unit_price");
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
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
					
					int iddd = (int) request.getSession(false).getAttribute("iddd");
					
					try
					{
						double unit_prices = methods.doublePinConversion(unit_price);
						
						String materials_enter = "update materials set materials=?,unit_price=? where id=?";
						ps = con.prepareStatement(materials_enter);
						
						ps.setString(1, material);
						ps.setDouble(2, unit_prices);
						ps.setInt(3, iddd);
						
						int m = ps.executeUpdate();
						
						if (m != 1)
						{
							request.getSession().setAttribute("materialError", "Error up dating the material.");
							
							response.sendRedirect("/jobcard/View_Materials");
						}
						else
						{
							request.getSession().setAttribute("materialedited", "Materials updated successfully");
							
							response.sendRedirect("/jobcard/View_Materials");
						}
					
					}
					catch (Exception e)
					{
						request.getSession().setAttribute("doubleError", "Double Error");
						
						response.sendRedirect(request.getHeader("Referer"));
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
			request.getSession().setAttribute("loginFirst", "Please login to update materials...");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
		
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
