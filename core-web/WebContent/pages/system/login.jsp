<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>qifu2</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="<%=basePath%>/tether/tether.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script type="text/javascript" src="<%=basePath%>/tether/tether.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="<%=basePath%>/popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="<%=basePath%>/jquery/jquery-3.2.1.min.js?ver=${jsVerBuild}"></script>
<link rel="stylesheet" href="<%=basePath%>/bootstrap-vali/css/main.css?ver=${jsVerBuild}" crossorigin="anonymous">
<link href="<%=basePath%>/font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>/bootstrap-vali/js/bootstrap.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<!-- The javascript plugin to display page loading on top-->
<script src="<%=basePath%>/js/plugins/pace.min.js"></script>

<style type="text/css">

body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #ffffff;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
.form-signin .form-control {
  position: relative;
  height: auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
  padding: 10px;
  font-size: 16px;
}
.form-signin .form-control:focus {
  z-index: 2;
}

</style>

<script type="text/javascript">

function submitLoginForm() {	
	document.loginForm.submit();
	$('#myPleaseWait').modal('show');	
}

function changeKaptcha() {
	$("#kaptchaImg").attr('src', "./kaptcha.jpg?n=" + guid());
}

/* 
 * http://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript 
 */
function guid() {
	
	  function s4() {
	    return Math.floor((1 + Math.random()) * 0x10000)
	      .toString(16)
	      .substring(1);
	  }
	  
	  return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
	    s4() + '-' + s4() + s4() + s4();
	  
}

</script>

</head>

<body>

<!-- Modal Start here-->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="myPleaseWait" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallModalLabel">Please wait!</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./images/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here -->

<section class="material-half-bg">
	<div class="cover"></div>
</section>
<section class="login-content">
	<div class="logo">
		<table border="0">
			<tr>
				<td align="right"><img src="./images/logo2.png" width="48" height="48" border="0"/></td>
				<td align="left">&nbsp;&nbsp;<h1>welcome qifu2</h1></td>
			</tr>
		</table>
	</div>
	<div class="login-box">

	<form class="form-signin" name="loginForm" id="loginForm" action="./login.do" method="post">
	   
	    <c:if test="${ \"Y\" == loginCaptchaCodeEnable }">    
	    <div class="form-group">
	          <label for="captcha">Captcha code <img src="./kaptcha.jpg?n=<%=System.currentTimeMillis()%>" id="kaptchaImg" onclick="changeKaptcha();"/></label>
	          <input class="form-control" type="text" id="captcha" name="captcha">
	    </div>       
	    </c:if>
	    
	    <div class="form-group">
	          <label for="username">Username</label>
	          <input class="form-control" type="text" id="username" name="username" maxlength="12">
	    </div>   
	    <div class="form-group">
	          <label for="password">Password</label>
	          <input class="form-control" type="password" id="password" name="password" maxlength="25">
	    </div>   
	   	
	   	<c:if test="${\"\" != pageMessage && null != pageMessage}">
		<div class="row"><p class="bg-warning"><c:out value="${pageMessage}" ></c:out></p></div>
	   	</c:if>
	   	
	   	<div class="form-group">
	    	<button type="button" class="btn btn-lg btn-primary btn-block" name="btnSubmit" onclick="submitLoginForm()">Login</button>
	   	</div>
	   	
	   	<div class="form-group"><p></p></div>
	   	
	    <div class="form-group">&nbsp;&nbsp;&nbsp;<label>qifu2 0.2 version</label></div>
	    
	    
	</form>
	
	</div>
</section>

</body>
</html>
