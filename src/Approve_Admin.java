import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Approve_Admin
 */
@WebServlet("/Approve_Admin")
public class Approve_Admin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Approve_Admin() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Methods methods = new Methods();
	
		int jobcard_no = (int) request.getSession().getAttribute("job_card_noo");
		
		int m = methods.approve_Admin_Jobcard(jobcard_no, request, response);
		
		if (m == 1)
		{
			request.getSession().setAttribute("ApprovalSuccessful", "Approval Successful");
	        
			response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			request.getSession().setAttribute("ApprovalError", "Approval Error");
	        
			response.sendRedirect(request.getHeader("Referer"));
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
