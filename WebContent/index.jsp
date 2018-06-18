<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>User Type</title>	  
		  
		<jsp:include page="cdn.jsp" />
	      
	</head>
	
	<body>
	
	<div class="container">
	
		<%
			if(request.getSession(false).getAttribute("adminLog") != null)
			{
				request.getSession(true).setAttribute("alreadyLogged", "You are already logged in");
				response.sendRedirect("/jobcard/homeadmin.jsp");
				return;
			}
		%>
		
		<%
			if(request.getSession(false).getAttribute("adminLog") != null)
			{
				request.getSession(true).setAttribute("alreadyLogged", "You are already logged in");
				response.sendRedirect("/jobcard/homeadmin.jsp");
				return;
			}
		%>
		
		<%
			if(request.getSession(false).getAttribute("adminLog") != null)
			{
				request.getSession(true).setAttribute("alreadyLogged", "You are already logged in");
				response.sendRedirect("/jobcard/homeadmin.jsp");
				return;
			}
		%>
		
		<%
			if(request.getSession(false).getAttribute("adminLog") != null)
			{
				request.getSession(true).setAttribute("alreadyLogged", "You are already logged in");
				response.sendRedirect("/jobcard/homeadmin.jsp");
				return;
			}
		%>
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
	
		
		<div class="container">
    	<div class="row">
			<div class="col-md-6 col-md-offset-1">
				<div class="panel panel-login">
					<div class="panel-heading">
						<i>Proceed As:</i>
						<hr>
					</div>
					<div class="panel-body">
						<div class="col-md-6">
								<form action="UserType" method="post" style="display: block;">
									
									<div class="form-group">
										<a href="adminlogin.jsp"><h4>ADMIN</h3></a>
									</div>
									
									<div class="form-group">
										<a href="engineerlogin.jsp"><h4>ENGINEER</h3></a>
									</div>
									
									<div class="form-group">
										<a href = "contractorlogin.jsp"><h4>CONTRACTOR</h3></a>
									</div>
									
									<div class="form-group">
										<a href="fieldEngineerLogin.jsp"><h4>FIELD ENGINEER</h3></a>
									</div>
									
								</form>
							</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		</div>
	</body>
	
</html>