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
msgFields['systemOid'] 	= 'systemOid';
msgFields['className'] 	= 'className';
msgFields['type'] 		= 'type';

var formGroups = new Object();
formGroups['systemOid'] = 'form-group1';
formGroups['className'] = 'form-group2';
formGroups['type'] 		= 'form-group3';

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
	window.location=parent.getProgUrlForOid('CORE_PROG001D0006E', '${sysCtxBean.oid}');
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0006E_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG001D0006E', '${sysCtxBean.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnUpdate();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0006E');"
	programName="${programName}"
	programId="${programId}"
	description="Modify server container custom context initialization/destory service.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="sysMap" name="systemOid" id="systemOid" value="systemOid" requiredFlag="Y" label="System"></q:select>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group2">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="className" id="className" value="sysCtxBean.className" maxlength="255" requiredFlag="Y" placeholder="Enter class name"></q:textbox>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group3">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="ctxBeanTypesMap" name="type" id="type" value="sysCtxBean.type" requiredFlag="Y" label="Type"></q:select>
		</div>
	</div>	
</div>
<div class="form-group" id="form-group4">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textarea name="description" value="sysCtxBean.description" id="description" label="Description" rows="3" placeholder="Enter description"></q:textarea>
		</div>
	</div>
</div>

<p style="margin-bottom: 10px"></p>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<q:button id="btnUpdate" label="<i class=\"icon fa fa-floppy-o\"></i>&nbsp;Save"
			xhrUrl="./core.sysCtxbeanUpdateJson.do"
			xhrParameter="
			{
				'oid'			:	'${sysCtxBean.oid}',
				'systemOid'		:	$('#systemOid').val(),
				'className'		:	$('#className').val(),
				'type'			:	$('#type').val(),
				'description'	:	$('#description').val()
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