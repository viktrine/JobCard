<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Contractor Profile Edit</title>
	  
			<jsp:include page="cdn.jsp" />
	      
	</head>
	
	<body>
	<div class="container">
	
		<%
			if(request.getSession(false).getAttribute("contractorLog") == null)
			{
				request.getSession(true).setAttribute("loginFirst", "Please login to proceed");
				response.sendRedirect("/jobcard/contractorlogin.jsp");
				return;
			}
		%>
		<jsp:include page="menucontractor.jsp" />
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000">All fields are required</font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000">Error Establishing database connection</font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty Error}">
    		<p><font color="FF0000"><c:out value="${sessionScope.Error}"/></font></p>
    		<c:remove var="Error" scope="session" />
		</c:if>
		
			
			<form class="form-horizontall" action='ContractorEdit' method="POST" id="reg">
 				
    			<div id="legend">
      				<legend class=""><h3><font color="#FF6A6A">Contractor Profile Edit</font></h3></legend>
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- Contractor Name -->
	      			<label class="control-label" for="Desc"><h4><font color="#FF6A6A">Contractor Details</font></h4></label>
	      			<br>
	      			<label class="control-label"  for="namee">Contractor Name</label>
	      			<div class="controls">
	        			<input type="text" id="namee" name="namee" placeholder="Contractor Name" class="input-xlarge" value='<c:out value="${contractorcname}" />' required>
	        			
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- Phone Number -->
	      			<label class="control-label"  for="namee">Phone Number</label>
	      			<div class="controls">
	        			<input type="tel" id="pno" name="pno" placeholder="Phone Number" class="input-xlarge" value='<c:out value="${contractorcontact_phone}" />' required>
	        			
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- PIN No. -->
	      			<label class="control-label"  for="namee">KRA PIN No.</label>
	      			<div class="controls">
	        			<input type="text" id="pin" name="pin" placeholder="PIN No." class="input-xlarge" value='<c:out value="${kra_pin}" />' required>
	        			
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- VAT No. -->
	      			<label class="control-label"  for="namee">VAT No.</label>
	      			<div class="controls">
	        			<input type="text" id="vat_no" name="vat_no" placeholder="VAT No." class="input-xlarge" value='<c:out value="${vat_no}" />' required>
	        			
	      			</div>
    		
    			</div>
    			
    			<hr>
    			
    			<div class="control-group">
      				<label class="control-label" for="Desc"><h4><font color="#FF6A6A">Manager Details</font></h4></label>
	      			<!-- First Name -->
	      			<br>
	      			<label class="control-label"  for="fname">First Name</label>
	      			<div class="controls">
	        			<input type="text" id="fname" name="fname" placeholder="First Name" class="input-xlarge" value='<c:out value="${contractorfname}" />' required>
	        			
	      			</div>
    		
				
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- Other Names -->
	      			<label class="control-label"  for="oname">Other Names</label>
	      			<div class="controls">
	        			<input type="text" id="oname" name="oname" placeholder="Other Names" class="input-xlarge" value='<c:out value="${contractoroname}" />' required>
	        			
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      				<!-- E-mail -->
      				<label class="control-label" for="email">E-mail</label>
      				<div class="controls">
				        <input type="email" id="email" name="email" placeholder="example@domain.com" class="input-xlarge" value='<c:out value="${contractoremail}" />' readonly required>
				        
    				</div>
    			</div>
    			
    				
    				
    			<div class="control-group">
      				<!-- Staff No -->
			        <label class="control-label"  for="staffid">Staff ID</label>
			        <div class="controls">
				        <input type="text" id="staffid" name="staffid" placeholder="Staff ID" class="input-xlarge" value='<c:out value="${contractorstaffid}" />' readonly required>
				        
     				</div>
    			</div>
    			<hr>
    			
   				<div class="control-group">
	      			<!-- Button -->
	      			<div class="controls">
	        			<button class="btn btn-success">Update Profile</button>
	      			</div>
    			</div>
			</form>
			
		</div>
	</div>
	</body>
	
</html>