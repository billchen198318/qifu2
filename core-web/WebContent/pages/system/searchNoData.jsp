<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

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
    <td bgcolor="#000000" style="border-top-right-radius: 5px;"><font color="#FFFFFF" size="5"><strong>No data:</strong></font></td>
  </tr>
  <tr>
    <td width="48" bgcolor="#F4F4F4"><img src="./images/information.png" width="48" height="48" border="0"/></td>
    <td valign="top" bgcolor="#F4F4F4">
	<font color="#000000" size="4">Data may have been deleted, <BR/>please close the page.</font>
	<br>  
	<br>Page message:<br>
	<font color="#666666" size="4">${pageMessage}</font>   
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

  
  </body>
</html>
