<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html lang="en">
	<head>
	  <title>Materials Entry</title>	  
		  <jsp:include page="cdn.jsp" />
	      

	</head>
	<body>
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
		
		<c:if test="${not empty nullError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.nullError}"/></font></p>
    		<c:remove var="nullError" scope="session" />
		</c:if>
		
		<c:if test="${not empty sqlError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.sqlError}"/></font></p>
    		<c:remove var="sqlError" scope="session" />
		</c:if>
		
		<c:if test="${not empty databaseConnectionError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.databaseConnectionError}"/></font></p>
    		<c:remove var="databaseConnectionError" scope="session" />
		</c:if>
		
		<c:if test="${not empty materialError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.materialError}"/></font></p>
    		<c:remove var="materialError" scope="session" />
		</c:if>
		
		<c:if test="${not empty materialPosted}">
    		<p><font color="00EE76"><c:out value="${sessionScope.materialPosted}"/></font></p>
    		<c:remove var="materialPosted" scope="session" />
		</c:if>
		
		<c:if test="${not empty doubleError}">
    		<p><font color="FF0000"><c:out value="${sessionScope.doubleError}"/></font></p>
    		<c:remove var="doubleError" scope="session" />
		</c:if>
		
		<div class="bodyy">
		
		<div id="legend">
      		<legend class=""><font color="#FF6A6A"><h2>Materials Description</h2></font></legend>
    	</div>
    			<p><h4>Your are required to provide a vivid description of materials and all the services
    			that are billable by the contractor. Provide the  unit price alongside the material 
    			description as well</h4></p>
    			
    			<br>	
    			
		<div class="panel panel-default">
    
    	<div class="panel-heading"><h4>Materials Entry</h4></div>
    	<div class="panel-body">
        	<form action="MaterialsProcess" method="post" >
 
      			<div class="input-group control-group after-add-more">
				   	Material Description: <input type="text" name="material"  placeholder="Material Description" size="50" class="input-xlarge"  required>
				   	Unit Price: <input type="number" name="unit_price"  step="0.01" min="0" placeholder="Unit Price" class="input-xlarge"  required>
			  </div>
			  
			  <hr>
 					<center>
 						<input type="reset" value="Reset">
 						<input type="submit" value="Submit Materials">
 					</center>
        	</form>
        	
        	<!-- Copy Fields-These are the fields which we get through jquery and then add after the above input,-->
		        <div class="copy-fields hide">
		            <div class="control-group input-group" style="margin-top:10px">
						   	Material Description: <input type="text" name="material"  placeholder="Material Description" size="50" class="input-xlarge" required>
						   	Unit Price: <input type="number" name="unit_price"  step="0.01" min="0" placeholder="Unit Price" class="input-xlarge" required>
		            	<div class="input-group-btn"> 
		              		<button class="btn btn-danger remove" type="button">Remove</button>
		            	</div>
		          	</div>
		        </div>
		
	    </div>
  	</div>
  	
  	<div class="input-group-btn"> 
		<button class="btn btn-success add-more" type="button"><i class="glyphicon glyphicon-plus"></i> Add More</button>
	</div>
			
		</div>
<script type="text/javascript">
 
    $(document).ready(function() {
 
	//here first get the contents of the div with name class copy-fields and add it to after "after-add-more" div class.
      $(".add-more").click(function(){ 
          var html = $(".copy-fields").html();
          $(".after-add-more").after(html);
      });
//here it will remove the current value of the remove button which has been pressed
      $("body").on("click",".remove",function(){ 
          $(this).parents(".control-group").remove();
      });
 
    });
 
</script>
</div>
	</body>
	
</html>