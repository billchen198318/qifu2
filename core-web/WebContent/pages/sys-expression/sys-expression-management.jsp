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

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="edit" title="Edit" src="./images/edit.png" onclick="editPage(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';		
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 			field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Id", 			field: "exprId"			},
		{ name: "Name",			field: "name"			},
		{ name: "Type", 		field: "type"			},
		{ name: "Description",	field: "description"	}
	];
}

function queryClear() {
	$("#type").val( _qifu_please_select_id );
	$("#exprId").val('');
	$("#name").val('');
	
	clearQueryGridTable();
	
}  

function editPage(oid) {
	parent.addTab('CORE_PROG003D0002E', parent.getProgUrlForOid('CORE_PROG003D0002E', oid) );
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.sysExpressionDeleteJson.do', 
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
	id="CORE_PROG003D0002Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG003D0002Q');" 
	createNewEnable="Y"
	createNewJsMethod="parent.addTab('CORE_PROG003D0002A', null);"
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0002Q');"
	programName="${programName}"
	programId="${programId}"
	description="Management expression item.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

      <div class="row">     
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="exprId" value="" id="exprId" label="Id" placeholder="Enter Id" maxlength="20"></q:textbox>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="name" value="" id="name" label="Name" placeholder="Enter name" maxlength="100"></q:textbox>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:select dataSource="typeMap" name="type" id="type" value="" label="Type"></q:select>
        </div>         
      </div>
      
<p style="margin-bottom: 10px"></p>
      
<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();"><i class="icon fa fa-search"></i>&nbsp;Query</button>
<button type="button" class="btn btn-primary" id="btnClear" onclick="queryClear();"><i class="icon fa fa-hand-paper-o"></i>&nbsp;Clear</button>

<p style="margin-bottom: 10px"></p>
<p style="margin-bottom: 10px"></p>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[type]'		: $('#type').val(),
		'parameter[exprId]'		: $('#exprId').val(),
		'parameter[name]'		: $('#name').val(),
		'select'				: getQueryGridSelect(),
		'showRow'				: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysExpressionQueryGridJson.do" 
	id="CORE_PROG003D0002Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

<br>
<br>

</body>
</html>