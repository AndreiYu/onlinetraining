<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<form id="add_course" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="add_course" />
</form>
<form id="view_courses" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="view_courses" />
</form>
<form id="view_users" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="view_users" />
</form>
<form id="view_enrolments" action="${contextPath}/controller" method="post">
	<input type="hidden" name="command" value="view_enrolments" />
</form>

<!-- Side-Nav-->
<aside class="main-sidebar hidden-print">
	<section class="sidebar">
		<div class="user-panel">
			<div class="pull-left image">
				<msgtag:display_img cssClass="img-circle" />
			</div>
			<div class="pull-left info">
				<p><c:out value="${user.first_name} ${user.last_name}"/></p>
				<p class="designation"><c:out value="${user.role}"/></p>
			</div>
		</div>
		
		<!-- Sidebar Menu-->
		<ul class="sidebar-menu">
			<li class="active"><a href="#"><i class="fa fa-dashboard"></i><span><fmt:message key="label.dashboard" /></span></a></li>
			<li class="treeview"><a href="#"><i class="fa fa-mortar-board"></i><span> <fmt:message
							key="label.manage_courses" /></span><i class="fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a href="#" onclick="frmsubmit('add_course');"><i class="fa fa-circle-o"></i> <fmt:message
								key="label.add_or_modify" /></a></li>
					<li><a href="#" onclick="frmsubmit('view_courses');"><i class="fa fa-circle-o"></i> <fmt:message
								key="label.view_all" /></a></li>
				</ul></li>
			<li class="treeview"><a href="#"><i class="fa fa-university"></i><span> Enrolments</span><i class="fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a href="#" onclick="frmsubmit('view_enrolments');"><i class="fa fa-circle-o"></i> View Enrolments</a></li>
					<li><a href="#" onclick="frmsubmit('view_certificates');"><i class="fa fa-circle-o"></i> View Certificates</a></li>
				</ul></li>
			<!-- 	<li><a href="#" onclick="frmsubmit('view_users');"><i class="fa fa-users"></i><span> Users</span></a></li> -->

			<li class="treeview"><a href="#"><i class="fa fa-users"></i><span> Manage Users</span><i class="fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a href="#" onclick="frmsubmit('view_users');"><i class="fa fa-circle-o"></i> Lecturers</a></li>
					<li><a href="#" onclick="frmsubmit('view_users');"><i class="fa fa-circle-o"></i> Students</a></li>
					<li><a href="#" onclick="frmsubmit('view_users');"><i class="fa fa-circle-o"></i> Banned Users</a></li>
				</ul></li>
		</ul>
	</section>
</aside>

