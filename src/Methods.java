import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import java.util.Random;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Methods 
{
	private static String url = "jdbc:mysql://localhost:3306/Fixed?zeroDateTimeBehavior=convertToNull";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "Brilliant31";

    
    private static Connection con;
    PreparedStatement ps = null;
	
	public Timestamp getDays()
    {
    	long retryDate = System.currentTimeMillis();

        int sec = 60 * 24 * 60 * 60;

        Timestamp original = new Timestamp(retryDate);
        
        Calendar cal = Calendar.getInstance();
        
        cal.setTimeInMillis(original.getTime());
        
        cal.add(Calendar.SECOND, sec);
        
        Timestamp later = new Timestamp(cal.getTime().getTime());

        return later;
    }
	
    public static Connection getConnection() 
    {
        try {
            Class.forName(driverName);
            try 
            {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } 
            catch (SQLException ex) 
            {
                System.out.println("Failed to create the database connection."); 
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Missing Database DRiver."); 
        }
        return con;
    }
    
    public int intPinConversion(String x)
    {
    	int integer = 0;
    	
    	integer = Integer.parseInt(x);
    	
    	return integer;
    }
    
    public double doublePinConversion(String unit_price)
    {
    	double doub = 0;
    	
    	doub = Double.parseDouble(unit_price);
    	
    	return doub;
    }
    
    
    public String tohash(String password) throws NoSuchAlgorithmException
    {
    	StringBuffer sb = new StringBuffer(); 
    	try
    	{
    		MessageDigest md = MessageDigest.getInstance("MD5");
        	
        	md.update(password.getBytes());
        	
        	byte byteData[] = md.digest();
        	
        	
        	
        	for (int i=0; i < byteData.length; i++) 
        	{
        		sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        	}
        	
        	
    	}
    	catch (Exception e)
    	{
    		System.out.println("Error..");
    	}
    	return sb.toString();
    	
    }
    
    public String randPass(int wordLength)
    {
    	Random r = new Random();
    	StringBuilder sb = new StringBuilder(wordLength);
    	
    	for (int i = 0; i < wordLength;i++)
    	{
    		char tmp = (char) ('a' + r.nextInt('z' - 'a'));
    		sb.append(tmp);
    	}
    	
    	return sb.toString();
    	
    }
    
    public void resetPassFieldEngineer(String email, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement psss = null;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		if ((email == null || email.isEmpty()))
		{
			
			request.getSession(true).setAttribute("nullError", "Please provide an email address");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from fieldEngineerDetails where email=?";
				ps = con.prepareStatement(selects);
				ps.setString(1, email);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				if(!rs.next())
				{
					request.getSession(true).setAttribute("EmailError", "User do not exist");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					int idn = rs.getInt("id");
					
					
					String passs = "select * from  fieldEngineerPass where  fieldEngineerDetails_id=?";
					pss = con.prepareStatement(passs);
					pss.setInt(1, idn);
					
					ResultSet rss = (ResultSet) pss.executeQuery();
					
					if(!rss.next())
					{
						request.getSession(true).setAttribute("EmailError", "Wrong Email provided");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					else
					{
						String passwordrand = methods.randPass(10);
						String password = methods.tohash(passwordrand);
						
						String updates = "update fieldEngineerPass set pass=?, expire_date=? where  fieldEngineerDetails_id=?";
						psss = con.prepareStatement(updates);
						
						psss.setString(1, password);
						psss.setTimestamp(2, currentTime);
						psss.setInt(3, idn);
						
						int i = psss.executeUpdate();
						
						if(i != 0)
						{
							request.getSession(true).setAttribute("updateSucess", passwordrand);
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						else
						{
							request.getSession(true).setAttribute("ResetError", "Error reseting Password");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
					}
					
				}
				
			}
			  
			  
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error connecting to the database");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
	}
    
    public void resetPassContractor(String email, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement psss = null;
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		if ((email == null || email.isEmpty()))
		{
			
			request.getSession(true).setAttribute("nullError", "Please provide an email address");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from managerDetails where email=?";
				ps = con.prepareStatement(selects);
				ps.setString(1, email);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				if(!rs.next())
				{
					request.getSession(true).setAttribute("EmailError", "User do not exist");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					int idn = rs.getInt("id");
					
					
					String passs = "select * from  contractorPass where  manager_id=?";
					pss = con.prepareStatement(passs);
					pss.setInt(1, idn);
					
					ResultSet rss = (ResultSet) pss.executeQuery();
					
					if(!rss.next())
					{
						request.getSession(true).setAttribute("EmailError", "Wrong Email provided");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					else
					{
						String passwordrand = methods.randPass(10);
						String password = methods.tohash(passwordrand);
						
						String updates = "update contractorPass set pass=?, expire_date=? where  manager_id=?";
						psss = con.prepareStatement(updates);
						
						psss.setString(1, password);
						psss.setTimestamp(2, currentTime);
						psss.setInt(3, idn);
						
						int i = psss.executeUpdate();
						
						if(i != 0)
						{
							request.getSession(true).setAttribute("updateSucess", passwordrand);
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						else
						{
							request.getSession(true).setAttribute("ResetError", "Error reseting Password");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
					}
					
				}
				
			}
			  
			  
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error connecting to the database");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
	}
    
    public void resetPassEngineer(String email, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Methods methods = new Methods();
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement psss = null;
		
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		
		if ((email == null || email.isEmpty()))
		{
			
			request.getSession(true).setAttribute("nullError", "Please provide an email address");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from engineerDetails where email=?";
				ps = con.prepareStatement(selects);
				ps.setString(1, email);
				ResultSet rs = (ResultSet) ps.executeQuery();
		
				if(!rs.isBeforeFirst())
				{
					request.getSession(true).setAttribute("EmailError", "User do not exist");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					while (rs.next())
					{
						int idn = rs.getInt("id");
						
						String passs = "select * from engineerPass where engineer_id=?";
						pss = con.prepareStatement(passs);
						pss.setInt(1, idn);
						
						ResultSet rss = (ResultSet) pss.executeQuery();
						
						if(!rss.isBeforeFirst())
						{
							request.getSession(true).setAttribute("EmailError", "Wrong Email provided");
				            
				    		response.sendRedirect(request.getHeader("Referer"));
						}
						else
						{
							String passwordrand = methods.randPass(10);
							String password = methods.tohash(passwordrand);
							
							String updates = "update engineerPass set pass=?, expire_date=? where engineer_id=?";
							psss = con.prepareStatement(updates);
							
							psss.setString(1, password);
							psss.setTimestamp(2, currentTime);
							psss.setInt(3, idn);
							
							int i = psss.executeUpdate();
							
							if(i != 0)
							{
								request.getSession(true).setAttribute("updateSucess",passwordrand);
					            
					    		response.sendRedirect(request.getHeader("Referer"));
							}
							else
							{
								request.getSession(true).setAttribute("ResetError", "Error reseting Password");
					            
					    		response.sendRedirect(request.getHeader("Referer"));
							}
						}
					}
				}
				
			}
			  
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error connecting to the database");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
	}
    
    public void changePass(int ses, String idd,String newpass, String newRepeat, String tabledata, String refer, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Methods methods = new Methods();
    	
		Connection con = null;
		PreparedStatement pss = null;
		
		java.sql.Timestamp expire = methods.getDays();
			
		if ((newpass == null || newpass.isEmpty()) || (newRepeat == null || newRepeat.isEmpty()) )
		{
			request.getSession(true).setAttribute("nullError", "All fields are required");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		else
		{
			if(newpass.equals(newRepeat))
			{
				try
				{
					con = Methods.getConnection();
					
					String password = methods.tohash(newpass);
					
					String updates = "update `"+tabledata+"` set pass=?, expire_date=? where `"+idd+"`=?";
					pss = con.prepareStatement(updates);
					
					pss.setString(1, password);
					pss.setTimestamp(2, expire);
					pss.setInt(3, ses);
					
					
					int i = pss.executeUpdate();
					
					if(i != 0)
					{
						request.getSession(true).setAttribute("updateSucess", "Password changed successfully");
			            
			    		response.sendRedirect(refer);
					}
					else
					{
						request.getSession(true).setAttribute("updateError", "Password Unchanged");
			            
			    		response.sendRedirect(request.getHeader("Referer"));
					}
					
				}
					
				catch (Exception e)
				{
					request.getSession(true).setAttribute("databaseConnectionError", "Error connecting to the database");
		            
		    		response.sendRedirect(request.getHeader(refer));
				}
			}
			else
			{
				request.getSession().setAttribute("passDiffError", "Password mismatch");
	    		response.sendRedirect(request.getHeader("Referer"));
			}
			
		}
		
    }
    
    public void getAdminDetails(int idnn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
		Connection con = null;
		PreparedStatement ps = null;
		
		try
		{
			
			con = Methods.getConnection();
			
			String selects = "select * from adminDetails inner join adminPass where adminDetails.id =?";
			ps = con.prepareStatement(selects);
			ps.setInt(1, idnn);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String adminfname = rs.getString("fname");
				String adminoname = rs.getString("oname");
				String adminemail = rs.getString("email");
				String adminphone = rs.getString("phone");
				String adminidpp_no = rs.getString("idpp_no");
				String adminstaff_no = rs.getString("staff_no");
				String admincompany_name = rs.getString("company_name");
				Timestamp adminregistration_date = rs.getTimestamp("registration_date");
				
				
				request.getSession(true).setAttribute("adminfname", adminfname);
				request.getSession(true).setAttribute("adminoname", adminoname);
				request.getSession(true).setAttribute("adminemail", adminemail);
				request.getSession(true).setAttribute("adminphone", adminphone);
				request.getSession(true).setAttribute("adminidpp_no", adminidpp_no);
				request.getSession(true).setAttribute("adminstaff_no", adminstaff_no);
				request.getSession(true).setAttribute("admincompany_name", admincompany_name);
				request.getSession(true).setAttribute("adminregistration_date", adminregistration_date);
				
			}
		}
		catch (Exception e)
		{
			
		}
		
    }
    
    public void getEngineerDetails(int idnn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
		Connection con = null;
		PreparedStatement ps = null;
		
		try
		{
			
			con = Methods.getConnection();
			
			String selects = "select * from engineerDetails inner join engineerPass where engineerDetails.id =?";
			ps = con.prepareStatement(selects);
			ps.setInt(1, idnn);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String engineerfname = rs.getString("fname");
				String engineeroname = rs.getString("oname");
				String engineeremail = rs.getString("email");
				String engineerphone = rs.getString("phone_no");
				String engineeridpp_no = rs.getString("idpp_no");
				String engineerstaff_no = rs.getString("staff_id");
				String engineercompany_name = rs.getString("company");
				Timestamp engineerregistration_date = rs.getTimestamp("registration_date");
				
				
				request.getSession(true).setAttribute("engineerfname", engineerfname);
				request.getSession(true).setAttribute("engineeroname", engineeroname);
				request.getSession(true).setAttribute("engineeremail", engineeremail);
				request.getSession(true).setAttribute("engineerphone", engineerphone);
				request.getSession(true).setAttribute("engineeridpp_no", engineeridpp_no);
				request.getSession(true).setAttribute("engineerstaff_no", engineerstaff_no);
				request.getSession(true).setAttribute("engineercompany_name", engineercompany_name);
				request.getSession(true).setAttribute("engineerregistration_date", engineerregistration_date);
				
			}
		}
		catch (Exception e)
		{
			
		}
		
    }
    
    public void getFieldEngineerDetails(int idnn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		
		try
		{
			
			con = Methods.getConnection();
			
			String selects = "select * from fieldEngineerDetails where fieldEngineerDetails.id =?";
			ps = con.prepareStatement(selects);
			ps.setInt(1, idnn);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String fieldfname = rs.getString("fname");
				String fieldoname = rs.getString("oname");
				String fieldphone_no = rs.getString("phone_no");
				String fieldemail = rs.getString("email");
				String fieldstaff_id = rs.getString("staff_id");
				int fieldRegcontractor_id = rs.getInt("contractor_id");
				int engineer_id = rs.getInt("engineer_id");
				Timestamp fieldregistration_date = rs.getTimestamp("registration_date");
				
				
				request.getSession(true).setAttribute("engineer_id", engineer_id);
				request.getSession(true).setAttribute("fieldfname", fieldfname);
				request.getSession(true).setAttribute("fieldoname", fieldoname);
				request.getSession(true).setAttribute("fieldphone_no", fieldphone_no);
				request.getSession(true).setAttribute("fieldemail", fieldemail);
				request.getSession(true).setAttribute("fieldstaff_id", fieldstaff_id);
				request.getSession(true).setAttribute("fieldRegcontractor_id", fieldRegcontractor_id);
				request.getSession(true).setAttribute("fieldregistration_date", fieldregistration_date);
				
				String contractorselect = "select * from contractorDetails where id =?";
				pss = con.prepareStatement(contractorselect);
				pss.setInt(1, fieldRegcontractor_id);
				
				ResultSet rss = pss.executeQuery();
				
				while (rss.next())
				{
					String regcontractorname = rss.getString("cname");
					
					request.getSession(true).setAttribute("regcontractorname", regcontractorname);
				}
				
				
			}
			
			
		}
		catch (Exception e)
		{
			
		}
		
    }
    
    public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
				
		return cal.getTime();
	}
    
    public static Date subtractSecond(Date date, int seconds) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
				
		return cal.getTime();
	}
    
    public void getContractorDetails(int idnn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
		Connection con = null;
		PreparedStatement ps = null;
		
		try
		{
			con = Methods.getConnection();
			
			String selects = "select * from managerDetails inner join contractorDetails on managerDetails.id = contractorDetails.manager_id where managerDetails.id =?";
			ps = con.prepareStatement(selects);
			ps.setInt(1, idnn);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String contractorfname = rs.getString("fname");
				String contractoroname = rs.getString("oname");
				String contractoremail = rs.getString("email");
				String contractorpin = rs.getString("kra_pin");
				String contractorstaffid = rs.getString("staffid");
				Timestamp contractorregistration_date = rs.getTimestamp("registration_date");
				int contractoridd = rs.getInt("contractorDetails.id");
				String contractorcname = rs.getString("cname");
				String contractorcontact_phone = rs.getString("contact_phone");
				String kra_pin = rs.getString("kra_pin");
				String vat_no = rs.getString("vat_no");
				
				
				request.getSession(true).setAttribute("contractoridd", contractoridd);
				request.getSession(true).setAttribute("contractorpin", contractorpin);
				request.getSession(true).setAttribute("contractorfname", contractorfname);
				request.getSession(true).setAttribute("contractoroname", contractoroname);
				request.getSession(true).setAttribute("contractoremail", contractoremail);
				request.getSession(true).setAttribute("contractorstaffid", contractorstaffid);
				request.getSession(true).setAttribute("contractorregistration_date", contractorregistration_date);
				request.getSession(true).setAttribute("contractorcname", contractorcname);
				request.getSession(true).setAttribute("contractorcontact_phone", contractorcontact_phone);
				request.getSession(true).setAttribute("kra_pin", kra_pin);
				request.getSession(true).setAttribute("vat_no", vat_no);
				
			}
		}
		catch (Exception e)
		{
			
		}
		
    }
    
    public void get_Jobcard_Status(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		
		int counts = 0;
		int count_0 = 0;
		int count_1 = 0;
		int count_2 = 0;
		int count_3 = 0;
		int count_4 = 0;
		int count_5 = 0;
		int count_6 = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String selects = "select * from jobcard_details";
			ps = con.prepareStatement(selects);
			
			ResultSet r = ps.executeQuery();
			
			while(r.next())
			{
				counts +=1;
			}
			request.getSession(true).setAttribute("counts", counts);
			
			
			String select_0 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_0);
			ps.setInt(1, 0);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				count_0 +=1;
			}
			request.getSession(true).setAttribute("count_0", count_0);
			
			String select_1 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_1);
			ps.setInt(1, 1);
			
			ResultSet rss = ps.executeQuery();
			
			while(rss.next())
			{
				count_1 +=1;
			}
			request.getSession(true).setAttribute("count_1", count_1);
			
			String select_2 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_2);
			ps.setInt(1, 2);
			
			ResultSet rsss = ps.executeQuery();
			
			while(rsss.next())
			{
				count_2 +=1;
			}
			request.getSession(true).setAttribute("count_2", count_2);
			
			String select_3 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_3);
			ps.setInt(1, 3);
			
			ResultSet rssss = ps.executeQuery();
			
			while(rssss.next())
			{
				count_3 +=1;
			}
			request.getSession(true).setAttribute("count_3", count_3);
			
			String select_4 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_4);
			ps.setInt(1, 4);
			
			ResultSet rsssss = ps.executeQuery();
			
			while(rsssss.next())
			{
				count_4 +=1;
			}
			request.getSession(true).setAttribute("count_4", count_4);
			
			String select_5 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_5);
			ps.setInt(1, 5);
			
			ResultSet rssssss = ps.executeQuery();
			
			while(rssssss.next())
			{
				count_5 +=1;
			}
			request.getSession(true).setAttribute("count_5", count_5);
			
			String select_6 = "select * from jobcard_details where status=?";
			ps = con.prepareStatement(select_6);
			ps.setInt(1, 6);
			
			ResultSet rsssssss = ps.executeQuery();
			
			while(rsssssss.next())
			{
				count_6 +=1;
			}
			request.getSession(true).setAttribute("count_6", count_6);
		
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
    }
    
    public int approve_contractor_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 1);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public int approve_Engineer_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 3);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public int approve_Admin_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 5);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public int reject_contractor_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 2);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public int reject_Engineer_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 4);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public int reject_Admin_Jobcard(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	Connection con = null;
		PreparedStatement ps = null;
		int m = 0;
		
		try
		{
			con = Methods.getConnection();
			
			String approve = "update jobcard_details set status =? where jobcard_no =?";
			ps = con.prepareStatement(approve);
			ps.setInt(1, 6);
			ps.setInt(2, status_int);
			
			m = ps.executeUpdate();
			
		}
		catch (Exception e)
		{
			request.getSession().setAttribute("DatabaseError", "Database Error, contact System Admin...");
			
			response.sendRedirect(request.getHeader("Referer"));
		}
		return m;
    }
    
    public void Field_Jobcard_Status(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
    	
    	RequestDispatcher menu = request.getRequestDispatcher("menufield.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("fieldengineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("fieldengineerLog");
			
			String itemnullError = (String) request.getSession().getAttribute("itemnullError");
			String successDelete = (String) request.getSession().getAttribute("successDelete");
			String ErrorDelete = (String) request.getSession().getAttribute("ErrorDelete");
			String DatabaseError = (String) request.getSession().getAttribute("DatabaseError");
			String no_Details = (String) request.getSession().getAttribute("no_Details");
			
			out.println("<html>");
		    out.println("<head><title>My Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    if(itemnullError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+itemnullError+"</font>");
		    	request.getSession().removeAttribute("itemnullError");
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
		    if(DatabaseError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+DatabaseError+"</font>");
		    	request.getSession().removeAttribute("DatabaseError");
		    }
		    if(no_Details != null)
		    {
		    	out.println("<font color=\"FF0000\">"+no_Details+"</font>");
		    	request.getSession().removeAttribute("no_Details");
		    }
		    
		    out.println("<center><h1>Job Cards</h1>");
			
			try
			{
				con = Methods.getConnection();
				
				String jobcard_details = "select DISTINCT jobcard_details.jobcard_no,jobcard_details.company,jobcard_details.ticket,jobcard_details.service,jobcard_details.report,jobcard_details.date_filled from jobcard_details where user_id = ? and status = ? group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				ps.setInt(1, idn);
				ps.setInt(2, status_int);
				
				
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				
				 if (!rs.isBeforeFirst())
				 {
					 out.println("<script> type=\"text/javascript\">");
		    		 out.print("alert('No Jobcards Found');");
		    		 out.print("location='homefieldengineer.jsp';");
		    		 out.println("</script>");
				 }
				 
				 else
				 {
					 out.print("<table width=25% border=1>");
					 
					 out.print("<tr><td nowrap><b>Job Card</b></td><td nowrap><b>Company</b></td><td nowrap><b>Ticket</b></td><td nowrap><b>Service</b></td><td nowrap><b>Report</b></td><td nowrap><b>Date Raised</b></td>"
						 		+ "<td nowrap><b>Action</b></td></tr>");
					 
					 while(rs.next())
						{
							 int job_card_no = rs.getInt("jobcard_details.jobcard_no");
							 String company = rs.getString("jobcard_details.company");
							 String ticket = rs.getString("jobcard_details.ticket");
							 String service = rs.getString("jobcard_details.service");
							 String report = rs.getString("jobcard_details.report");
							 String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));

							 /* double billable = rs.getDouble("jobcard_charges.billable");
							 double reschedule = rs.getDouble("jobcard_charges.reschedule");
							

							String date_raiseed = df_day.format(rs.getTimestamp("left_office"));
							 
							 String left_office = df_day.format(rs.getTimestamp("jobcard_time.left_office"));
							 String arrive_client = df_day.format(rs.getTimestamp("jobcard_time.arrive_client"));
							 String left_client = df_day.format(rs.getTimestamp("jobcard_time.left_client"));
							 String arrive_office = df_day.format(rs.getTimestamp("jobcard_time.arrive_office"));
							*/
							 out.print("<tr><td nowrap>"+job_card_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+service+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+report+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td>"
							 		+ "<td nowrap>"
							 		+ "<a href='ViewParticularJobcardField?job_card_no="+job_card_no+"' title='View Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
							 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Job Card' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
							 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a></td></tr>");
						}
				 }
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homefieldengineer.jsp';");
	    		out.println("</script>");
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to view your Job Cards");
            
    		response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
    }
    
    public void Engineer_Jobcard_Status(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
    	
    	RequestDispatcher menu = request.getRequestDispatcher("menuengineer.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("engineerLog") != null || request.getSession().getAttribute("adminLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("engineerLog");
			
			out.println("<html>");
		    out.println("<head><title>My Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    out.println("<center><h1>Job Cards</h1>"); 
		    
		    
			try
			{
				con = Methods.getConnection();
				
				String jobcard_details = "select * from jobcard_details where engineer_id = ? and status = ? group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				ps.setInt(1, idn);
				ps.setInt(2, status_int);
				
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				out.print("<table width=25% border=1>");
		    	  
				
				if(!rs.isBeforeFirst())
				{
					out.println("<script> type=\"text/javascript\">");
		    		out.print("alert('No record found');");
		    		out.print("location='homeengineer.jsp';");
		    		out.println("</script>");
				}
				else
				{
					 out.print("<tr><td nowrap><b>Job Card</b></td><td nowrap><b>Company</b></td><td nowrap><b>Ticket</b></td><td nowrap><b>Service</b></td><td nowrap><b>Report</b></td><td nowrap><b>Date Raised</b></td>"
					 		+ "<td nowrap><b>Action</b></td></tr>");
					 
					while(rs.next())
					{
						 int job_card_no = rs.getInt("jobcard_details.jobcard_no");
						 String company = rs.getString("jobcard_details.company");
						 String ticket = rs.getString("jobcard_details.ticket");
						 String service = rs.getString("jobcard_details.service");
						 String report = rs.getString("jobcard_details.report");
						 String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));

						 /* double billable = rs.getDouble("jobcard_charges.billable");
						 double reschedule = rs.getDouble("jobcard_charges.reschedule");
						

						String date_raiseed = df_day.format(rs.getTimestamp("left_office"));
						 
						 String left_office = df_day.format(rs.getTimestamp("jobcard_time.left_office"));
						 String arrive_client = df_day.format(rs.getTimestamp("jobcard_time.arrive_client"));
						 String left_client = df_day.format(rs.getTimestamp("jobcard_time.left_client"));
						 String arrive_office = df_day.format(rs.getTimestamp("jobcard_time.arrive_office"));
						*/
						 out.print("<tr><td nowrap>"+job_card_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+service+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+report+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td>"
						 		+ "<td nowrap>"
						 		+ "<a href='ViewParticularJobcardEngineer?job_card_no="+job_card_no+"' title='View Record' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
						 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Record' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
						 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a>&nbsp; &nbsp; &nbsp;</td></tr>");
							}
				}
				
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.print("location='homeengineer.jsp';");
	    		out.println("</script>");
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to view your Job Cards");
            
    		response.sendRedirect("/jobcard/engineerlogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
    }
    
    public void Contractor_Jobcard_Status(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
    	
    	RequestDispatcher menu = request.getRequestDispatcher("menucontractor.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		Methods methods = new Methods();
		
		if (request.getSession().getAttribute("contractorLog") != null || request.getSession().getAttribute("engineerLog") != null)
		{
			int idn = (int) request.getSession().getAttribute("contractorLog");
			
			String itemnullError = (String) request.getSession().getAttribute("itemnullError");
			String successDelete = (String) request.getSession().getAttribute("successDelete");
			String ErrorDelete = (String) request.getSession().getAttribute("ErrorDelete");
			String DatabaseError = (String) request.getSession().getAttribute("DatabaseError");
			
			
			out.println("<html>");
		    out.println("<head><title>My Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    
		    out.print("<div class=container>");
		    menu.include(request, response);
		    
		    if(itemnullError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+itemnullError+"</font>");
		    	request.getSession().removeAttribute("itemnullError");
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
		    if(DatabaseError != null)
		    {
		    	out.println("<font color=\"FF0000\">"+DatabaseError+"</font>");
		    	request.getSession().removeAttribute("DatabaseError");
		    }
		    
		    out.println("<center><h1>Job Cards</h1>"); 
		    
		    
			try
			{
				con = Methods.getConnection();
				
				methods.getContractorDetails(idn, request, response);
				
				int contractor_idd = (int) request.getSession(false).getAttribute("contractoridd");
		
				
				String jobcard_details = "select * from jobcard_details where contractor_id = ? and status = ? group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				ps.setInt(1, contractor_idd);
				ps.setInt(2, status_int);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				out.print("<table width=25% border=1>");
		    	  
		    	
				if(!rs.isBeforeFirst())
				{
					out.println("<script> type=\"text/javascript\">");
		    		out.print("alert('No record found');");
		    		out.print("location='homecontractor.jsp';");
		    		out.println("</script>");
		    		  
				}
				else
				{
					 out.print("<tr><td nowrap><b>Job Card</b></td><td nowrap><b>Company</b></td><td nowrap><b>Ticket</b></td><td nowrap><b>Service</b></td><td nowrap><b>Report</b></td><td nowrap><b>Date Raised</b></td>"
					 		+ "<td nowrap><b>Action</b></td></tr>");
					 
					while(rs.next())
					{
						 int job_card_no = rs.getInt("jobcard_details.jobcard_no");
						 String company = rs.getString("jobcard_details.company");
						 String ticket = rs.getString("jobcard_details.ticket");
						 String service = rs.getString("jobcard_details.service");
						 String report = rs.getString("jobcard_details.report");
						 String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));

						 out.print("<tr><td nowrap>"+job_card_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+service+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+report+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td>"
						 		+ "<td nowrap>"
						 		+ "<a href='ViewParticularJobcardContractor?job_card_no="+job_card_no+"' title='View Record' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
						 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Record' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
						 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a></td></tr>");
							}
				}
				
			}
			catch (Exception e) 
			{
				out.println("<script> type=\"text/javascript\">");
	    		out.print("alert('Error connecting to the database');");
	    		out.println("</script>");
	    		  
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		else
		{
			request.getSession().setAttribute("loginFirst", "Please login to view your Job Cards");
            
    		response.sendRedirect("/jobcard/contractorlogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
    }
    
    public void Admin_Jobcard_Status(int status_int, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	response.setContentType("text/html");
    	
    	RequestDispatcher menu = request.getRequestDispatcher("menuadmin.jsp");
		RequestDispatcher cdnpage = request.getRequestDispatcher("cdn.jsp");
		
		PrintWriter out = response.getWriter();
		Connection con = null;
		PreparedStatement ps = null;
		
		
		if (request.getSession().getAttribute("adminLog") != null)
		{
			out.println("<html>");
		    out.println("<head><title>Job Cards</title>");
		    
		    cdnpage.include(request, response);
		    
		    out.println("</head>");
		    
		    out.println("<body>");
		    out.print("<div class=container>");
		    menu.include(request, response);
		    out.println("<center><h1>Job Cards</h1>");
			
			try
			{
				con = Methods.getConnection();
				
				String jobcard_details = "select DISTINCT jobcard_details.jobcard_no,jobcard_details.company,jobcard_details.ticket,jobcard_details.service,jobcard_details.report,jobcard_details.date_filled from jobcard_details where status = ? group by jobcard_details.jobcard_no order by jobcard_details.jobcard_no";
				ps = con.prepareStatement(jobcard_details);
				ps.setInt(1, status_int);
				
				ResultSet rs = (ResultSet) ps.executeQuery();
				
				
				DateFormat df_day = new SimpleDateFormat("dd-MM-yyyy");
				
				
				 if (!rs.isBeforeFirst())
				 {
					 out.println("<script> type=\"text/javascript\">");
		    		 out.print("alert('No Jobcards Found');");
		    		 out.print("location='homeadmin.jsp';");
		    		 out.println("</script>");
				 }
				 
				 else
				 {
					 out.print("<table width=25% border=1>");
					 
					 out.print("<tr><td nowrap><b>Job Card</b></td><td nowrap><b>Company</b></td><td nowrap><b>Ticket</b></td><td nowrap><b>Service</b></td><td nowrap><b>Report</b></td><td nowrap><b>Date Raised</b></td>"
						 		+ "<td nowrap><b>Action</b></td></tr>");
					 
					 while(rs.next())
						{
							 int job_card_no = rs.getInt("jobcard_details.jobcard_no");
							 String company = rs.getString("jobcard_details.company");
							 String ticket = rs.getString("jobcard_details.ticket");
							 String service = rs.getString("jobcard_details.service");
							 String report = rs.getString("jobcard_details.report");
							 String date_raised = df_day.format(rs.getTimestamp("jobcard_details.date_filled"));

							 /* double billable = rs.getDouble("jobcard_charges.billable");
							 double reschedule = rs.getDouble("jobcard_charges.reschedule");
							

							String date_raiseed = df_day.format(rs.getTimestamp("left_office"));
							 
							 String left_office = df_day.format(rs.getTimestamp("jobcard_time.left_office"));
							 String arrive_client = df_day.format(rs.getTimestamp("jobcard_time.arrive_client"));
							 String left_client = df_day.format(rs.getTimestamp("jobcard_time.left_client"));
							 String arrive_office = df_day.format(rs.getTimestamp("jobcard_time.arrive_office"));
							*/
							 out.print("<tr><td nowrap>"+job_card_no+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+company+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+ticket+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+service+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+report+"&nbsp; &nbsp; &nbsp;</td><td nowrap>"+date_raised+"&nbsp; &nbsp; &nbsp;</td>"
							 		+ "<td nowrap>"
							 		+ "<a href='ViewParticularJobcardAdmin?job_card_no="+job_card_no+"' title='View Record' data-toggle='tooltip'><span class='glyphicon glyphicon-eye-open'></span></a> &nbsp; &nbsp; &nbsp;"
							 		+ "<a href='EditField_Jobcard?job_card_no="+job_card_no+"' title='Update Record' data-toggle='tooltip'> <span class='glyphicon glyphicon-pencil'></span></a> &nbsp; &nbsp; &nbsp;" 
							 		+ "<a href='DeleteField_Jobcard?job_card_no="+job_card_no+"' onclick=\"return confirm('Are you sure you want to delete the contact?')\" title='Delete Job Card' data-toggle='tooltip'><span class='glyphicon glyphicon-trash'></span> </a>&nbsp; &nbsp; &nbsp;</td></tr>");
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
			request.getSession().setAttribute("loginFirst", "Please login to view your Job Cards");
            
    		response.sendRedirect("/jobcard/adminlogin.jsp");
		}
		
		out.println("</center>");
		out.print("</div>");
	    out.println("</body>");
	    out.println("</html>");
	    out.close();
	
    }
    
    public void destroy_Sessions(String dest, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	if(request.getSession(false) != null)
    	{
    		request.getSession(false).invalidate();
    		
    		response.sendRedirect(dest);
    	}
    }
    
    public int Admin_login(String home, String dest, String useremail, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	int idn = 0;
    	
    	try
		{
    		Methods methods = new Methods();
    		
			String hashpassword = methods.tohash(password);
			
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from adminDetails inner join adminPass on adminDetails.id=adminPass.adminDetailsid where adminDetails.email=? and adminPass.pass=?";
				
				ps = con.prepareStatement(selects);
				ps.setString(1, useremail);
				ps.setString(2, hashpassword);
				
				ResultSet rs = ps.executeQuery();
				
				if(!rs.isBeforeFirst())
				{
					request.getSession(true).setAttribute("loginFail", "Email Password mismatchi!");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					methods.get_Jobcard_Status(request, response);
					
					while(rs.next())
					{
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						
						idn = rs.getInt("adminDetails.id");
						
						Timestamp expiring = rs.getTimestamp("adminPass.expire_date");
						
						if (currentTime.after(expiring))
						{
				    		response.sendRedirect(dest);
						}
						else 
						{
							request.getSession(true).setAttribute("loginSuccess", "Login Successful");
				            
							response.sendRedirect(home);
						}
					}
					
				}
			}
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error Establishing database connection");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		catch (Exception e)
		{
			request.getSession(true).setAttribute("passError", "Pass Hashing Error");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		
		return idn;
    }
    
    public int Engineer_login(String home, String dest, String useremail, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	int idn = 0;
    	
    	try
		{
    		Methods methods = new Methods();
    		
			String hashpassword = methods.tohash(password);
			
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from engineerDetails inner join engineerPass on engineerDetails.id = engineerPass.engineer_id where engineerDetails.email=? and engineerPass.pass=?";
				
				ps = con.prepareStatement(selects);
				ps.setString(1, useremail);
				ps.setString(2, hashpassword);
				
				ResultSet rs = ps.executeQuery();
				
				if(!rs.isBeforeFirst())
				{
					request.getSession(true).setAttribute("loginFail", "Email Password mismatchi!");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					while(rs.next())
					{
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						
						idn = rs.getInt("engineerDetails.id");
						
						
						Timestamp expiring = rs.getTimestamp("engineerPass.expire_date");
						
						if (currentTime.after(expiring))
						{
				    		response.sendRedirect(dest);
						}
						else 
						{
							request.getSession(true).setAttribute("loginSuccess", "Login Successful");
				            
							response.sendRedirect(home);
						}
					}
					
				}
			}
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error Establishing database connection");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		catch (Exception e)
		{
			request.getSession(true).setAttribute("passError", "Pass Hashing Error");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		
		return idn;
    }
    
    public int Contractor_login(String home, String dest, String useremail, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	int idn = 0;
    	
    	try
		{
    		Methods methods = new Methods();
    		
			String hashpassword = methods.tohash(password);
			
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from managerDetails inner join contractorPass on managerDetails.id = contractorPass.manager_id where managerDetails.email=? and contractorPass.pass=?";
				
				ps = con.prepareStatement(selects);
				ps.setString(1, useremail);
				ps.setString(2, hashpassword);
				
				ResultSet rs = ps.executeQuery();
				
				if(!rs.isBeforeFirst())
				{
					request.getSession(true).setAttribute("loginFail", "Email Password mismatchi!");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					while(rs.next())
					{
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						
						idn = rs.getInt("managerDetails.id");
						
						
						Timestamp expiring = rs.getTimestamp("contractorPass.expire_date");
						
						if (currentTime.after(expiring))
						{
				    		response.sendRedirect(dest);
						}
						else 
						{
							request.getSession(true).setAttribute("loginSuccess", "Login Successful");
				            
							response.sendRedirect(home);
						}
					}
					
				}
			}
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error Establishing database connection");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		catch (Exception e)
		{
			request.getSession(true).setAttribute("passError", "Pass Hashing Error");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		
		return idn;
    }
    
    public int field_login(String home, String dest, String useremail, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	int idn = 0;
    	
    	try
		{
    		Methods methods = new Methods();
    		
			String hashpassword = methods.tohash(password);
			
			try
			{
				con = Methods.getConnection();
				
				String selects = "select * from fieldEngineerDetails inner join fieldEngineerPass on fieldEngineerDetails.id = fieldEngineerPass.fieldEngineerDetails_id where fieldEngineerDetails.email=? and fieldEngineerPass.pass=?";
				
				ps = con.prepareStatement(selects);
				ps.setString(1, useremail);
				ps.setString(2, hashpassword);
				
				ResultSet rs = ps.executeQuery();
				
				if(!rs.isBeforeFirst())
				{
					request.getSession(true).setAttribute("loginFail", "Email Password mismatchi!");
		            
		    		response.sendRedirect(request.getHeader("Referer"));
				}
				else
				{
					while(rs.next())
					{
						Timestamp currentTime = new Timestamp(System.currentTimeMillis());
						
						idn = rs.getInt("fieldEngineerDetails.id");
						
						
						Timestamp expiring = rs.getTimestamp("fieldEngineerPass.expire_date");
						
						if (currentTime.after(expiring))
						{
				    		response.sendRedirect(dest);
						}
						else 
						{
							request.getSession(true).setAttribute("loginSuccess", "Login Successful");
				            
							response.sendRedirect(home);
						}
					}
					
				}
			}
			catch (Exception e)
			{
				request.getSession(true).setAttribute("databaseConnectionError", "Error Establishing database connection");
	            
	    		response.sendRedirect(request.getHeader("Referer"));
			}
		}
		catch (Exception e)
		{
			request.getSession(true).setAttribute("passError", "Pass Hashing Error");
            
    		response.sendRedirect(request.getHeader("Referer"));
		}
		
		return idn;
    }
}
