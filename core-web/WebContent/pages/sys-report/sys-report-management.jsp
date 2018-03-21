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

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="edit" title="Edit" src="./images/edit.png" onclick="editPage(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';
	str += '<img alt="edit param" title="Edit parameter" src="./images/alert.png" onclick="editParam(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';	
	str += '<img alt="preview" title="Preview report" src="./images/service.png" onclick="previewShow(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';	
	str += '<img alt="download" title="Download source" src="./images/download.png" onclick="downloadReportSrc(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';		
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 			field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Id", 			field: "reportId"		},
		{ name: "File", 		field: "file"			},
		{ name: "Compile", 		field: "isCompile"		},
		{ name: "Description", 	field: "description"	}
	];
}

function queryClear() {
	$("#id").val('');
	
	clearQueryGridTable();
	
}  

function editPage(oid) {
	parent.addTab('CORE_PROG001D0005E', parent.getProgUrlForOid('CORE_PROG001D0005E', oid) );
}

function editParam(oid) {
	parent.addTab('CORE_PROG001D0005S01Q', parent.getProgUrlForOid('CORE_PROG001D0005S01Q', oid) );
}

function previewShow(oid) {
	parent.showModal( 'CORE_PROG001D0005S02Q', parent.getProgUrlForOid('CORE_PROG001D0005S02Q', oid) );
}

function downloadReportSrc(oid) {
	xhrSendParameter(
			'./core.sysReportDownloadContentJson.do', 
			{ 'oid' : oid }, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					parent.toastrWarning( data.message );
					return;
				}
				parent.toastrInfo( data.message );
				commonDownloadFile( data.value );
			}, 
			function() {
				
			},
			_qifu_defaultSelfPleaseWaitShow
	);
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.sysReportDeleteJson.do', 
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
	id="CORE_PROG001D0005Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG001D0005Q');" 
	createNewEnable="Y"
	createNewJsMethod="parent.addTab('CORE_PROG001D0005A', null);"
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0005Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp">
	<jsp:param value="Y" name="commonUploadEnable"/>
</jsp:include>

      <div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="id" value="" id="id" label="Id" placeholder="Enter Id" maxlength="50"></q:textbox>
        </div>
      </div>
      
<br>
      
<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
<button type="button" class="btn btn-primary" id="btnClear" onclick="queryClear();">Clear</button>

<br>
<br>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[reportId]'	: $('#id').val(),
		'select'				: getQueryGridSelect(),
		'showRow'				: getQueryGridShowRow()	
	}
	"
	xhrUrl="./core.sysReportQueryGridJson.do" 
	id="CORE_PROG001D0005Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>