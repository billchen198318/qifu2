<%@page import="org.qifu.util.SystemSettingConfigureUtils"%>
<%@page import="org.qifu.base.Constants"%>
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
	<meta name="description" content="QiFu 2 is an JAVA base backend WEB system.">
    <title>QiFu-2</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="./bootstrap-vali/css/main.css?ver=${jsVerBuild}">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="./font-awesome/css/font-awesome.min.css?ver=${jsVerBuild}">
    
    <!-- toastr css -->
    <link rel="stylesheet" type="text/css" href="./toastr/toastr.min.css?ver=${jsVerBuild}">
    
    
    <link rel="stylesheet" href="./css/m.css?ver=${jsVerBuild}" >

</head>


<body class="app sidebar-mini">


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

    <!-- Navbar-->
    <header class="app-header"><a class="app-header__logo" href="<%=basePath%>/index.do">QíFú</a>
      <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
      <!-- Navbar Right Menu-->
      <ul class="app-nav">
        <!-- User Menu-->
        <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Open Profile Menu"><i class="fa fa-user fa-lg"></i></a>
          <ul class="dropdown-menu settings-menu dropdown-menu-right">
            <li><a class="dropdown-item" href="javascript:logoutEvent();"><i class="fa fa-sign-out fa-lg"></i> Logout</a></li>
          </ul>
        </li>
      </ul>
    </header>
    

    <!-- Sidebar menu-->
    <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
	
    <aside class="app-sidebar">
      
      <ul class="app-menu">
		
       	${navItemHtmlData}
       	
       	
       	<li><a class="app-menu__item" href="#" onclick="addTab('CORE_PROG999D9999Q', null);"><i class="app-menu__icon fa fa-info-circle"></i><span class="app-menu__label">About</span></a></li>
       	<li><a class="app-menu__item" href="#" onclick="logoutEvent();"><i class="app-menu__icon fa fa-power-off"></i><span class="app-menu__label">Logout</span></a></li>
       	
      </ul>
    </aside>
	
    <main class="app-content">
	<nav class="tabbable">
			<ul class="nav nav-tabs" id="myTab" role="tablist">

			</ul>

			<!-- Tab panes -->
			<div class="tab-content" id="myTabContent">
				

			</div>
	</nav>
    </main>
    
    
    <!-- Config variable -->
    <script src="./configJs.do?ver=${jsVerBuild}" ></script>
    
<script type="text/javascript">

var _m_PAGE_CHANGE_URL_PARAM = '<%=Constants.QIFU_PAGE_IN_TAB_IFRAME%>';

// =====================================================================
${menuJavascriptData}
// =====================================================================

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

</script>    
    
    <!-- Essential javascripts for application to work-->
    <script type="text/javascript" src="./jquery/jquery-3.3.1.min.js?ver=${jsVerBuild}"></script>
    <script type="text/javascript" src="./popper-js/umd/popper.min.js?ver=${jsVerBuild}"></script>
    <script src="./bootstrap-vali/js/bootstrap.js?ver=${jsVerBuild}" ></script>
    <script src="./js/main.js?ver=${jsVerBuild}"></script>   
    <!-- The javascript plugin to display page loading on top-->
    <script src="./js/plugins/pace.min.js?ver=${jsVerBuild}"></script>
    
    <!-- bootbox & toastr -->
    <script src="./bootbox/bootbox.all.js?ver=${jsVerBuild}" ></script>
    <script src="./toastr/toastr.min.js?ver=${jsVerBuild}" ></script>
    
    <!-- custom function -->
    <script src="./js/m.js?ver=${jsVerBuild}" ></script>
    
    
<script type="text/javascript">
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
    

</body>
  
  
</html>
