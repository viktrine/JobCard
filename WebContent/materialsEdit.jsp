<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Materials Edit</title>	  
		  
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
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty sqlError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.sqlError}"/></font></p>
    		<c:remove var="sqlError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty doubleError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.doubleError}"/></font></p>
    		<c:remove var="doubleError" scope="session" />
		</c:if>
		<div class="bodyy">
		
		<div id="legend">
      		<legend class=""><font color="#FF6A6A"><h2>Materials Description</h2></font></legend>
    	</div>
    			<p><h4>Your are required to provide a vivid description of materials and all the services
    			that are billable by the contractor. Provide the  unit price alongside the material 
    			description as well</h4></p>
    			
    			<br>	
    			
			<div class="panel panel-default">
    
		    	<div class="panel-heading"><h4>Edit Material</h4></div>
		    	<div class="panel-body">
        			<form action="MaterialsEdit" method="post" >
 
      					<div class="input-group control-group after-add-more">
						   	Material Description: <input type="text" name="material"  placeholder="Material Description" size="50" class="input-xlarge" value="<c:out value="${sessionScope.materialss}"/>">
						   	Unit Price: <input type="number" name="unit_price"  step="0.01" min="0" placeholder="Unit Price" class="input-xlarge" value="<c:out value="${sessionScope.unit_price}"/>">
			 			</div>
			  			
			  			<hr>
 						
 						<center><input type="reset" value="Reset"><input type="submit" value="Edit Material"></center>
        			</form>
        		</div>
  			</div>
		</div>
	</div>
	</body>
	
</html>