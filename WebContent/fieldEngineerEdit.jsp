<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Field Engineer Edit</title>
			<jsp:include page="cdn.jsp" />
	      
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
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty Error}">
    		<p><font color="FF0000"><c:out value="${sessionScope.Error}"/></font></p>
    		<c:remove var="Error" scope="session" />
		</c:if>
		
			<form class="form-horizontall" action='FieldEdit' method="post">
 				
    			<div id="legend">
      				<legend class=""><h3><font color="#FF6A6A">Field Engineer Profile Edit</font></h3></legend>
    			</div>
    			
    			
    			<div class="control-group">
      				<!-- First Name -->
	      			<br>
	      			<label class="control-label"  for="fname">First Name</label>
	      			<div class="controls">
	        			<input type="text" id="fname" name="fname" placeholder="First Name" class="input-xlarge" value='<c:out value="${fieldfname}" />' required>
	        			<c:remove var="fname" scope="session" />
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- Other Names -->
	      			<label class="control-label"  for="oname">Other Names</label>
	      			<div class="controls">
	        			<input type="text" id="oname" name="oname" placeholder="Other Names" class="input-xlarge" value='<c:out value="${fieldoname}" />' required>
	        			<c:remove var="oname" scope="session" />
	      			</div>
    		
    			</div>
    			
    			<div class="control-group">
      			
	      			<!-- Phone Number -->
	      			<label class="control-label"  for="pno">Phone Number</label>
	      			<div class="controls">
	        			<input type="tel" id="pno" name="pno" placeholder="Phone Number" class="input-xlarge" value='<c:out value="${fieldphone_no}" />' required>
	        			<c:remove var="pno" scope="session" />
	      			</div>
    		
    			</div>
				
    			
    			<div class="control-group">
      				<!-- E-mail -->
      				<label class="control-label" for="email">E-mail</label>
      				<div class="controls">
				        <input type="email" id="email" name="email" placeholder="example@domain.com" class="input-xlarge" value='<c:out value="${fieldemail}" />' readonly required>
				        <c:remove var="email" scope="session" />
    				</div>
    			</div>
    			
    			<div class="control-group">
      				<!-- Staff No -->
			        <label class="control-label"  for="staffid">Staff ID</label>
			        <div class="controls">
				        <input type="text" id="staffid" name="staffid" placeholder="Staff ID" class="input-xlarge" value='<c:out value="${fieldstaff_id}" />' readonly required>
				        <c:remove var="staffid" scope="session" />
     				</div>
    			</div>
    			
    			<hr>
    			
   				<div class="control-group">
	      			<!-- Button -->
	      			<div class="controls">
	        			<button class="btn btn-success">Update Details</button>
	      			</div>
    			</div>
			</form>
			
		</div>
	</body>
	
</html>