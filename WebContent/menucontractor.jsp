<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div class="container">
  	<ul class="nav nav-tabs">
    	<li class="nav-item">
      		<a class="nav-link active" href="homecontractor.jsp">HOME</a>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> MANAGE FIELD ENGINEERS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="fieldengineer.jsp">Register Field Engineer</a>
		        <a class="dropdown-item" href="View_FieldEngineersContractor">View Field Engineers</a>
		        <a class="dropdown-item" href="fieldEngineerPassReset.jsp">Reset Field Engineer Password</a>
		  	</div>
    	</li>
    	
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> VIEW JOB CARDS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="View_contractorsJobcards">All</a>
			    <a class="dropdown-item" href="View_contractorsJobcardsNew">Newly Created</a>
			    <a class="dropdown-item" href="View_contractorsJobcardsIApproved">I Approved</a>
			    <a class="dropdown-item" href="View_contractorsJobcardsIRejected">I Rejected</a>
			    <a class="dropdown-item" href="View_contractorsJobcardsEngineerApproved">Engineer Approved</a>
			    <a class="dropdown-item" href="View_contractorsJobcardsEngineerRejected">Engineer Rejected</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> VAT DECLARATION</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="vat.jsp">Provide monthly VAT</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">PROFORMA</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="proformaContractor.jsp">Print Proforma</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"><span class='glyphicon glyphicon-user'></span><c:out value="${sessionScope.contractorcname}"/></a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="contractorlogin.jsp">Login</a>
			    <a class="dropdown-item" href="ProfileContractor">View Profile</a>
			    <a class="dropdown-item" href=contractorEdit.jsp>Edit Profile</a>
			    <a class="dropdown-item" href="DestroySessionsContractor">Log Out</a>
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
