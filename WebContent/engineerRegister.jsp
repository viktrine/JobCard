<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Engineer Register</title>	  
		  
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
		
		<c:if test="${not empty UserExist}">
    		<p><font color="FF0000">Email User Already Exist</font></p>
    		<c:remove var="UserExist" scope="session" />
		</c:if>
		
		<c:if test="${not empty registerSuccess}">
    		<p><h3><font color="00EE76">Registration Successful</h3></font></p>
    		<c:remove var="registerSuccess" scope="session" />
		</c:if>
		
		<c:if test="${not empty registerError}">
    		<p><font color="FF0000">Error During Registration</font></p>
    		<c:remove var="registerError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000">Error Establishing database connection</font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		<c:if test="${not empty passwordrand}">
    		<p><font color="FF0000">Password:  <c:out value="${sessionScope.passwordrand}"/> </font></p>
    		<c:remove var="passwordrand" scope="session" />
		</c:if>
			<form class="form-horizontall" action='EngineerRegister' method="POST">

    			<div id="legend">
      				<legend class=""><font color="#FF6A6A">Engineer Registration</font></legend>
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- First Name -->
	      			<label class="control-label"  for="fname">First Name</label>
	      			<div class="controls">
	        			<input type="text" id="fname" name="fname" placeholder="First Name" class="input-xlarge" value='<c:out value="${fname}" />' required>
	      			</div>
    		
    			</div>
    
    			<div class="control-group">
      			
	      			<!-- Other Names -->
	      			<label class="control-label"  for="oname">Other Names</label>
	      			<div class="controls">
	        			<input type="text" id="oname" name="oname" placeholder="Other Names" class="input-xlarge" value='<c:out value="${oname}" />' required>
	      			</div>

    			</div>
    			
    			<div class="control-group">
      				<!-- Phone Number -->
      				<label class="control-label" for="phone">Phone Number</label>
      				<div class="controls">
				        <input type="tel" id="pno" name="pno" placeholder="07********" class="input-xlarge" value='<c:out value="${pno}" />' required>
    				</div>
    			</div>
    			
 
  				<div class="control-group">
      				<!-- E-mail -->
      				<label class="control-label" for="email">E-mail</label>
      				<div class="controls">
				        <input type="email" id="email" name="email" placeholder="example@domain.com" class="input-xlarge" value='<c:out value="${email}" />' required>
    				</div>
    			</div>
 
 			  	
    			<div class="control-group">
      				<!-- IN/PP No -->
			        <label class="control-label"  for="idpp">ID/PP No</label>
			        <div class="controls">
				        <input type="text" id="idpp" name="idpp" placeholder="ID/PP NO" class="input-xlarge" value='<c:out value="${idpp}" />' required>
     				</div>
    			</div>
    			
    			<div class="control-group">
      				<!-- Staff No -->
			        <label class="control-label"  for="staffid">Staff ID</label>
			        <div class="controls">
				        <input type="text" id="staffid" name="staffid" placeholder="Staff ID" class="input-xlarge" value='<c:out value="${staffid}" />' required>
     				</div>
    			</div>
    			
    			
    			<div class="control-group">
      				<!-- Company-->
			        <label class="control-label"  for="company">Company</label>
			        <div class="controls">
				        <input type="text" id="company" name="company" placeholder="Company" class="input-xlarge" value='<c:out value="${company}" />' required>
     				</div>
    			</div>
    			
   				<div class="control-group">
	      			<!-- Button -->
	      			<div class="controls">
	        			<button class="btn btn-success">Register</button>
	      			</div>
    			</div>
			</form>
			</div>
	
	</body>
	
</html>