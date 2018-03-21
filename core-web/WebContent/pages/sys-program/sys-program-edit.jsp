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
	
	$("#icon").trigger("change");
	
});

var msgFields = new Object();
msgFields['progSystemOid'] 	= 'progSystem';
msgFields['progId'] 		= 'progId';
msgFields['name'] 			= 'name';
msgFields['url']			= 'url';
msgFields['itemType']		= 'itemType';
msgFields['iconOid']		= 'icon';
msgFields['editMode']		= 'editMode';
msgFields['isDialog']		= 'isDialog';
msgFields['dialogWidth']	= 'dialogWidth';
msgFields['dialogHeight']	= 'dialogHeight';

var formGroups = new Object();
formGroups['progSystem'] 	= 'form-group1';
formGroups['progId'] 		= 'form-group1';
formGroups['name'] 			= 'form-group1';
formGroups['url'] 			= 'form-group2';
formGroups['itemType'] 		= 'form-group2';
formGroups['icon'] 			= 'form-group2';
formGroups['editMode'] 		= 'form-group2';
formGroups['isDialog'] 		= 'form-group3';
formGroups['dialogWidth'] 	= 'form-group3';
formGroups['dialogHeight'] 	= 'form-group3';

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
	clearWarningMessageField(formGroups, msgFields);
	window.location=parent.getProgUrlForOid('CORE_PROG001D0002E', '${sysProg.oid}');
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0002E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG001D0002E', '${sysProg.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0002E');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<form action="." name="CORE_PROG001D0002E_form" id="CORE_PROG001D0002E_form">
<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="sysMap" name="progSystem" id="progSystem" value="sysSelectOid" label="System" requiredFlag="Y"></q:select>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="progId" value="sysProg.progId" id="progId" label="Id" requiredFlag="Y" maxlength="50" placeholder="Enter Program Id" readonly="Y"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="name" value="sysProg.name" id="name" label="Name" requiredFlag="Y" maxlength="100" placeholder="Enter Program Name"></q:textbox>
		</div>
	</div>
</div>
<div class="form-group" id="form-group2">	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="url" value="sysProg.url" id="url" label="Url" maxlength="255" placeholder="Enter Program URL"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="{ \"FOLDER\":\"FOLDER\", \"ITEM\":\"ITEM\" }" name="itemType" id="itemType" value="sysProg.itemType" label="Type" requiredFlag="Y"></q:select>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="iconMap" name="icon" id="icon" value="iconSelectOid" label="Icon" requiredFlag="Y" onchange="showIcon();"></q:select>
			<div id="iconShow"></div>
			<script type="text/javascript">
			function showIcon() {
				var iconOid = $("#icon").val();
				if ( _qifu_please_select_id == iconOid ) {
					$("#iconShow").html( '' );
					return;
				}
				var iconUrl = parent.getIconUrlFromOid( iconOid );
				if (null == iconUrl || '' == iconUrl) {
					$("#iconShow").html( '' );
					return;
				}
				$("#iconShow").html( '<img src="' + iconUrl + '" border="0">' );
			}
			</script>				
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="editMode" id="editMode" label="Edit mode" checkedTest=" \"Y\" == sysProg.editMode "></q:checkbox>			
		</div>
	</div>
</div>	
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="isDialog" id="isDialog" label="Dialog" checkedTest=" \"Y\" == sysProg.isDialog "></q:checkbox>		
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="dialogWidth" value="sysProg.dialogW" id="dialogWidth" label="Dialog width" maxlength="4" placeholder="Enter dialog width"></q:textbox>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="dialogHeight" value="sysProg.dialogH" id="dialogHeight" label="Dialog height" maxlength="4" placeholder="Enter dialog height"></q:textbox>
		</div>
	</div>	
</div>	
</form>
						
<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="Save"
			xhrUrl="./core.sysProgramUpdateJson.do"
			xhrParameter="
			{
				'oid'			:	'${sysProg.oid}',
				'progSystemOid'	:	$('#progSystem').val(),
				'progId'		:	$('#progId').val(),
				'name'			:	$('#name').val(),
				'url'			:	$('#url').val(),
				'itemType'		:	$('#itemType').val(),
				'iconOid'		:	$('#icon').val(),
				'editMode'		:	( $('#editMode').is(':checked') ? 'Y' : 'N' ),
				'isDialog'		:	( $('#isDialog').is(':checked') ? 'Y' : 'N' ),
				'dialogWidth'	:	$('#dialogWidth').val(),
				'dialogHeight'	:	$('#dialogHeight').val()
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