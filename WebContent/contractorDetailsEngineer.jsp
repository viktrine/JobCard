<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Profile View</title>
	  		
	  		<jsp:include page="cdn.jsp" />
	  		
	  		<script src="times.js"></script>
	  		
	  		
	</head>
	
	<body>
	<div class="container">
	
		<%
			if(request.getSession(false).getAttribute("engineerLog") == null)
			{
				request.getSession(true).setAttribute("loginFirst", "Please login to proceed");
				response.sendRedirect("/jobcard/engineerlogin.jsp");
				return;
			}
		%>
		
	<jsp:include page="menuengineer.jsp" />
			
			<div class="row">
        		<div class="col-xs-12 col-sm-6 col-md-6">
		            <div class="well well-sm">
		                <div class="row">
		                    <div class="col-sm-6 col-md-4">
		                        <img  src="profile.jpeg" alt="" class="img-rounded img-responsive" />
		                    </div>
		                    <div class="col-sm-6 col-md-8">
		                    <table>
		                    <tr>
		                       <td nowrap style="text-align: right">Contractor Name:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pcname}"/></td>
		                    </tr>
		                    <tr>
		                       <td nowrap style="text-align: right">Manager Name:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pname}"/></td>
		                    </tr>
		                    <tr> 
		                       <td nowrap style="text-align: right">Email:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pemail}"/></td>
		                    </tr>
		                    <tr> 
		                       <td nowrap style="text-align: right">Phone Number:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pcontact_phone}"/></td>
		                    </tr>
		                    <tr>
		                       <td nowrap style="text-align: right">Staff ID:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pstaff_id}"/></td>
		                    </tr>
		                    <tr>
		                       <td nowrap style="text-align: right">Registration Date:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pregistration_date}"/></td>
		                    </tr> 
		                    <tr>
		                       <td nowrap style="text-align: right">KRA PIN:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pkra_pin}"/></td>
		                    </tr> 
		                    <tr>
		                       <td nowrap style="text-align: right">VAT Number:</td><td>&nbsp;&nbsp;&nbsp;<c:out value="${sessionScope.pvat_no}"/></td>
		                    </tr>  
		                       </table>
                    		</div>
                		</div>
            		</div>
        		</div>
    		</div>
			
		</div>
	</body>
</html>