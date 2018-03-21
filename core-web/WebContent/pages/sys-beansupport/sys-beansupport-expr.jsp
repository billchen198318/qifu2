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
	
	queryGrid();
	
});

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="mapper" title="Variable mapper" src="./images/alert.png" onclick="editVarMapperPage(\'' + value + '\');"/>';	
	str += '&nbsp;&nbsp;';		
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 				field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Expression Id",	field: "exprId"			},
		{ name: "Seq", 				field: "exprSeq"		},
		{ name: "Process type",		field: "runType"		}
	];
}

var msgFields = new Object();
msgFields['expressionOid'] 		= 'expressionOid';
msgFields['exprSeq'] 			= 'exprSeq';
msgFields['runType'] 			= 'runType';

var formGroups = new Object();
formGroups['expressionOid'] 	= 'form-group1';
formGroups['exprSeq'] 			= 'form-group1';
formGroups['runType'] 			= 'form-group1';

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
	$("#expressionOid").val( _qifu_please_select_id );
	$("#runType").val( _qifu_please_select_id );
	$("#exprSeq").val('');
	clearQueryGridTable();
}

function editVarMapperPage(oid) {
	parent.addTab('CORE_PROG003D0003S02Q', parent.getProgUrlForOid('CORE_PROG003D0003S02Q', oid) );
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.sysBeanSupportExpressionDeleteJson.do', 
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
	id="CORE_PROG003D0003S01Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0003S01Q', '${sysBeanHelp.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnSave();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0003S01Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			System&nbsp;:&nbsp;${sysBeanHelp.system}
			<br>
			Bean Id&nbsp;:&nbsp;${sysBeanHelp.beanId}&nbsp;&nbsp;/&nbsp;&nbsp;Method&nbsp;:&nbsp;${sysBeanHelp.method}
		</div>
	</div>			
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="expressionMap" name="expressionOid" id="expressionOid" value="" requiredFlag="Y" label="Expression"></q:select>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="exprSeq" id="exprSeq" value="" maxlength="10" requiredFlag="Y" label="Seq" placeholder="Enter process order seq"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="runTypeMap" name="runType" id="runType" value="" requiredFlag="Y" label="Process type"></q:select>
		</div>
	</div>	
	
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
		&nbsp;	
		<q:button id="btnSave" label="Save"
			xhrUrl="./core.sysBeanSupportExpressionSaveJson.do"
			xhrParameter="	
			{
				'sysBeanHelpOid'	:	'${sysBeanHelp.oid}',
				'expressionOid'		:	$('#expressionOid').val(),
				'exprSeq'			:	$('#exprSeq').val(),
				'runType'			:	$('#runType').val()
			}
			"
			onclick="btnSave();"
			loadFunction="saveSuccess(data);"
			errorFunction="clearSave();">
		</q:button>
		<q:button id="btnClear" label="Clear" onclick="clearSave();"></q:button>
	</div>
</div>

<br>
<br>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[helpOid]'		: '${sysBeanHelp.oid}',
		'select'					: getQueryGridSelect(),
		'showRow'					: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysBeanSupportExpressionQueryGridJson.do" 
	id="CORE_PROG003D0003S01Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>