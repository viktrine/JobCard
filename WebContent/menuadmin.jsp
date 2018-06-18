<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<div class="container">

	<div class="row">

  	<ul class="nav nav-tabs">
    	<li class="nav-item">
      		<a class="nav-link active" href="homeadmin.jsp">HOME</a>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> REGISTER USERS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="adminRegister.jsp">Register Admin</a>
		        <a class="dropdown-item" href="engineerRegister.jsp">Register Engineer</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> MANAGE USERS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="View_Admins">View Admins</a>
		        <a class="dropdown-item" href="View_EngineersAdmin">View Engineers</a>
		        <a class="dropdown-item" href="View_ContractorsAdmin">View Contractors</a>
		        <a class="dropdown-item" href="View_FieldEngineersAdmin">View Field Engineers</a>
		        <a class="dropdown-item" href="engineeringresetpass.jsp">Reset Engineer Password</a>
		        <a class="dropdown-item" href="adminresetpass.jsp">Reset Admin Password</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> MATERIALS</a>
      		
      		<div class="dropdown-menu">
      			<a class="dropdown-item" href="materials.jsp">New Material</a>
		        <a class="dropdown-item" href="View_Materials">View Materials</a>
		  	</div>
    	</li>
    	
    
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"> VIEW JOB CARDS</a>
      		
      		<div class="dropdown-menu">
			    <a class="dropdown-item" href="View_AdminJobcard" >All</a>
			    <a class="dropdown-item" href="View_AdminJobcardNew">New</a>
			    <a class="dropdown-item" href="View_AdminJobcardIApprove">I Approved</a>
			    <a class="dropdown-item" href="View_AdminJobcardIReject">I Rejected</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">REPORTS</a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="proforma.jsp">Print Proforma</a>
		  	</div>
    	</li>
    	
    	<li class="nav-item dropdown">
      		<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#"><span class='glyphicon glyphicon-user'></span><c:out value="${sessionScope.adminfname}"/> <c:out value="${sessionScope.adminoname}"/></a>
      		
      		<div class="dropdown-menu">
		        <a class="dropdown-item" href="adminlogin.jsp">Login</a>
			    <a class="dropdown-item" href="ProfileAdmin">View Profile</a>
			    <a class="dropdown-item" href="adminEdit.jsp">Edit Profile</a>
			    <a class="dropdown-item" href="DestroySessionsAdmin">Log Out</a>
		  	</div>
    	</li>
    	
    	
  	</ul>
  		</div>
    </div>	
    

<div class="row">
	&nbsp;
	&nbsp;
	&nbsp;
	<br>
</div>
