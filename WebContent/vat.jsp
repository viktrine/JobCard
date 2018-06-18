<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page import="java.io.IOException,java.io.PrintWriter,java.sql.*,java.sql.Connection,java.io.IOException,java.sql.PreparedStatement,
java.sql.ResultSet,javax.servlet.ServletException,javax.servlet.annotation.WebServlet,javax.servlet.http.HttpServlet,
javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>VAT</title>
 		
 		<jsp:include page="cdn.jsp" />
	  	<script src="times.js"></script>
	      
	</head>
	
	<body>
	<div class="container">
	
	<%
		if (request.getSession(false).getAttribute("contractorLog") == null)
		{
			request.getSession(true).setAttribute("loginFirst", "Please login to proceed");
			response.sendRedirect("/jobcard/contractorlogin.jsp");
			return;
		}
	%>
	
		<jsp:include page="menucontractor.jsp" />
			
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty vat_success}">
    		<p><font color="00EE76"><c:out value="${sessionScope.vat_success}"/></font></p>
    		<c:remove var="vat_success" scope="session" />
		</c:if>
		
		<c:if test="${not empty vat_error}">
    		<p><font color="FF0000"><c:out value="${sessionScope.vat_error}"/></font></p>
    		<c:remove var="vat_error" scope="session" />
		</c:if>
		<%
		 // Register JDBC driver
  	  Class.forName("com.mysql.jdbc.Driver");
  	  
  	  // Open a connection
  	  Connection con = DriverManager.getConnection("jdbc:mysql://node9334-jobcard.p4d.click/Fixed","root","XDEbyt28426");
  	  
  	  // Execute SQL query
  	  
  	  PreparedStatement ps = con.prepareStatement("select * from contractorDetails");
  	  
  	  //java.sql.Statement stmt = con.createStatement();
  	  
  	  ResultSet rs = (ResultSet) ps.executeQuery();
		%>
			<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-12">
								<center><label>VAT Criteria</label></center>
							</div>
						</div>
						<hr>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="login-form" action="Vat" method="post" style="display: block;">
								<label>VAT Value declaration for the period </label>
									
									<div class="form-group">
									<label>Between</label>
										<input type="date" name="datefrom"  class="form-control" placeholder="start date">
									</div>
									
									<div class="form-group">
									<label>And</label>
										<input type="date" name="dateto" class="form-control" placeholder="last date">
									</div>
									
									<div class="form-group">
									<label>Amount</label>
										<input type="number" name="amount" class="form-control" placeholder="VAT Value" step="0.1">
									</div>
									
									
									<div class="form-group">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Post VAT">
											</div>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-lg-12">
												
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