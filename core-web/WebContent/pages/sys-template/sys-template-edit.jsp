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

<!-- Include Editor style. -->
<link href="./froala_editor/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
<link href="./froala_editor/css/froala_style.min.css" rel="stylesheet" type="text/css" />
<!-- Include Editor JS files. -->
<script type="text/javascript" src="./froala_editor/js/froala_editor.pkgd.min.js"></script>

<style type="text/css">


</style>


<script type="text/javascript">

$( document ).ready(function() {
	
	$('#message').froalaEditor({
		height: 250
	});
	
});

var msgFields = new Object();
msgFields['templateId'] 	= 'id';
msgFields['title'] 			= 'title';
msgFields['message'] 		= 'message';

var formGroups = new Object();
formGroups['id'] 		= 'form-group1';
formGroups['title'] 	= 'form-group1';
formGroups['message'] 	= 'form-group2';

function updateSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	updateSave();
}

function updateSave() {
	clearWarningMessageField(formGroups, msgFields);
	$("#title").val( '' );
	$('#message').froalaEditor('html.set', '');
	$("#description").val( '' );
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0004E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG001D0004E', '${template.oid}');"  
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0004E');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="id" value="template.templateId" id="id" label="Id" requiredFlag="Y" maxlength="10" placeholder="Enter Id" readonly="Y"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="title" value="template.title" id="title" label="Title" requiredFlag="Y" maxlength="200" placeholder="Enter title"></q:textbox>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group2">
	<q:textarea name="message" id="message" value="template.message" label="Message" requiredFlag="Y" escapeHtml="N"></q:textarea>
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" value="template.description" id="description" label="Description" rows="3" placeholder="Enter descripnion"></q:textarea>
		</div>
	</div>
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="Save"
			xhrUrl="./core.templateUpdateJson.do"
			xhrParameter="
			{
				'oid'			:	'${template.oid}',
				'templateId'	:	'${template.templateId}',
				'title'			:	$('#title').val(),
				'description'	:	$('#description').val(),
				'message'		:	$('#message').froalaEditor('html.get')
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="updateSave();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="updateSave();"></q:button>
	</div>
</div>

</body>
</html>