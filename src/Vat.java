

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class Vat
 */
@WebServlet("/Vat")
public class Vat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Vat() {
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
		PrintWriter out = response.getWriter();
		
		Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		
		String datefrom = request.getParameter("datefrom");
		String dateto = request.getParameter("dateto");
		String amount = request.getParameter("amount");
		
		if (request.getSession().getAttribute("contractorLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("contractorLog");
			
			if ((datefrom == null || datefrom.isEmpty()) || (dateto == null || dateto.isEmpty()) || (amount == null || amount.isEmpty()))
			{
				request.getSession().setAttribute("nullError", "Provide all fields to process your VAT");
	            
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
					
					double vat = methods.doublePinConversion(amount);
					
					try
					{
						String vat_value = "insert into vat (vat_value,from_date,to_date,contractor_id) values(?,?,?,?)";
						ps = con.prepareStatement(vat_value);
						
						ps.setDouble(1, vat);
						ps.setTimestamp(2, (Timestamp) sqldatefromdate);
						ps.setTimestamp(3, (Timestamp) sqldatetodate);
						ps.setInt(4, idn);
						
						ps.executeUpdate();
						
						request.getSession().setAttribute("vat_success", "You have successfully submitted the monthly VAT value");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					catch (Exception e)
					{
						request.getSession().setAttribute("vat_error", "Error providing the VAT value");
			            
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
			request.getSession().setAttribute("loginFirstVat", "Please login to submit VAT value for the particular period");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
		}
	}

}
