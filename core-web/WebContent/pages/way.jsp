<%@page import="org.qifu.base.Constants"%>
<%@page import="org.qifu.util.ApplicationSiteUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String mainSysBasePath = ApplicationSiteUtils.getBasePath(Constants.getMainSystem(), request);

%>
<!doctype html>
<html itemscope="itemscope" itemtype="http://schema.org/WebPage">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <base href="<%=basePath%>">
    
    <title>qifu2</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="qifu2">
	<meta http-equiv="description" content="qifu2">
	
<style type="text/css">

</style>

<script type="text/javascript">

</script>

</head>

<body>
<a href="<%=mainSysBasePath%>/index.do">qifu2</a>
<script type="text/javascript">
window.location = "<%=mainSysBasePath%>/index.do";
</script>
</body>
</html>

