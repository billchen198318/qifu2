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
<title>qifu2</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>

<style type="text/css">


</style>


<script type="text/javascript">

$( document ).ready(function() {
	
});

function loadDiagram(type, objId) {
	xhrSendParameter(
			'./core.sysBpmResourceExportDiagramJson.do', 
			{ 
				'type' 			: type,
				'objectId'		: objId,
				'resourceId'	: '${sysBpmnResource.id}'
			}, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					parent.toastrWarning( data.message );
					return;
				}
				parent.toastrInfo( data.message );
				commonViewUploadFile( data.value );
			}, 
			function() {
				
			},
			_qifu_defaultSelfPleaseWaitShow
	);
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG003D0004S01Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0004S01Q', '${sysBpmnResource.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0004S01Q');"
	programName="${programName}"
	programId="${programId}"
	description="View BPM ( Activiti ) process list diagram.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

	<table class="table">
		<legend><h4><span class="badge badge-secondary">Process definition</span></h4></legend>
		<thead class="thead-light">
		<tr>
			<th><strong>Key</strong></th>
			<th><strong>Name</strong></th>
			<th><strong>#</strong></th>
		</tr>
		</thead>
		<tbody>
		<q:if test=" null != processDefinitions ">
		<c:forEach items="${processDefinitions}" var="item" varStatus="myIndex">
		
		<tr>
			<td align="left" width="40%" >${item.key}</td>
			<td align="left" width="40%" >${item.name}</td>
			<td align="center" width="20%" >
				<q:button id="btnProcessDefinitionDiagram_${myIndex.index}" label="Diagram" onclick="loadDiagram('processDefinition', '${item.id}');"></q:button>
			</td>
		</tr>			
		
		</c:forEach>	
		</q:if>
		</tbody>
	</table>
	
	<p style="margin-bottom: 10px"></p>
	
	<table class="table">
		<legend><h4><span class="badge badge-secondary">Process instance</span></h4></legend>
		<thead class="thead-light">
		<tr>
			<th><strong>Id</strong></th>
			<th><strong>Process definition</strong></th>
			<th><strong>#</strong></th>
		</tr>
		</thead>
		<tbody>
		<q:if test=" null != processInstances ">
		<c:forEach items="${processInstances}" var="item" varStatus="myIndex">
		
		<tr>
			<td align="left" width="40%" >${item.id}</td>
			<td align="left" width="40%" >${item.processDefinitionId}</td>
			<td align="center" width="20%" >
				<q:button id="btnProcessInstanceDiagram_${myIndex.index}" label="Diagram" onclick="loadDiagram('processInstance', '${item.id}');"></q:button>	
			</td>
		</tr>			
		
		</c:forEach>
		</q:if>
		</tbody>
	</table>	
	
	<p style="margin-bottom: 10px"></p>
	
	<table class="table">
		<legend><h4><span class="badge badge-secondary">Task</span></h4></legend>
		<thead class="thead-light">
		<tr>
			<th><strong>Id</strong></th>
			<th><strong>Name</strong></th>
			<th><strong>Assignee</strong></th>			
			<th><strong>#</strong></th>
		</tr>
		</thead>
		<tbody>
		<q:if test=" null != tasks ">
		<c:forEach items="${tasks}" var="item" varStatus="myIndex">
		
		<tr>
			<td align="left" width="30%" >${item.id}</td>
			<td align="left" width="30%" >${item.name}</td>
			<td align="left" width="30%" >${item.assignee}</td>
			<td align="center" width="30%" >
				<q:button id="btnTaskDiagram_${myIndex.index}" label="Diagram" onclick="loadDiagram('task', '${item.id}');"></q:button>
			</td>
		</tr>			
		
		</c:forEach>
		</q:if>
		</tbody>
	</table>	

</body>
</html>