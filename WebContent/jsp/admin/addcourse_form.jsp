<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.yushkev.onlinetraining.entity.enumtype.CourseStatus"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- if previously course to modify was chosen, fill parameters in form-->
<c:if test="${allCourses != null && selected_item != null && selected_item != ''}">
	<c:forEach var="course" items="${allCourses}">
		<c:if test="${course.id eq selected_item}">
			<c:set var="course_to_modify" value="${course}" scope="request" />
		</c:if>
	</c:forEach>
</c:if>


<%-- <form action="${contextPath}/controller" method="POST" id="add_course_type">
	<input type="hidden" name="command" value="${course_type_id !='' ? 'modify_course_type' : 'add_course_type'}" /> <input
		type="hidden" name="selected_item" value="course_type_id" /><!--  duplicate name? -->
</form>
<form action="${contextPath}/controller" method="POST" id="add_lecturer">
	<input type="hidden" name="command" value="add_lecturer" />
</form> --%>

<form action="${contextPath}/controller" method="POST" id="modify_course">
	<input type="hidden" name="command" value="modify_course" />
	<input type="hidden" name="selected_item" value="${param.selected_item1}" />
</form>
<form action="${contextPath}/controller" method="POST" id="reset_data">
	<input type="hidden" name="command" value="${param.command}" />
</form>

<form class="form-horizontal" action="${contextPath}/controller" method="POST" enctype="multipart/form-data">
	<!-- name of previously defined command (add or modify) First - add from sidenav, then - modify pressed in another form here -->
	<input type="hidden" name="command" value="${param.command}" />
	<!-- define selected course to show after page refreshing -->
	<input type="hidden" name="selected_item" value="${selected_item}" />
	<!--  add_course or modify_course -->
	<fieldset>
		<legend>
			<fmt:message key="label.course.find_to_modify" />
		</legend>
		<!--           	error messages printing -->
		<msgtag:display_msg />
		<!--           	end of tag -->

		<!--           	modify existing -->
		<div class="form-group">
			<select class="form-control" name="selected_item1" id="select_existing_course" form="modify_course">
				<option label=""></option>
				<c:forEach var="course" items="${allCourses}">
					<option value="${course.id}" ${course.id eq courseToModify.id ? 'selected' : ''}>${course.id}&nbsp;${course.title}</option>
				</c:forEach>
			</select> <br>
			<button class="btn btn-primary icon-btn" name="choose_to_modify" value="choose_to_modify" type="submit" form="modify_course">
				<!-- user may choose but doon't click, so modify command will not be applied  -->
				<fmt:message key="label.modify_existing" />
			</button>
			&nbsp;&nbsp;&nbsp;
		</div>
		<!--     end      	modify existing -->

		<!--     add new course			    -->
		<legend>
			<fmt:message key="label.course.or_add_new" />
		</legend>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.title" /></label> <input class="form-control" type="text"
				name="course_title" pattern="(^[а-яА-ЯёЁa-zA-Z0-9].*){1,250}" title="<fmt:message key="message.modify_course.wrong_title"/>"
				placeholder="<fmt:message key="label.course.title.placeholder"/>"
				value="<c:out value="${courseToModify != null ? courseToModify.title : param.course_title}"/>" required>
		</div>

		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.type" /></label>&nbsp;&nbsp;&nbsp; <select class="form-control"
				name="course_type_id" required>
				<option label=""></option>
				<c:forEach items="${courseTypes}" var="course_type_id">
					<option value="${course_type_id.id}"
						${(course_type_id.id eq param.course_type_id) || (course_type_id.id eq courseToModify.typeId) ? 'selected' : ''}>${course_type_id.category}</option>
				</c:forEach>
			</select> <br>
			<button class="btn btn-default btn-xs" form="add_course_type">
				<fmt:message key="label.add_new" />
				&nbsp;&nbsp;&nbsp;
			</button>

		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.lecturer" /></label> &nbsp;&nbsp;&nbsp;<select
				class="form-control" id="select_lecturer" name="course_lecturer" required>
				<option label=""></option>
				<c:forEach var="course_lecturer" items="${lecturers}">
					<option value="${course_lecturer.id}"
						${(course_lecturer.id eq param.course_lecturer) || (course_lecturer.id eq courseToModify.lecturerId) ? 'selected' : ''}>
						${course_lecturer.first_name} ${course_lecturer.last_name}</option>
				</c:forEach>
			</select> <br>
			<button class="btn btn-default btn-xs" form="add_lecturer">
				<fmt:message key="label.add_new" />
			</button>
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.status" /></label>&nbsp;&nbsp;&nbsp; <select class="form-control"
				id="select_status" name="course_status" required>
				<option label=""></option>
				<c:forEach var="course_status" items="${CourseStatus.values()}">
					<option value="${course_status}"
						${(course_status eq param.course_status) || (course_status eq courseToModify.status) ? 'selected' : ''}>${course_status}</option>
				</c:forEach>
			</select>
		</div>

		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.startdate" /></label> <input class="form-control"
				id="select_start_date" name="course_startdate" type="text" placeholder="<fmt:message key="label.course.date.placeholder"/>"
				value="<c:out value="${courseToModify != null ? courseToModify.startDateString : param.course_startdate}" />">
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.enddate" /></label> <input class="form-control"
				id="select_end_date" name="course_enddate" type="text" placeholder="<fmt:message key="label.course.date.placeholder"/>"
				value="<c:out value="${courseToModify != null ? courseToModify.endDateString : param.course_enddate}" />">
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.description" /></label>
			<textarea class="form-control" rows="5" name="course_description"
				placeholder="<fmt:message key="label.course.description.placeholder"/>"><c:out
					value="${courseToModify != null ? courseToModify.description : param.course_description}" /></textarea>
		</div>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course.certificate_price" /></label> <input class="form-control"
				type="number" name="certificate_price" min="0.00" max="10000.00" step="0.01"
				title="<fmt:message key="message.modify_course.wrong_cert_price"/>"
				placeholder="<fmt:message key="label.course.cert_price.placeholder"/>"
				value="<c:out value="${courseToModify != null ? courseToModify.certificatePrice.toString() : param.certificate_price}" />"
				required>
		</div>
		<!-- file uploading -->
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.photo" /></label> <input class="form-control" type="file" id="avatar"
				accept=".png,.gif,.jpeg,.jpg,.PNG,.GIF,.JPEG,.JPG" name="avatar_course">
		</div>
		<!-- end of file uploading -->


		<div class="card-footer">
			<button class="btn btn-primary icon-btn" name="submit" type="submit">
				<i class="fa fa-fw fa-lg fa-check-circle"></i>
				<fmt:message key="label.save_changes" />
			</button>
			&nbsp;&nbsp;&nbsp;
			<button class="btn btn-default icon-btn" name="reset_data" type="submit" form="reset_data">
				<fmt:message key="label.cancel" />
			</button>
			&nbsp;&nbsp;&nbsp;
		</div>

	</fieldset>
</form>