<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yushkev.onlinetraining.entity.enumtype.CourseStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="title">
	<fmt:message key="label.add_course" />
</c:set>
<c:import url="../fragment/head.jsp" />


<c:import url="../fragment/header.jsp"></c:import>
<c:import url="../fragment/sidenav.jsp"></c:import>

<div class="content-wrapper">
	<div class="page-title">
		<div>
			<h1>
				<i class="fa fa-edit"></i> Form Components
				<!--  page name -->
			</h1>

			<p>Bootstrap default form components</p>
		</div>
		<div>
			<ul class="breadcrumb">
				<li><i class="fa fa-home fa-lg"></i></li>
				<li>Forms</li>
				<li><a href="#">Form Components</a></li>
			</ul>
		</div>
	</div>
	<div class="col-md-12">
		<div class="card">
			<div class="row">
				<div class="col-lg-6">
					<div class="well bs-component">

						<c:choose>
							<c:when test="${param.command == 'add_course_type' || param.command == 'modify_course_type'}">
								<c:import url="addcoursetype_form.jsp"></c:import>
							</c:when>
							<%-- if courseType was added - go back to add_course form --%>
							<c:when test="${not empty courseType}">
								<c:remove var="courseType" scope="session" />
								<jsp:forward page="/controller">
									<jsp:param name="command" value="add_course" />
								</jsp:forward>
							</c:when>
							<c:otherwise>
								<c:import url="addcourse_form.jsp"></c:import>
							</c:otherwise>
						</c:choose>

					</div>


					<!-- </div> -->
				</div>
			</div>
		</div>
	</div>
</div>
</body>

<c:import url="../fragment/footer.jsp" />

<script type="text/javascript">
      
      $('#select_start_date, #select_end_date').datepicker({
      	format: "dd/mm/yyyy",
      	autoclose: true,
      	todayHighlight: true
      });

      $('#select_type, #select_lecturer, #select_existing_course').select2();
      
    </script>




