<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<form id="logout" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="logout" />
</form>
<form id="view_profile" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="view_profile" />
</form>

<body class="sidebar-mini fixed">
	<div class="wrapper">

		<!-- Navbar-->
		<header class="main-header hidden-print">
			<a class="logo" href="${contextPath}"><fmt:message key="label.login.header" /></a>
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a class="sidebar-toggle" href="#" data-toggle="offcanvas"></a>

				<!-- Navbar Right Menu-->
				<div class="navbar-custom-menu">
					<ul class="top-nav">
						<!-- 	"javascript:window.history.back();"             ${fromPagePath} -->
						<li class="dropdown notification-menu"><a class="dropdown-toggle" href="javascript:window.history.back();"><i
								class="fa fa-arrow-left fa-lg"></i></a>
						<li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false"><i class="fa fa-user fa-lg"></i></a>
							<ul class="dropdown-menu settings-menu">
								<li><a href="#" onclick="frmsubmit('view_profile');"><i class="fa fa-wrench fa-lg"></i> <fmt:message
											key="label.profile" /></a></li>
								<li><a href="#" onclick="frmsubmit('logout');"><i class="fa fa-user fa-lg"></i> <fmt:message key="label.logout" /></a></li>
							</ul></li>
					</ul>
				</div>
			</nav>
		</header>