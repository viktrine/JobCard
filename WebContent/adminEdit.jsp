<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Admin Edit</title>	  
		
		<jsp:include page="cdn.jsp" />
	      
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

			<div class="panel-body">
				<div class="row">
					<div class="col-lg-12 col-lg-offset-1">
						<form class="form-horizontall" action='AdminEdit' method="POST">
     			<div id="legend">
      				<legend class=""><font color="#FF6A6A">Admin Profile Edit</font></legend>
    			</div>
				
    			<div class="control-group">
      			
	      			<!-- First Name -->
	      			<label class="control-label"  for="fname">First Name</label>
	      			<div class="controls">
	        			<input type="text" id="fname" name="fname" placeholder="First Name" class="input-xlarge" value='<c:out value="${adminfname}" />' required>
	        			<c:remove var="fname" scope="session" />
	      			</div>
    		
    			</div>
    
    			<div class="control-group">
      			
	      			<!-- Other Names -->
	      			<label class="control-label"  for="oname">Other Names</label>
	      			<div class="controls">
	        			<input type="text" id="oname" name="oname" placeholder="Other Names" required class="input-xlarge" value='<c:out value="${adminoname}" />'>
	        			<c:remove var="oname" scope="session" />
	      			</div>
    		
    			</div>
 
  				<div class="control-group">
      				<!-- E-mail -->
      				<label class="control-label" for="email">E-mail</label>
      				<div class="controls">
				        <input type="email" id="email" name="email" placeholder="example@domain.com" class="input-xlarge" value='<c:out value="${adminemail}" />' required>
				        <c:remove var="email" scope="session" />
    				</div>
    			</div>
				
    			<div class="control-group">
      				<!-- Tel -->
      				<label class="control-label" for="email">Phone Number</label>
      				<div class="controls">
				        <input type="tel" id="email" name="tel" placeholder="07xxxxxxxx" class="input-xlarge" value='<c:out value="${adminphone}" />' required>
				        <c:remove var="tel" scope="session" />
    				</div>
    			</div>
 
 			  	
    			
    			<div class="control-group">
      				<!-- IN/PP No -->
			        <label class="control-label"  for="idpp">ID/PP No</label>
			        <div class="controls">
				        <input type="text" id="idpp" name="idpp" placeholder="ID/PP NO" class="input-xlarge" required value='<c:out value="${adminidpp_no}" />'>
				        <c:remove var="idpp" scope="session" />
     				</div>
    			</div>
    			<div class="control-group">
      				<!-- Staff No -->
			        <label class="control-label"  for="staffid">Staff No</label>
			        <div class="controls">
				        <input type="text" id="staffid" name="staffid" placeholder="Staff ID" class="input-xlarge" required value='<c:out value="${adminstaff_no}" />'>
				        <c:remove var="staffid" scope="session" />
     				</div>
    			</div>
    			
    			<div class="control-group">
      				<!-- Company Name-->
			        <label class="control-label"  for="company">Company Name</label>
			        <div class="controls">
				        <input type="text" id="company" name="company" placeholder="Company" class="input-xlarge" required value='<c:out value="${admincompany_name}" />'>
				        <c:remove var="company" scope="session" />
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
			</div>
			
			
	</div>
	</body>
	
</html>