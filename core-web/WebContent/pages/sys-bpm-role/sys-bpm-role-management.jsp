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
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 						field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Id", 						field: "id"					},
		{ name: "Resource name",	field: "name"				},
		{ name: "Role",					field: "role"				},
		{ name: "Task",					field: "taskName"		}
	];
}

function queryClear() {
	$("#resourceOid").val( _qifu_please_select_id );
	$("#taskName").val('');
	
	clearQueryGridTable();
	
}  

function editPage(oid) {
	parent.addTab('CORE_PROG003D0005E', parent.getProgUrlForOid('CORE_PROG003D0005E', oid) );
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.sysBpmResourceRoleDeleteJson.do', 
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
	id="CORE_PROG003D0005Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG003D0005Q');" 
	createNewEnable="Y"
	createNewJsMethod="parent.addTab('CORE_PROG003D0005A', null);"
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG003D0005Q');"
	programName="${programName}"
	programId="${programId}"
	description="Management BPM process role.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp">
	<jsp:param value="Y" name="commonUploadEnable"/>
</jsp:include>

      <div class="row">     
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:select dataSource="resourceMap" name="resourceOid" id="resourceOid" value="" label="Resource"></q:select>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="taskName" value="" id="taskName" label="Task name" placeholder="Enter task name" maxlength="255"></q:textbox>
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
		'parameter[resourceOid]'	: $('#resourceOid').val(),
		'parameter[taskName]'		: $('#taskName').val(),
		'select'					: getQueryGridSelect(),
		'showRow'					: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysBpmResourceRoleQueryGridJson.do" 
	id="CORE_PROG003D0005Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

<br>
<br>
<br>

</body>
</html>