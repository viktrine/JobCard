<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Engineer Login</title>	  
		  
		  	<jsp:include page="cdn.jsp" />
	      
	</head>
	
	<body>
	<div class="container">
		
		<%
			if(request.getSession().getAttribute("engineerLog") != null && request.getSession().getAttribute("engineerLog").equals(true))
			{
				request.getSession(true).setAttribute("alreadyLogged", "Error");
				response.sendRedirect("/jobcard/homeengineer.jsp");
				return;
			}
		%>
	
		<jsp:include page="menuengineer.jsp" />
	
		<c:if test="${not empty loginFirst}">
    		<p><font color="FF0000"><c:out value="${sessionScope.loginFirst}"/></font></p>
    		<c:remove var="loginFirst" scope="session" />
		</c:if>
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000">Please enter your email and password</font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000">Error Establishing database connection</font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty passError}">
    		<p><font color="FF0000">Pass Hashing Error</font></p>
    		<c:remove var="passError" scope="session" />
		</c:if>
		
		<c:if test="${not empty loginFail}">
    		<p><font color="FF0000">Email Password mismatch</font></p>
    		<c:remove var="loginFail" scope="session" />
		</c:if>
		
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12">
								<center><i>Login</i></center>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="EngineerLogin" method="post" style="display: block;">
									<div class="form-group">
										<input type="email" name="useremail" id="username" tabindex="1" class="form-control" placeholder="email" value="">
									</div>
									<div class="form-group">
										<input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
									</div>
									<div class="form-group text-center">
										<input type="checkbox" tabindex="3" class="" name="remember" id="remember">
										<label for="remember"> Remember Me</label>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Log In">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												<div class="text-center">
													<a href="#" class="forgot-password" onclick="return alert('Contact your contractor to reset your password.');">Forgot Password?</a>
												</div>
											</div>
										</div>
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