<%@page import="org.qifu.base.model.YesNo"%>
<%@page import="org.qifu.po.TbSysProg"%>
<%@page import="org.qifu.util.MenuSupportUtils"%>
<%@page import="org.qifu.util.ApplicationSiteUtils"%>
<%@page import="org.qifu.base.Constants"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String progId = request.getParameter("progId");
String progUrl = "";
String sysId = "";
String progName = "";
if (StringUtils.isBlank(progId)) {
	progId = "CORE_PROG999D9999Q"; // about
}

try {
	TbSysProg sysProg = MenuSupportUtils.loadSysProg(progId);
	sysId = Constants.getSystem();
	progName = sysProg.getName();
	if (!StringUtils.isBlank(sysProg.getUrl()) && !YesNo.YES.equals(sysProg.getEditMode())) {
		progUrl = MenuSupportUtils.getUrl(basePath, ApplicationSiteUtils.getSys(sysId), sysProg);
	}	
} catch (Exception e) {
	e.printStackTrace();
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>qifu2</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="qifu2">
	<meta http-equiv="description" content="qifu2">

  </head>
  
  <link rel="stylesheet" href="<%=basePath%>/bootstrap-vali/css/main.css">
  
<style>
body {
  width: 94%;
  margin:0 auto;
}
</style>   
  
  <body leftmargin="0" topmargin="0" >
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	
    
    <tr>
      <td colspan="2" bgcolor="#FFFFFF" height="90%">
      
      <br/>

<center>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10" rowspan="3" bgcolor="#01600A" style="border-top-left-radius: 5px; border-bottom-left-radius: 5px;">&nbsp;</td>
    <td width="48" bgcolor="#000000">&nbsp;</td>
    <td bgcolor="#000000" style="border-top-right-radius: 5px;"><font color="#FFFFFF" size="5"><B>Refresh page:</B></font></td>
  </tr>
  <tr>
    <td width="48" bgcolor="#F4F4F4"><img src="./images/information.png" width="48" height="48" border="0"/></td>
    <td valign="top" bgcolor="#F4F4F4">
	<font color="#000000" size="4">Please wait redirect <%=progName%>(<%=progId%>).</font>
	<br>  
<%
if (!StringUtils.isBlank(progUrl)) {
%>	
	<a href="<%=progUrl%>">Click direct redirect</a>
<%
}
%>	
    </td>
  </tr>
  <tr>
    <td width="48" bgcolor="#F4F4F4">&nbsp;</td>
    <td bgcolor="#F4F4F4" style="border-bottom-right-radius: 5px;">&nbsp;</td>
  </tr>
</table>

</center>

      <br/>
            
      </td>
      
    </tr>
    
	
  </table>

<script>
<%
if (!StringUtils.isBlank(progUrl)) {
%>
function redirectProgPage() {	
	window.location = "<%=progUrl%>";
}
setTimeout(redirectProgPage, 2000);
<%
} else {
%>
alert('ERROR, the program cannot redirect.');
<%
}
%>
</script>


  </body>
</html>
