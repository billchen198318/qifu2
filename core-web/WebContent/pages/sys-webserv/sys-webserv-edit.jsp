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

var msgFields = new Object();
msgFields['systemOid'] 		= 'systemOid';
msgFields['wsId'] 			= 'wsId';
msgFields['beanId'] 		= 'beanId';
msgFields['type'] 			= 'type';
msgFields['publishAddress']	= 'publishAddress';

var formGroups = new Object();
formGroups['systemOid'] 		= 'form-group1';
formGroups['wsId'] 				= 'form-group1';
formGroups['beanId'] 			= 'form-group1';
formGroups['type'] 				= 'form-group2';
formGroups['publishAddress']	= 'form-group2';

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
	window.location=parent.getProgUrlForOid('CORE_PROG003D0001E', '${sysWsConfig.oid}');
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG003D0001E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0001E', '${sysWsConfig.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0001E');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="sysMap" name="systemOid" id="systemOid" value="systemOid" requiredFlag="Y" label="System"></q:select>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="wsId" id="wsId" value="sysWsConfig.wsId" maxlength="10" requiredFlag="Y" label="Id" placeholder="Enter Id" readonly="Y"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="beanId" id="beanId" value="sysWsConfig.beanId" maxlength="255" requiredFlag="Y" label="Service bean Id" placeholder="Enter service bean Id"></q:textbox>
		</div>
	</div>		
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="typeMap" name="type" id="type" value="sysWsConfig.type" label="Type"></q:select>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="publishAddress" id="publishAddress" value="sysWsConfig.publishAddress" maxlength="255" label="Address" placeholder="Enter address"></q:textbox>
		</div>
	</div>			
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" value="sysWsConfig.description" id="description" label="Description" rows="3" placeholder="Enter descripnion"></q:textarea>
		</div>
	</div>
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="Save"
			xhrUrl="./core.sysWebServiceUpdateJson.do"
			xhrParameter="	
			{
				'oid'				:	'${sysWsConfig.oid}',
				'systemOid'			:	$('#systemOid').val(),
				'wsId'				:	$('#wsId').val(),
				'beanId'			:	$('#beanId').val(),
				'type'				:	$('#type').val(),
				'publishAddress'	:	$('#publishAddress').val(),
				'description'		:	$('#description').val()
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="clearUpdate();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="clearUpdate();"></q:button>
	</div>
</div>

</body>
</html>