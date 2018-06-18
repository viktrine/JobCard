<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div class="container">
  	<ul class="nav nav-tabs">
    	<li class="nav-item">
      		<a class="nav-link active" href="homeengineer.jsp">HOME</a>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> MANAGE USERS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="contractorRegister.jsp">Register Contractor</a>
		        <a class="dropdown-item" href="View_ContractorsEngineer">View Contractors</a>
		        <a class="dropdown-item" href="contractorresetpass.jsp">Reset Contractors Password</a>
		        <a class="dropdown-item" href="View_FieldEngineersEngineer">View Field Engineers</a>
		  	</div>
    	</li>
    	
    
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> VIEW JOB CARDS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="View_engineersJobcards">All</a>
			    <a class="dropdown-item" href="View_engineersJobcardsNew">New</a>
			    <a class="dropdown-item" href="View_engineersJobcardsIApproved">I Approved</a>
			    <a class="dropdown-item" href="View_engineersJobcardsIRejected">I Rejected</a>
			    <a class="dropdown-item" href="View_engineersJobcardsAdminApproved">Admin Approved</a>
			    <a class="dropdown-item" href="View_engineersJobcardsAdminRejected">Admin Rejected</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"><span class='glyphicon glyphicon-user'></span><c:out value="${sessionScope.engineerfname}"/> <c:out value="${sessionScope.engineeroname}"/></a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="engineerlogin.jsp">Login</a>
			    <a class="dropdown-item" href="ProfileEngineer">View Profile</a>
			    <a class="dropdown-item" href="engineerEdit.jsp">Edit Profile</a>
			    <a class="dropdown-item" href="DestroySessionsEngineer">Log Out</a>
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
