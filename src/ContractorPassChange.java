import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ContractorPassChange
 */
@WebServlet("/ContractorPassChange")
public class ContractorPassChange extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContractorPassChange() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		
		Methods methods = new Methods();
		
		String newpass = request.getParameter("newpass");
		String repeatnewpass = request.getParameter("repeatnewpass");
		
		int idn = (int) request.getSession().getAttribute("contractorLog");
		
		methods.changePass(idn, "manager_id", newpass, repeatnewpass, "contractorPass", "/jobcard/homecontractor.jsp",request, response);
		
	}

}
