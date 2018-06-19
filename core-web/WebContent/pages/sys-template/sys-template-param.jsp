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
	queryGrid();
});

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 				field: "oid", 		formatter: getQueryGridFormatter },
		{ name: "Template Id", 		field: "templateId"		},
		{ name: "Variable", 		field: "templateVar"	},
		{ name: "Object variable", 	field: "objectVar"		},
		{ name: "Title only", 		field: "isTitle"		}
	];
}

var msgFields = new Object();
msgFields['templateVar'] 	= 'templateVar';
msgFields['objectVar'] 		= 'objectVar';

var formGroups = new Object();
formGroups['templateVar']	= 'form-group1';
formGroups['objectVar']		= 'form-group1';

function saveSuccess(data) {
	clearWarningMessageField(formGroups, msgFields);
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		setWarningMessageField(formGroups, msgFields, data.checkFields);
		return;
	}
	parent.toastrInfo( data.message );
	clearSave();
	queryGrid();
}

function clearSave() {
	clearWarningMessageField(formGroups, msgFields);
	$("#templateVar").val( '' );
	$("#objectVar").val( '' );
	$("#isTitle").prop('checked', false);
	clearQueryGridTable();
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.templateParamDeleteJson.do', 
						{ 'oid' : oid }, 
						function(data) {
							if ( _qifu_success_flag != data.success ) {
								parent.toastrWarning( data.message );
							}
							if ( _qifu_success_flag == data.success ) {
								parent.toastrInfo( data.message );
							}
							queryGrid();
						}, 
						function() {
							
						},
						_qifu_defaultSelfPleaseWaitShow
				);
			}
	);	
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0004S01Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG001D0004S01Q', '${template.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnSave();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0004S01Q');"
	programName="${programName}"
	programId="${programId}"
	description="Settings template parameter.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	Template&nbsp;:&nbsp;${template.templateId}
        </div>
	</div>        
	<div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="templateVar" value="" id="templateVar" label="Template variable" requiredFlag="Y" maxlength="100" placeholder="Enter template variable"></q:textbox>
       </div>
	</div>
	<div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="objectVar" value="" id="objectVar" label="Object variable" requiredFlag="Y" maxlength="100" placeholder="Enter object variable"></q:textbox>
       </div>
	</div>	
</div>
<div class="form-group" id="form-group2">	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="isTitle" id="isTitle" label="for title only"></q:checkbox>
		</div>
	</div>	
</div>

<p style="margin-bottom: 10px"></p>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
		&nbsp;
		<q:button id="btnSave" label="Save"
			xhrUrl="./core.templateParamSaveJson.do"
			xhrParameter="
			{
				'templateOid'	:	'${template.oid}',
				'templateVar'	:	$('#templateVar').val(),
				'objectVar'		:	$('#objectVar').val(),
				'isTitle'		:	( $('#isTitle').is(':checked') ? 'Y' : 'N' )
			}
			"
			onclick="btnSave();"
			loadFunction="saveSuccess(data);"
			errorFunction="clearSave();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="clearSave();"></q:button>
	</div>
</div>

<p style="margin-bottom: 10px"></p>
<p style="margin-bottom: 10px"></p>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[templateOid]'	: '${template.oid}',
		'select'					: getQueryGridSelect(),
		'showRow'					: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.templateParamQueryGridJson.do" 
	id="CORE_PROG001D0004S01Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>