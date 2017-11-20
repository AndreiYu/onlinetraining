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

<c:if test="${courseTypes != null && !courseTypes.isEmpty() && selected_item != null && !selected_item.isEmpty()}">
	<c:forEach var="course_type" items="${courseTypes}">
		<c:if test="${course_type.id == selected_item}">
			<c:set var="courseType_to_modify" value="${course_type}" scope="request" />
		</c:if>
	</c:forEach>
</c:if>


<form action="${contextPath}/controller" method="POST" id="modify_course_type">
	<input type="hidden" name="command" value="modify_course_type" />
</form>

<form action="${contextPath}/controller" method="POST" id="add_course">
	<input type="hidden" name="command" value="add_course" />
</form>

<!--           	error messages printing -->
<msgtag:display_msg />
<!--           	end of tag -->

<form class="form-horizontal" action="${contextPath}/controller" method="POST">
	<input type="hidden" name="command" value="${param.command}" /> 
	<input type="hidden" name="selected_item" value="${courseType_to_modify.id}" />
	<!--  add_courseType or modify_courseType -->
	<fieldset>

		<legend>
			<fmt:message key="label.course_type.find_to_modify" />
		</legend>


		<!--           	modify existing -->
		<div class="form-group">
			<select class="form-control" id="select_existing_courseType" name="selected_item" form="modify_course_type">
				<option label=""></option>
				<c:forEach var="course_type" items="${courseTypes}">
					<option value="${course_type.id}" ${course_type.id eq courseType_to_modify.id ? 'selected' : ''}>${course_type.id}&nbsp;${course_type.category}</option>
				</c:forEach>
			</select> <br>
			<button class="btn btn-default btn-xs" name="choose_to_modify" value="choose_to_modify" type="submit" form="modify_course_type">
				<!-- onclick="frmsubmit('modify_course');" -->
				<fmt:message key="label.modify_existing" />
			</button>

		</div>
		<!--     end      	modify existing -->


		<!--     add new courseType			    -->
		<legend>
			<fmt:message key="label.course.or_add_new" />
		</legend>
		<div class="form-group">
			<label class="control-label"><fmt:message key="label.course_type.category" /></label> <input class="form-control" type="text"
				name="course_type_category" pattern="(^[а-яА-ЯёЁa-zA-Z0-9].*){1,250}"
				title="<fmt:message key="message.modify_course.wrong_title"/>"
				placeholder="<fmt:message key="label.course_type.category.placeholder"/>"
				value="<c:out value="${courseType_to_modify not empty ? courseType_to_modify.category : param.course_type_category}"/>" required>
		</div>

		<div class="card-footer">
			<button class="btn btn-primary icon-btn" name="submit" type="submit">
				<i class="fa fa-fw fa-lg fa-check-circle"></i>
				<fmt:message key="label.save_changes" />
			</button>
			&nbsp;&nbsp;&nbsp;
			<button class="btn btn-default icon-btn" name="reset" type="reset">
				<fmt:message key="label.cancel" />
			</button>
			&nbsp;&nbsp;&nbsp;
			<button class="btn btn-default icon-btn" type="submit" form="add_course">
				<i class="fa fa-fw fa-lg fa-reply"></i>
				<fmt:message key="label.back_modify_course" />
			</button>
		</div>

	</fieldset>
</form>