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
	
});

var msgFields = new Object();
msgFields['resourceOid'] 	= 'resourceOid';
msgFields['roleOid'] 			= 'roleOid';
msgFields['taskName'] 		= 'taskName';

var formGroups = new Object();
formGroups['resourceOid']	= 'form-group1';
formGroups['roleOid']			= 'form-group2';
formGroups['taskName']		= 'form-group3';

function saveSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	clearSave();
}

function clearSave() {
	clearWarningMessageField(formGroups, msgFields);
	$("#resourceOid").val( _qifu_please_select_id );
	$("#roleOid").val( _qifu_please_select_id );
	$("#taskName").val( '' );
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG003D0005A_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG003D0005A');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnSave();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0005A');"
	programName="${programName}"
	programId="${programId}"
	description="Create settings BPM process role.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="resourceMap" name="resourceOid" id="resourceOid" value="" requiredFlag="Y" label="Resource"></q:select>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="roleMap" name="roleOid" id="roleOid" value="" requiredFlag="Y" label="Role"></q:select>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="taskName" id="taskName" value="" maxlength="255" requiredFlag="Y" label="Task name"></q:textbox>
		</div>
	</div>	
</div>

<p style="margin-bottom: 10px"></p>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnSave" label="<i class=\"icon fa fa-floppy-o\"></i>&nbsp;Save"
			xhrUrl="./core.sysBpmResourceRoleSaveJson.do"
			xhrParameter="
			{
				'resourceOid'		:	$('#resourceOid').val(),
				'roleOid'			:	$('#roleOid').val(),
				'taskName'			:	$('#taskName').val()
			}
			"
			onclick="btnSave();"
			loadFunction="saveSuccess(data);"
			errorFunction="clearSave();">
		</q:button>
		<q:button id="btnClear" label="<i class=\"icon fa fa-hand-paper-o\"></i>&nbsp;Clear" onclick="clearSave();"></q:button>
	</div>
</div>

<br>
<br>

</body>
</html>