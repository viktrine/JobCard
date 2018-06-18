<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Job Card Edit</title>
	  		
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
		
		
			<form action='JobcardEdit' method="POST" id="reg">
			
 				
    			<div id="legend">
      				<legend class=""><font color="#FF6A6A">JOB CARD No: <c:out value="${sessionScope.job_card_noo}"/></font></legend>
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
    				<td style="text-align: right"><label>Customer Served</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="company" name="company" placeholder="company" class="input-xlarge" size="40" value='<c:out value="${sessionScope.companyy}"/>'></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Location</label> &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="location" name="location" placeholder="location" class="input-xlarge" required size="40" value='<c:out value="${sessionScope.locationn}"/>'></td>
    			</tr>
    			
    			<tr>
    				<td style="text-align: right"><label>Trouble Ticket </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="ticket" name="ticket" placeholder="Ticket" class="input-xlarge" required size="40" value='<c:out value="${sessionScope.tickett}"/>'></td>
    			</tr>
    			<tr>
    				<td style="text-align: right"><label>Service Offered </label>&nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</td><td><input type="text" id="service" name="service" placeholder="Service" class="input-xlarge" required size="40" value='<c:out value="${sessionScope.servicee}"/>'></td>
    			</tr>
    			
    			</table>
		
    			<hr>
    			
    			<div class="control-group">
      			
	      			<!-- ENGINEER'S REPORT -->
	      			<label for="report"><font color="#FF6A6A">Engineer's Report, Work Completed and Recommendations</font></label>
	      			<div class="controls">
	        			<textarea name="report" class="form-control" rows="3"  column="40" value='<c:out value="${sessionScope.reportt}"/>' required></textarea>
	      			</div>
    		
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
	        			<td><input type="datetime-local" id="time" name="leftOffice" placeholder="Time Left Office" value='<c:out value="${sessionScope.left_officee}"/>'></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time arrived at client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="atPremise" name="atPremise" placeholder="Time arrived premise" value='<c:out value="${sessionScope.arrive_clientt}"/>'></td>
	      			</tr>
	      			
	      			<tr>
	        			<td style="text-align: left"><label>Time left client premise &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="offPremise" name="offPremise" placeholder="Time Left premise" value='<c:out value="${sessionScope.left_clientt}"/>'></td>
	      			</tr>
	      			<tr>
	        			<td style="text-align: left"><label>Time Arrived back at office &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="datetime-local" id="backOffice" name="backOffice" placeholder="Time arrived office" value='<c:out value="${sessionScope.arrive_officee}"/>'></td>
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
	        			<td><input type="text" id="billable" name="billable" placeholder="" class="input-xlarge" value='<c:out value="${sessionScope.billablee}"/>'></td>
	      			</tr>
	      			
	      			<tr>
	        			<td><label>Reschedule &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp;</label></td>
	        			<td><input type="text" id="reschedule" name="reschedule" placeholder="" class="input-xlarge" value='<c:out value="${sessionScope.reschedulee}"/>'></td>
	      			</tr>
	      			
	      		</tbody>
    			</table>
    			<hr>
    			<div class="control-group">
	      			<!-- Button -->
	      			<div class="controls">
		      			<tr>
		      				<td><button class="btn btn-success">Confirm Edit</button></td>
		      			</tr>
	        		</div>
    			</div>
			</form>
			
		</div>
	</body>
</html>