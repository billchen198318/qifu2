<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>

<style type="text/css">


</style>


<script type="text/javascript">

$( document ).ready(function() {
	
});

function previewReport() {
	var paramData = [];
	<q:if test=" null != paramList && paramList.size > 0 ">
	<c:forEach items="${paramList}" var="item" varStatus="myIndex">
	paramData.${item.urlParam} = $("#CORE_PROG001D0005S02Q_field\\:${item.urlParam}" ).val();
	</c:forEach>
	</q:if>
	
	commonOpenJasperReport('${sysJreport.reportId}', paramData);
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0005S02Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG001D0005S02Q', '${sysJreport.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.hideModal('CORE_PROG001D0005S02Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<q:if test=" null != paramList && paramList.size > 0 ">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:button id="preview" label="Preview" onclick="previewReport();"></q:button>
		</div>
	</div>
	
	<br>
	
	
		<table class="table">
		<thead class="thead-inverse">
		<tr>
			<th>URL / Report parameter</th>
			<th>Variable value</th>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${paramList}" var="item" varStatus="myIndex">
			<tr>
				<td>${item.urlParam} / ${item.rptParam}</td>
				<td>
					<q:textbox name="CORE_PROG001D0005S02Q_field:${item.urlParam}" id="CORE_PROG001D0005S02Q_field:${item.urlParam}" value="" maxlength="50" placeholder="Enter variable value"></q:textbox>
				</td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
		
		
</q:if>
<q:else>
	<h4><span class="badge badge-warning">No settings report parameter</span></h4>
</q:else>

</body>
</html>