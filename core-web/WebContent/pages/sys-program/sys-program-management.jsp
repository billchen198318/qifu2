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
<title>qifu2</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="../common-f-inc.jsp"></jsp:include>

<style type="text/css">


</style>


<script type="text/javascript">

function getQueryGridFormatter(value) {
	var str = '';
	str += '<img alt="edit" title="Edit" src="./images/edit.png" onclick="editPage(\'' + value + '\');"/>';
	str += '&nbsp;&nbsp;';
	str += '<img alt="delete" title="Delete" src="./images/delete.png" onclick="deleteRecord(\'' + value + '\');"/>';
	return str;
}
function getQueryGridHeader() {
	return [
		{ name: "#", 	field: "oid", 	formatter: getQueryGridFormatter },
		{ name: "Id&nbsp;<img class='btn btn-light btn-sm' src='./images/chevron-arrow-up.png' onclick='queryAsc(\"id\");'/>&nbsp;<img class='btn btn-light btn-sm' src='./images/chevron-arrow-down.png' onclick='queryDesc(\"id\");'/>", 	field: "progId"					},
		{ name: "Name&nbsp;<img class='btn btn-light btn-sm' src='./images/chevron-arrow-up.png' onclick='queryAsc(\"name\");'/>&nbsp;<img class='btn btn-light btn-sm' src='./images/chevron-arrow-down.png' onclick='queryDesc(\"name\");'/>", field: "name"					},
		{ name: "Type", field: "itemType"				},
		{ name: "System", field: "progSystem"	},
		{ name: "Url", field: "url"					},
		{ name: "Edit mode", field: "editMode"	}
	];
}

function queryClear() {
	$("#id").val('');
	$("#name").val('');
	$('#sortType').val('ASC');
	$('#orderBy').val('sp.progId,sp.name');
	
	clearQueryGridTable();
	
}  

function editPage(oid) {
	parent.addTab('CORE_PROG001D0002E', parent.getProgUrlForOid('CORE_PROG001D0002E', oid) );
}

function deleteRecord(oid) {
	parent.bootbox.confirm(
			"Delete?", 
			function(result) { 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./core.sysProgramDeleteJson.do', 
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


function queryAsc(type) {
	$('#sortType').val('ASC');
	if ('id' == type) {
		$('#orderBy').val('sp.progId');
	}
	if ('name' == type) {
		$('#orderBy').val('sp.name');
	}	
	queryGrid();
}
function queryDesc(type) {
	$('#sortType').val('DESC');
	if ('id' == type) {
		$('#orderBy').val('sp.progId');
	}
	if ('name' == type) {
		$('#orderBy').val('sp.name');
	}	
	queryGrid();
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0002Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG001D0002Q');" 
	createNewEnable="Y"
	createNewJsMethod="parent.addTab('CORE_PROG001D0002A', null);"
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0002Q');"
	programName="${programName}"
	programId="${programId}"
	description="Management program item.">		
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>
      
<input type="hidden" id="sortType" name="sortType" value="ASC">
<input type="hidden" id="orderBy" name="orderBy" value="sp.progId,sp.name">
      
      <div class="row">
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="id" value="" id="id" label="Id" placeholder="Enter Id" maxlength="50"></q:textbox>
        </div>
        <div class="col-xs-6 col-md-6 col-lg-6">
        	<q:textbox name="name" value="" id="name" label="Name" placeholder="Enter name" maxlength="100"></q:textbox>
       </div>
      </div>

<p style="margin-bottom: 10px"></p>
      
<button type="button" class="btn btn-primary" id="btnQuery" onclick="queryGrid();">Query</button>
<button type="button" class="btn btn-primary" id="btnClear" onclick="queryClear();">Clear</button>

<p style="margin-bottom: 10px"></p>
<p style="margin-bottom: 10px"></p>

<q:grid gridFieldStructure="getQueryGridHeader()" 
	xhrParameter="
	{
		'parameter[progId]'	: $('#id').val(),
		'parameter[name]'	: $('#name').val(),
		'select'			: getQueryGridSelect(),
		'showRow'			: getQueryGridShowRow(),
		'sortType'			: $('#sortType').val(),
		'orderBy'			: $('#orderBy').val()
	}
	"
	xhrUrl="./core.sysProgramQueryGridJson.do" 
	id="CORE_PROG001D0002Q_grid"
	queryFunction="queryGrid()"
	clearFunction="clearQueryGridTable()">
</q:grid>

</body>
</html>