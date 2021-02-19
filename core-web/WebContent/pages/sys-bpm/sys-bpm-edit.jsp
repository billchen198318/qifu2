<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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
	
	$("#uploadLabel").html('<span class="badge badge-success">BPM Resource: ${sysBpmnResource.id}</span>');
	
});

var msgFields = new Object();
msgFields['id'] 			= 'id';
msgFields['name'] 			= 'name';

var formGroups = new Object();
formGroups['id'] 			= 'form-group1';
formGroups['name'] 			= 'form-group1';

function updateSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	clearUpdate();
}

function clearUpdate() {
	window.location=parent.getProgUrlForOid('CORE_PROG003D0004E', '${sysBpmnResource.oid}');
}

function uploadModal() {
	showCommonUploadModal(
			'uploadOid', 
			'tmp', 
			'Y',
			function() {
				$("#uploadLabel").html('<span class="badge badge-success">Upload success</span>');
			},
			function() {
				$("#uploadLabel").html('<span class="badge badge-danger">Upload fail</span>');
			}
	);
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG003D0004E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0004E', '${sysBpmnResource.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0004E');"
	programName="${programName}"
	programId="${programId}"
	description="Modify or upload BPM ( Activiti ) resource.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp">
	<jsp:param value="Y" name="commonUploadEnable"/>
</jsp:include>

<input type="hidden" name="uploadOid" id="uploadOid" value="">
<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			Activiti BPM file(zip)&nbsp;<font color='RED'>*</font>
			<p style="margin-bottom: 10px"></p>
			<q:button id="uploadBtn" label="<i class=\"icon fa fa-upload\"></i>&nbsp;Upload" onclick="uploadModal();"></q:button><div id="uploadLabel"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="id" id="id" value="sysBpmnResource.id" maxlength="100" requiredFlag="Y" label="Id" placeholder="Enter Id" readonly="Y"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="name" id="name" value="sysBpmnResource.name" maxlength="255" requiredFlag="Y" label="Name" placeholder="Enter name"></q:textbox>
		</div>
	</div>		
</div>
<div class="form-group" id="form-group2">
	<div class="row">	
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" value="sysBpmnResource.description" id="description" label="Description" rows="3" placeholder="Enter description"></q:textarea>
		</div>
	</div>	
</div>

<p style="margin-bottom: 10px"></p>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="<i class=\"icon fa fa-floppy-o\"></i>&nbsp;Save"
			xhrUrl="./core.sysBpmResourceUpdateJson.do"
			xhrParameter="	
			{
				'oid'				:	'${sysBpmnResource.oid}',			
				'uploadOid'			:	$('#uploadOid').val(),
				'id'				:	$('#id').val(),
				'name'				:	$('#name').val(),
				'description'		:	$('#description').val()
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="clearUpdate();">
		</q:button>
		<q:button id="btnClear" label="<i class=\"icon fa fa-hand-paper-o\"></i>&nbsp;Clear" onclick="clearUpdate();"></q:button>
	</div>
</div>

<br>
<br>
<br>

</body>
</html>