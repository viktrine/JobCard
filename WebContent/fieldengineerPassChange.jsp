<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Field Engineer Pass Change</title>	  
		  
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
		
		<c:if test="${not empty pinError}">
    		<p><font color="FF0000">Give an integer PIN</font></p>
    		<c:remove var="pinError" scope="session" />
		</c:if>
		
		<c:if test="${not empty updateError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.updateError}"/></font></p>
    		<c:remove var="updateError" scope="session" />
		</c:if>
		
		<c:if test="${not empty randPass}">
    		<p><font color="FF0000"><c:out value="${sessionScope.randPass}"/></font></p>
    		<c:remove var="randPass" scope="session" />
		</c:if>
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty EmailError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.EmailError}"/></font></p>
    		<c:remove var="EmailError" scope="session" />
		</c:if>
		
		<c:if test="${not empty passDiffError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.passDiffError}"/></font></p>
    		<c:remove var="passDiffError" scope="session" />
		</c:if>
		
		
		<c:if test="${not empty ResetError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.ResetError}"/></font></p>
    		<c:remove var="ResetError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty passDiffError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.passDiffError}"/></font></p>
    		<c:remove var="passDiffError" scope="session" />
		</c:if>
		
    	<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12">
							<center><label>Change Password</label></center>
								
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="FieldEngineerPassChange" method="post" style="display: block;">
									<div class="form-group">
										Old Password:<input type="password" name="oldpass" tabindex="1" class="form-control" placeholder="Old Password" required>
									</div>
									<div class="form-group">
										New Password<input type="password" name="newpass" tabindex="1" class="form-control" placeholder="new password" required>
									</div>
									<div class="form-group">
										Repeat Password:<input type="password" name="repeatnewpass" tabindex="1" class="form-control" placeholder="new password" required>
									</div>
									
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Change">
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