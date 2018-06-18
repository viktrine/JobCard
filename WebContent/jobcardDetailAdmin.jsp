<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Job Card View - Admin</title>
	  		
	  		<jsp:include page="cdn.jsp" />
	  		
	  		<script src="times.js"></script>
	      
	</head>
	
	<body>
	<div class="container">
	
	<%
		if (request.getSession(false).getAttribute("adminLog") == null)
		{
			request.getSession(true).setAttribute("loginFirst", "Please login to proceed");
			response.sendRedirect("/jobcard/adminlogin.jsp");
			return;
		}
	%>
		<jsp:include page="menuadmin.jsp" />
		
		<c:if test="${not empty ApprovalSuccessful}">
    		<p><font color="00EE76"><c:out value="${sessionScope.ApprovalSuccessful}"/></font></p>
    		<c:remove var="ApprovalSuccessful" scope="session" />
		</c:if>
		
		<c:if test="${not empty ApprovalError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.ApprovalError}"/></font></p>
    		<c:remove var="ApprovalError" scope="session" />
		</c:if>
		
		
			<form action='Jobcard' method="POST" id="reg">
			
 				
    			<div id="legend">
      				<legend class=""><font color="#FF6A6A">JOB CARD No: <c:out value="${sessionScope.job_card_noo}"/></font> <a style="float:right" href="Approve_Admin" title='Approve Jobcard' data-toggle='tooltip'>APPROVE<span class='glyphicon glyphicon-ok'></span></a><a style="float:right" href="Reject_Admin" title='Reject Jobcard' data-toggle='tooltip'><font color="FF0000">REJECT<span class='glyphicon glyphicon-remove'></span></font></a></legend>
      				</div>
    			
    			<div>
    				
    			</div>
    			<table>
    			
    			<tr>
    				<td style="text-align: right"><label>Date Raised</label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.date_raisedd}"/></td>
    			</tr>
    			<tr>
    				<td style="text-align: right"><label>Engineer </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.fieldname}"/></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Contractor </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.regcontractorname}"/></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Customer Served</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.companyy}"/></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Location</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.locationn}"/></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Trouble Ticket </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.tickett}"/></td>
    			</tr>
    			<tr>
    				<td style="text-align: right"><label>Service Offered </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><c:out value="${sessionScope.servicee}"/></td>
    			</tr>
    			
    			</table>
		
    			<hr>
    			
    			<div class="control-group">
      			
	      			<!-- ENGINEER'S REPORT -->
	      			<label for="report"><font color="#FF6A6A">Engineer's Report, Work Completed and Recommendations</font></label>
	      			<div class="controls">
	        			<c:out value="${sessionScope.reportt}"/>
	      			</div>
    		
    			</div>
    			
    			<hr>
    			
    			<label for="report"><font color="#FF6A6A">Items Used.</font></label>
	      			
    			<div class="input-group control-group after-add-more">
      			
      			<table border="1">
	      				<thead>
		      				<tr>
		      					<th>Item(s) Used</th>
		      					<th>Total Amount</th>
		      				</tr>
	      				</thead>
	      				<tbody>
	      					<tr>
	      						<td>
		      					<c:out value="${sessionScope.typee}"/> (<c:out value="${sessionScope.unitt}"/>)&nbsp; &nbsp; &nbsp;
		      					</td>
		      					<td style="text-align: right"><c:out value="${sessionScope.payy}"/></td>
		      					
	      					</tr>
							  
	      				</tbody>
	      			</table>
				   </div>
				   
		
				   <hr>
 					
    		<label for="timme"><font color="#FF6A6A">Time Cards</font></label>
    			
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
	        			<td><c:out value="${sessionScope.left_officee}"/></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time arrived at client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><c:out value="${sessionScope.arrive_clientt}"/></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time left client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><c:out value="${sessionScope.left_clientt}"/></td>
	      			</tr>
	      			<tr>
	        			<td style="text-align: left"><label>Time Arrived back at office &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><c:out value="${sessionScope.arrive_officee}"/></td>
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
	        			<td><c:out value="${sessionScope.billablee}"/></td>
	      			</tr>
	      			
	      			<tr>
	        			<td><label>Reschedule &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><c:out value="${sessionScope.reschedulee}"/></td>
	      			</tr>
	      			
	      		</tbody>
    			</table>
    			<hr>
    			<table>
    				<tr>
	    				<td><label>Total Payable to Contractor &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
		        		<td><label><font color="#FF6A6A"><c:out value="${sessionScope.summ}"/></font></label></td>
    				</tr>
    			</table>
			</form>
			
		</div>
	</body>
</html>