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

function sysChange() {
	
	$('#folderProgOid').find('option').remove().end();
	$("#progListGrid").html( '' );
	
	var sysOid = $("#sysOid").val();
	xhrSendParameter(
			'./core.getCommonProgramFolderJson.do', 
			{ 'oid' : sysOid }, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					
					$('#folderProgOid')
				    .find('option')
				    .remove()
				    .end()
				    .append('<option value="' + _qifu_please_select_id + '">' + _qifu_please_select_name + '</option>')
				    .val( _qifu_please_select_id );
					
					return;
				}
				for (var n in data.value) {
					$('#folderProgOid').append($('<option>', {
					    value: n,
					    text: data.value[n]
					}));
				}
				
			}, 
			function() {
				
			},
			_qifu_defaultSelfPleaseWaitShow
	);	
}

function progFolderChange() {
	
	$("#progListGrid").html( '' );
	var folderProgOid = $("#folderProgOid").val();
	if (null == folderProgOid || '' == folderProgOid || _qifu_please_select_id == folderProgOid) {
		return;
	}
	xhrSendParameter(
			'./core.menuSettingsQueryProgramListByFolderOidJson.do', 
			{ 'oid' : folderProgOid }, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					parent.toastrWarning( data.message );
					return;
				}
				var progAll = data.value.all;
				var progEnable = data.value.enable;
				
				var str = '';
				str += '<table class="table">';
				str += '<thead class="thead-inverse">';
				str += '<tr>';
				str += '<th>&nbsp;&nbsp;#&nbsp;&nbsp;</th>';
				str += '<th>Name</th>';
				str += '</tr>';
				str += '</thead>';
				str += '<tbody>';
				for (var p in progAll) {
					var checkIt = false;
					for (var e in progEnable) {
						if ( progEnable[e].oid == progAll[p].oid ) {
							checkIt = true;
						}
					}
					var chkStr = '';
					if (checkIt) {
						chkStr = ' checked="checked" ';
					}
					str += '<tr>';
					str += '<td>&nbsp;&nbsp;<label class="custom-control custom-checkbox"><input type="checkbox" class="custom-control-input" id="prog' + progAll[p].oid + '" name="prog' + progAll[p].oid + '" onclick="updateMenu();" ' + chkStr + ' value="' + progAll[p].oid + '" ><span class="custom-control-indicator"></span></label></td>';
					str += '<td><img src="' + parent.getIconUrlFromId(progAll[p].icon) + '" border="0">&nbsp;' + progAll[p].name + '</td>';
					str += '</tr>';
				}
				str += '</tbody>';
				str += '</table>';				
				$("#progListGrid").html( str );
			}, 
			function() {
				
			},
			_qifu_defaultSelfPleaseWaitShow
	);	
	
}

function updateMenu() {
	var parentOid = $("#folderProgOid").val();
	var progAppendOid = '';
	$('input.custom-control-input:checkbox:checked').each(function() {
	    progAppendOid += $(this).val() + _qifu_delimiter;
	});
	xhrSendParameterNoPleaseWait(
			'./core.menuSettingsUpdateJson.do', 
			{ 
				'folderProgramOid'	: parentOid,
				'appendOid'			: progAppendOid
			}, 
			function(data) {
				if ( _qifu_success_flag == data.success ) {
					parent.toastrInfo( data.message );
				} else {
					parent.toastrWarning( data.message );
				}
				progFolderChange(); // 重取 table 資料
			}, 
			function() {
				window.location=parent.getProgUrl('CORE_PROG001D0003Q');
			}
	);	
	
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0003Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG001D0003Q');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0003Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="sysMap" name="sysOid" id="sysOid" value="" label="System" requiredFlag="Y" onchange="sysChange();"></q:select>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:select dataSource="folderProgMap" name="folderProgOid" id="folderProgOid" value="" label="Program folder" requiredFlag="Y" onchange="progFolderChange();"></q:select>
		</div>
	</div>		
	<div class="row">
		<div id="progListGrid"></div>
	</div>			
</div>	

</body>
</html>