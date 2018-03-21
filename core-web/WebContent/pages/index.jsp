<%@page import="org.qifu.util.SystemSettingConfigureUtils"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="q" uri="http://www.qifu.org/controller/tag" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String leftMenuEnable = SystemSettingConfigureUtils.getLeftAccordionContainerEnableValue();

%>

<!DOCTYPE html>
<html>
<head>
<title>qifu</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="./tether/tether.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script type="text/javascript" src="./tether/tether.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
<script type="text/javascript" src="./jquery/jquery-3.2.1.min.js?ver=${jsVerBuild}"></script>
<link rel="stylesheet" href="./bootstrap-vali/css/main.css?ver=${jsVerBuild}" crossorigin="anonymous">
<link href="./font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}" rel="stylesheet" type="text/css" />
<script src="./bootstrap-vali/js/bootstrap.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="./bootbox/bootbox.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<link rel="stylesheet" href="./toastr/toastr.min.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script src="./toastr/toastr.min.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<link rel="stylesheet" href="./css/m.css?ver=${jsVerBuild}" crossorigin="anonymous">
<script src="./configJs.do?ver=${jsVerBuild}" crossorigin="anonymous"></script>
<script src="./js/m.js?ver=${jsVerBuild}" crossorigin="anonymous"></script>

<style type="text/css">


</style>


<script type="text/javascript">

var _m_PAGE_CHANGE_URL_PARAM = 'isQifuPageChange';

// =====================================================================
${menuJavascriptData}
//=====================================================================

${iconJavascriptData}

function getIconUrlFromOid(oid) {
	var iconUrl = '';
	for (var n in _iconData) {
		if (_iconData[n].oid == oid) {
			iconUrl = '<%=basePath%>/icons/' + _iconData[n].fileName;
		}
	}
	return iconUrl;
}

function getIconUrlFromId(id) {
	var iconUrl = '';
	for (var n in _iconData) {
		if (_iconData[n].iconId == id) {
			iconUrl = '<%=basePath%>/icons/' + _iconData[n].fileName;
		}
	}
	return iconUrl;
}

$( document ).ready(function() {
	
	$('#myTab').bind('show', function(e) {  
	    var paneID = $(e.target).attr('href');
	    var src = $(paneID).attr('data-src');
	    // if the iframe hasn't already been loaded once
	    if($(paneID+" iframe").attr("src")=="")
	    {
	        $(paneID+" iframe").attr("src",src);
	    }
	});
	
	// first load on config
	${firstLoadJavascript}
	
});

</script>


</head>


<body>


<!-- Modal Start here -->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="myPleaseWait" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallModalLabel">Please wait!</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./images/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here -->

<!-- Modal Start here for page query grid -->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallQueryGridModalLabel" aria-hidden="true" id="myPleaseWaitForQueryGrid" data-keyboard="false" data-backdrop="static">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallQueryGridModalLabel">Please wait!</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./images/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here for page query grid -->


<!-- ##################### Modal for Program ##################### -->
${modalHtmlData}
<!-- ##################### Modal for Program ##################### -->


    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <a class="navbar-brand" href="./index.do"><img alt="祈-qífú" src="./images/logo3.png" border="0"></a>
      <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      
      <div class="collapse navbar-collapse" id="navbarsExampleDefault">
        <ul class="navbar-nav mr-auto">
		  <li class="nav-item dropdown active">
			<a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			  <b>Application</b>
			</a>
			<div class="dropdown-menu scrollable-menu" aria-labelledby="navbarDropdownMenuLink">
			  
			  ${dropdownHtmlData}
			  
			  <a class="dropdown-item" href="./index.do"><img src="./icons/view-refresh.png" broder="0">&nbsp;&nbsp;<b>Refresh</b></a>
			  
			</div>
		  </li>
          <li class="nav-item">
            <a class="nav-link" href="#" onclick="addTab('CORE_PROG999D9999Q', null);"><b>About</b></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" onclick="logoutEvent();"><b>Logout</b></a>
          </li>		  
        </ul>
        <!-- 
        <form class="form-inline mt-2 mt-md-0">
          <input class="form-control mr-sm-2" type="text" placeholder="Search">
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
        -->
      </div>
    </nav>


<%
if ("Y".equals(leftMenuEnable)) { // 有啟動左邊的選單
%> 
    <div class="container-fluid">
      <div class="row">
           
        <nav class="col-md-2 d-none d-md-block bg-light sidebar"><!-- 2018-01-24 col-sm-3 col-md-2 hidden-xs-down d-none d-sm-block bg-light -->
		
			${navItemHtmlData}
		  
        </nav>              

        <main class="col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3">
		
			<ul class="nav nav-tabs" id="myTab" role="tablist">

			</ul>

			<!-- Tab panes -->
			<div class="tab-content" id="myTabContent">
				

			</div>
			
			
        </main>
      </div>
    </div>
<%
} else { // 沒有啟動左邊的選單
%>      
		<main class="pt-3">
		
			<ul class="nav nav-tabs" id="myTab" role="tablist">

			</ul>

			<!-- Tab panes -->
			<div class="tab-content" id="myTabContent">
				

			</div>
			
		</main>
<%
}
%>

</body>
  
  
</html>
