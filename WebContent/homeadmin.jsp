<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
<html lang="en">
	<head>
  		<title>Admin Home</title>
	  	
	  	<jsp:include page="cdn.jsp" />
	  	
	</head>
	
	<body >
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
		
  		<c:if test="${not empty loginSuccess}">
    		<p><font color="00EE76">Login Successful</font></p>
    		<c:remove var="loginSuccess" scope="session" />
		</c:if>
			
		
		<c:if test="${not empty updateSucess}">
    		<p><font color="00EE76"><c:out value="${sessionScope.updateSucess}"/></font></p>
    		<c:remove var="updateSucess" scope="session" />
		</c:if>
		
		<c:if test="${not empty Success}">
    		<p><font color="00EE76"><c:out value="${sessionScope.Success}"/></font></p>
    		<c:remove var="Success" scope="session" />
		</c:if>
		
		<c:if test="${not empty alreadyLogged}">
    		<p><font color="FF0000"><c:out value="${sessionScope.alreadyLogged}"/></font></p>
    		<c:remove var="alreadyLogged" scope="session" />
		</c:if>
		
  		<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<p>
						  			The Admin has a role of registering a contractor. This is to give a better control of who to register 
						  			compared to where a contractor registers personally.  
						  		</p>
							</div>
						</div>
						
						<div class="row">
							<div class="col-lg-12">
								<p>
						  			Password resetting for contractors is done by the admin as well as resetting admin password.
						  			This is restricted to only the admin of the system.
						  		</p>
							</div>
						</div>
						
						<div class="row">
							<div class="col-lg-12">
								<p>
						  			The admin can access various raised job cards. If the unit input is correct the admin can approve. 
						  			If a job card is wrong, the admin is preserved the ability to roll it back. 
 						  		</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
  		
  		</div>
	</body>
	
</html>