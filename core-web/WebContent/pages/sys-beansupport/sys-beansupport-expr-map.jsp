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
	
	$("#methodParamIndex").ForceNumericOnly();
	queryGrid();
	
});

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 				field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Variable",	field: "varName"			},
		{ name: "Method result", 				field: "methodResultFlag"		},
		{ name: "Method param class",		field: "methodParamClass"		},
		{ name: "Method param index",		field: "methodParamIndex"		}
	];
}

var msgFields = new Object();
msgFields['varName'] 		= 'varName';
msgFields['methodParamClass'] 			= 'methodParamClass';
msgFields['methodParamIndex'] 			= 'methodParamIndex';

var formGroups = new Object();
formGroups['varName'] 	= 'form-group1';
formGroups['methodParamClass'] 			= 'form-group2';
formGroups['methodParamIndex'] 			= 'form-group2';

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
	$("#varName").val('');
	$("#methodResultFlag").prop('checked', false);
	$("#methodParamClass").val('');
	$("#methodParamIndex").val('');
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
						'./core.sysBeanSupportExpressionParamDeleteJson.do', 
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
	id="CORE_PROG003D0003S02Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrlForOid('CORE_PROG003D0003S02Q', '${sysBeanHelpExpr.oid}');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="Y" 
	saveJsMethod="btnSave();" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0003S02Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			Expresion Id&nbsp;/&nbsp;SEQ&nbsp;/&nbsp;Type
			<br>
			${sysBeanHelpExpr.exprId}&nbsp;/&nbsp;${sysBeanHelpExpr.exprSeq}&nbsp;/&nbsp;${sysBeanHelpExpr.runType}
		</div>
	</div>			
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="varName" id="varName" value="" maxlength="255" label="Variable" requiredFlag="Y"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="methodResultFlag" id="methodResultFlag" label="Method result"></q:checkbox>
		</div>
	</div>
</div>
<div class="form-group" id="form-group2">	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="methodParamClass" id="methodParamClass" value="" maxlength="255" label="Method parameter class" requiredFlag="Y"></q:textbox>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="methodParamIndex" id="methodParamIndex" value="" maxlength="2" label="Method parameter index" requiredFlag="Y"></q:textbox>
		</div>
	</div>		
</div>

<br>

<div class="row">
	<div class="col-xs-6 col-md-6 col-lg-6">
		<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
		&nbsp;	
		<q:button id="btnSave" label="Save"
			xhrUrl="./core.sysBeanSupportExpressionParamSaveJson.do"
			xhrParameter="	
			{
				'sysBeanHelpExprOid'	:	'${sysBeanHelpExpr.oid}',
				'varName'			:	$('#varName').val(),
				'methodResultFlag'			:	( $('#methodResultFlag').is(':checked') ? 'Y' : 'N' ),
				'methodParamClass'			:	$('#methodParamClass').val(),
				'methodParamIndex'			:	( isNaN(parseInt( $('#methodParamIndex').val() )) ? -1 : parseInt( $('#methodParamIndex').val() ) )
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
		'parameter[helpExprOid]'		: '${sysBeanHelpExpr.oid}',
		'select'					: getQueryGridSelect(),
		'showRow'					: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysBeanSupportExpressionParamQueryGridJson.do" 
	id="CORE_PROG003D0003S02Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>