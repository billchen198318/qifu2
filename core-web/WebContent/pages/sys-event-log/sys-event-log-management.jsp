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
		{ name: "#", 			field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "System",		field: "sysId"			},
		{ name: "User", 		field: "user"			},
		{ name: "Event", 		field: "executeEvent"	},
		{ name: "Permit", 		field: "isPermit"		},
		{ name: "Date time", 	field: "cdateString"	}
	];
}

function queryClear() {
	$("#user").val('');
	$("#sysId").val('');
	
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
						'./core.sysEventLogDeleteJson.do', 
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
	id="CORE_PROG004D0001Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG004D0001Q');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG004D0001Q');"
	programName="${programName}"
	programId="${programId}"
	description="Event history log.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

      <div class="row">     
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="user" value="" id="user" label="Account" placeholder="Enter account" maxlength="24"></q:textbox>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="sysId" value="" id="sysId" label="System Id" placeholder="Enter system id" maxlength="10"></q:textbox>
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
		'parameter[user]'		: $('#user').val(),
		'parameter[sysId]'		: $('#sysId').val(),
		'select'				: getQueryGridSelect(),
		'showRow'				: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysEventLogQueryGridJson.do" 
	id="CORE_PROG004D0001Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

<p style="margin-bottom: 10px"></p>
<p style="margin-bottom: 10px"></p>

<q:button id="btnDeleteAll" label="<i class=\"icon fa fa-trash-o\"></i>&nbsp;Clear log"
	xhrUrl="./core.sysEventLogDeleteAllJson.do"
	xhrParameter="{	}"
	onclick="btnDeleteAll();"
	loadFunction="queryGrid();"
	errorFunction=""
	cssClass="btn btn-warning"
	bootboxConfirm="Y"
	bootboxConfirmTitle="Clear(delete) all log">
</q:button>

<br>
<br>
<br>

</body>
</html>