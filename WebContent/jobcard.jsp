<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="java.io.IOException,java.io.PrintWriter,java.sql.*,java.sql.Connection,java.io.IOException,java.sql.PreparedStatement,
java.sql.ResultSet,javax.servlet.ServletException,javax.servlet.annotation.WebServlet,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.util.Date"%>

		
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Job Card</title>
	  		
	  		<jsp:include page="cdn.jsp" />
	  		
	  		<script src="times.js"></script>
	</head>
	
	<body>
	<div class="container">
	
	<%
		if (request.getSession(false).getAttribute("fieldengineerLog") == null)
		{
			request.getSession(true).setAttribute("loginFirst", "Please login");
			response.sendRedirect("/jobcard/fieldEngineerLogin.jsp");
			return;
		}
	%>
		<jsp:include page="menufield.jsp" />
			
		<c:if test="${not empty itemnullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.itemnullError}"/></font></p>
    		<c:remove var="itemnullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty loginFirst}">
    		<p><font color="FF0000"><c:out value="${sessionScope.loginFirst}"/></font></p>
    		<c:remove var="loginFirst" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty notimecard}">
    		<p><font color="FF0000"><c:out value="${sessionScope.notimecard}"/></font></p>
    		<c:remove var="notimecard" scope="session" />
		</c:if>
		
		<c:if test="${not empty timeconversionerror}">
    		<p><font color="FF0000"><c:out value="${sessionScope.timeconversionerror}"/></font></p>
    		<c:remove var="timeconversionerror" scope="session" />
		</c:if>
		
		<c:if test="${not empty billable}">
    		<p><font color="FF0000"><c:out value="${sessionScope.billable}"/></font></p>
    		<c:remove var="billable" scope="session" />
		</c:if>
		
		<c:if test="${not empty materialPosted}">
    		<p><font color="00EE76"><c:out value="${sessionScope.materialPosted}"/></font></p>
    		<c:remove var="materialPosted" scope="session" />
		</c:if>
		
		
		<%
		 // Register JDBC driver
	  	  Class.forName("com.mysql.jdbc.Driver");
	  	  
	  	  // Open a connection
	  	  Connection con = DriverManager.getConnection("jdbc:mysql://node9334-jobcard.p4d.click/Fixed","root","XDEbyt28426");
	  	  
  	  	  // Execute SQL query
  	  
  	  	PreparedStatement ps = con.prepareStatement("select * from materials");
  	 	PreparedStatement psss = con.prepareStatement("select * from engineerDetails");
  	  
  	  	//java.sql.Statement stmt = con.createStatement();
  	  
  	  	ResultSet rs1 = (ResultSet) ps.executeQuery();
  		ResultSet rs3 = (ResultSet) psss.executeQuery();
		%>
		
		<form action='Jobcard' method="POST" id="reg">
			
 		<div id="legend">
      		<legend class=""><font color="#FF6A6A">JOB CARD</font></legend>
    	</div>
    			
    	<table>
    	
			<tr>
    			<td style="text-align: right"><label>Date </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><%=new java.util.Date() %></td>
    		</tr>
    		<tr>
    			<td style="text-align: right"><label>Engineer </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.fieldfname}"/> <c:out value="${sessionScope.fieldoname}"/></td>
    		</tr>
    		
    		<tr>
    			<td style="text-align: right"><label>Contractor </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.regcontractorname}"/></td>
    		</tr>
    		
    		<tr>
    			<td style="text-align: right"><label>Assign To</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td>
    			<td><div id="mySelect">
		     			<select style="width: 380px;" name="asiigned_email">
		      				<option selected disabled>Choose Engineer</option>	
			      				<% while(rs3.next()){ %>
			      					<option><%= rs3.getString("email")%></option>	
			      				<%}%>
						</select> 
						</div>
				</td>
    		</tr>
    		
    		<tr>
    			<td style="text-align: right"><label>Customer</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="company" name="company" placeholder="customer" class="input-xlarge" required size="40"></td>
    		</tr>
    		
    		<tr>
    			<td style="text-align: right"><label>Location</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="location" name="location" placeholder="location" class="input-xlarge" required size="40"></td>
    		</tr>
    		
    		<tr>
    			<td style="text-align: right"><label>Trouble Ticket </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="ticket" name="ticket" placeholder="Ticket" class="input-xlarge" required size="40"></td>
    		</tr>
    		<tr>
    			<td style="text-align: right"><label>Service Required </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="service" name="service" placeholder="Service" class="input-xlarge" required size="40"></td>
    		</tr>
    		
    	</table>
		    			<hr>
    			
    			<div class="control-group">
      			
	      			<!-- ENGINEER'S REPORT -->
	      			<label for="report"><font color="#FF6A6A">Engineer's Report, Work Completed and Recommendations</font></label>
	      			<div class="controls">
	        			<textarea name="report" class="form-control" rows="3"  column="40" required></textarea>
	      			</div>
    		
    			</div>
    			
    			<hr>
    			
    			<label for="report"><font color="#FF6A6A">Please provide the billable items used in the field</font></label>
	      			
    			<div class="input-group control-group after-add-more">
      			
      			<table>
	      				<thead>
		      				<tr>
		      					<th>Item Type</th>
		      					<th>Description</th>
		      					<th>Serial Number</th>
		      					<th>Units Used</th>
		      				</tr>
	      				</thead>
	      				<tbody>
	      					<tr>
	      						<td>
		      					<div id="mySelect">
		      						<select style="width: 200px;" name="item">
		      						
			      					<option selected disabled>Choose Material</option>	
			      					<% while(rs1.next()){ %>
			      					
			      						<option><%= rs1.getString(2)%></option>	
			      					
			      					<%}%>
									</select> 
								</div>
		      					</td>
		      					<td><input type="text" name="desc"  placeholder="Small Description" size="25" class="input-xlarge"></td>
		      					<td><input type="text" name="Serial" placeholder="Serial Number" size="25" class="input-xlarge"></td>
		      					<td><input type="text" name="units" placeholder="Number of items used"  size="25" class="input-xlarge"></td>
		      					
	      					</tr>
							  
	      				</tbody>
	      			</table>
				   </div>
				   
				   <div class="copy-fields hide">
            		<div class="control-group input-group" style="margin-top:10px">
				   	<table>
	      				<% ResultSet rs2 = (ResultSet) ps.executeQuery(); %>
	      				<tr>
	      						<td>
		      					<div id="mySelect">
		      						<select style="width: 200px;" name="item">
		      						<option selected disabled>Choose Material</option>
			      					<% while(rs2.next()){ %>
			      					<option><%= rs2.getString(2)%></option>		
			      					<%}%>
									</select> 
								</div>
		      					</td>
		      					<td><input type="text" name="desc"  placeholder="Small Description" size="25" class="input-xlarge"></td>
		      					<td><input type="text" name="Serial" placeholder="Serial Number" size="25" class="input-xlarge"></td>
		      					<td><input type="text" name="units" placeholder="Number of items used"  size="25" class="input-xlarge"></td>
	      				</tr>
	      			</table>
	      			<div class="input-group-btn"> 
              			<button class="btn btn-danger remove" type="button">Remove</button>
            		</div>
          			</div>
        		</div>
        		
				   <div class="input-group-btn"> 
				   		<button class="btn btn-success add-more" type="button"><i class="glyphicon glyphicon-plus"></i> Add More Materials</button>
					</div>
		
				   <hr>
 					
    		<label for="timme"><font color="#FF6A6A">Labour Service will be charged to the customer based on the <br/>
	      			following time card and at the current rates.</font></label>
    			
    			<br>
    			<div class="control-group">
    			
      			<table>
	      			<!--Time Card-->
	      			
	      			<thead>
	      				<th><center>Description</center></th>
	      				<th><center>Time</center></th>
	      			</thead>
	      			<tbody>
	      			<tr>
	        			<td style="text-align: left"><label>Time Left Office &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="time" name="leftOffice" placeholder="Time Left Office"></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time arrived at client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="atPremise" name="atPremise" placeholder="Time arrived premise"></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time left client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="offPremise" name="offPremise" placeholder="Time Left premise"></td>
	      			</tr>
	      			<tr>
	        			<td style="text-align: left"><label>Time Arrived back at office &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="backOffice" name="backOffice" placeholder="Time arrived office"></td>
	      			</tr>
	      			</tbody>
	      		</table>
	      		</div>
    			
    			<hr>
    			
    			<table>
    			<thead>
    				<th></th>
    				<th>Charges (including V.A.T)</th>
    			</thead>
    			<tbody>
    				<tr>
	        			<td><label>Billable &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="text" id="billable" name="billable" placeholder="" class="input-xlarge"></td>
	      			</tr>
	      			
	      			<tr>
	        			<td><label>Reschedule &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="text" id="reschedule" name="reschedule" placeholder="" class="input-xlarge"></td>
	      			</tr>
	      		</tbody>
    			
    			
   				<div class="control-group">
	      			<!-- Button -->
	      			<div class="controls">
		      			<tr>
		      				<td><button class="btn btn-success">Submit Job Card</button></td>
		      			</tr>
	        		</div>
    			</div>
    			</table>
			</form>
			<script type="text/javascript">
 
    $(document).ready(function() {
 
	//here first get the contents of the div with name class copy-fields and add it to after "after-add-more" div class.
      $(".add-more").click(function(){ 
          var html = $(".copy-fields").html();
          $(".after-add-more").after(html);
      });
//here it will remove the current value of the remove button which has been pressed
      $("body").on("click",".remove",function(){ 
          $(this).parents(".control-group").remove();
      });
 
    });
 
</script>
		</div>
	</body>
</html>