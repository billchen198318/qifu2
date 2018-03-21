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
	
	$('#progOid').find('option').remove().end();
	$("#roleListGrid").html( '' );	
	
	var sysOid = $("#sysOid").val();
	xhrSendParameter(
			'./core.getCommonProgramFolderMenuItemJson.do', 
			{ 'oid' : sysOid }, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					
					$('#progOid')
				    .find('option')
				    .remove()
				    .end()
				    .append('<option value="' + _qifu_please_select_id + '">' + _qifu_please_select_name + '</option>')
				    .val( _qifu_please_select_id );
					
					return;
				}
				for (var n in data.value) {
					$('#progOid').append($('<option>', {
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

function progChange() {
	
	$("#roleListGrid").html( '' );	
	
	var progOid = $("#progOid").val();
	
	if ( _qifu_please_select_id == progOid || null == progOid ) {
		return;
	}	
	
	xhrSendParameter(
			'./core.queryMenuProgramRoleListByOidJson.do', 
			{ 'oid' : progOid }, 
			function(data) {
				if ( _qifu_success_flag != data.success ) {
					parent.toastrWarning( data.message );
					return;
				}
				
				var roleAll = data.value.all;
				var roleEnable = data.value.enable;
				
				var str = '';
				str += '<table class="table">';
				str += '<thead class="thead-inverse">';
				str += '<tr>';
				str += '<th>&nbsp;&nbsp;#&nbsp;&nbsp;</th>';
				str += '<th>Role</th>';
				str += '</tr>';
				str += '</thead>';
				str += '<tbody>';
				for (var p in roleAll) {
					var checkIt = false;
					for (var e in roleEnable) {
						if ( roleEnable[e].oid == roleAll[p].oid ) {
							checkIt = true;
						}
					}
					var chkStr = '';
					if (checkIt) {
						chkStr = ' checked="checked" ';
					}
					str += '<tr>';
					str += '<td>&nbsp;&nbsp;<label class="custom-control custom-checkbox"><input type="checkbox" class="custom-control-input" id="role' + roleAll[p].oid + '" name="role' + roleAll[p].oid + '" onclick="updateMenuRoleEnable();" ' + chkStr + ' value="' + roleAll[p].oid + '" ><span class="custom-control-indicator"></span></label></td>';
					str += '<td>' + roleAll[p].role + '</td>';
					str += '</tr>';
				}
				str += '</tbody>';
				str += '</table>';				
				$("#roleListGrid").html( str );
				
			}, 
			function() {
				
			},
			_qifu_defaultSelfPleaseWaitShow
	);	
	
	
}

function updateMenuRoleEnable() {
	var progOid = $("#progOid").val();
	var roleAppendOid = '';
	$('input.custom-control-input:checkbox:checked').each(function() {
		roleAppendOid += $(this).val() + _qifu_delimiter;
	});
	xhrSendParameterNoPleaseWait(
			'./core.menuRoleUpdateJson.do', 
			{ 
				'progOid'		: progOid,
				'appendOid'		: roleAppendOid
			}, 
			function(data) {
				if ( _qifu_success_flag == data.success ) {
					parent.toastrInfo( data.message );
				} else {
					parent.toastrWarning( data.message );
				}
				progChange(); // 重取 table 資料
			}, 
			function() {
				window.location=parent.getProgUrl('CORE_PROG002D0003Q');
			}
	);	
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG002D0003Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG002D0003Q');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG002D0003Q');" >
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
			<q:select dataSource="progMap" name="progOid" id="progOid" value="" label="System" requiredFlag="Y" onchange="progChange();"></q:select>
		</div>
	</div>
	<div class="row">
		<div id="roleListGrid"></div>
	</div>		
</div>	

</body>
</html>