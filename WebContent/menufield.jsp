<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<div class="container">

  	<ul class="nav nav-tabs">
    	<li class="nav-item">
      		<a class="nav-link active" href="homefieldengineer.jsp">HOME</a>
    	</li>
    
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">JOB CARD ACTIONS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="View_FieldEngineerJobcard">View My Job Cards</a>
		   		<a class="dropdown-item" href="jobcard.jsp">Create New</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> VIEW JOB CARDS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="View_FieldEngineerJobcard">All</a>
			    <a class="dropdown-item" href="View_FieldEngineerJobcardNew">New</a>
			    <a class="dropdown-item" href="View_FieldEngineerJobcardApproved">Approved</a>
			    <a class="dropdown-item" href="View_FieldEngineerJobcardRejected">Rejected</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"><span class='glyphicon glyphicon-user'></span><c:out value="${sessionScope.fieldfname}"/> <c:out value="${sessionScope.fieldoname}"/></a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="fieldEngineerLogin.jsp">Login</a>
			    <a class="dropdown-item" href="ProfileField">View Profile</a>
			    <a class="dropdown-item" href="fieldEngineerEdit.jsp">Edit Profile</a>
			    <a class="dropdown-item" href="DestroySessionsField">Log Out</a>
		  	</div>
    	</li>
  	</ul>
</div>


<div class="row">
	&nbsp;
	&nbsp;
	&nbsp;
	<br>
</div>