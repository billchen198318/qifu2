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

function updateSuccess(data) {
	if ( _qifu_success_flag != data.success ) {
		parent.toastrWarning( data.message );
		return;
	}
	parent.toastrInfo( data.message );
}

</script>

</head>

<body>

<q:toolBar 
	id="CORE_PROG001D0007Q_toolbar" 
	refreshEnable="Y"
	refreshJsMethod="window.location=parent.getProgUrl('CORE_PROG001D0007Q');" 
	createNewEnable="N"
	createNewJsMethod=""
	saveEnabel="N" 
	saveJsMethod="" 	
	cancelEnable="Y" 
	cancelJsMethod="parent.closeTab('CORE_PROG001D0007Q');" >
</q:toolBar>
<jsp:include page="../common-f-head.jsp"></jsp:include>

<div class="form-group" id="form-group1">
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:textbox name="mailFrom" id="mailFrom" value="mailFrom" requiredFlag="Y" label="Mail from" maxlength="100"></q:textbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="mailEnable" id="mailEnable" checkedTest=" \"Y\" == mailEnable " label="Mail sender enable"></q:checkbox>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
			<q:checkbox name="leftMenu" id="leftMenu" checkedTest=" \"Y\" == leftMenu " label="Left menu enable (need re-login to change)"></q:checkbox>
		</div>
	</div>	
	<div class="row">
		<div class="col-xs-6 col-md-6 col-lg-6">
		
		<q:button id="btnUpdate" label="Save"
			xhrUrl="./core.sysSettingUpdateJson.do"
			xhrParameter="
			{
				'mailFrom'		:	$('#mailFrom').val(),	
				'mailEnable'	:	( $('#mailEnable').is(':checked') ? 'Y' : 'N' ),
				'leftMenu'		:	( $('#leftMenu').is(':checked') ? 'Y' : 'N' )
			}
			"
			onclick="btnUpdate();"
			loadFunction="updateSuccess(data);"
			errorFunction="clearUpdate();">
		</q:button>	
		
		</div>
	</div>		
</div>	

</body>
</html>