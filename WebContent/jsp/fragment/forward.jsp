<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>


<c:choose>
	<c:when test="${user.role == 'ADMIN'}">
		<jsp:forward page="admin/blank.jsp" />
	</c:when>
	<c:when test="${user.role == 'STUDENT'}">
		<jsp:forward page="student/blank.jsp" />
	</c:when>
	<c:when test="${user.role == 'LECTURER'}">
		<jsp:forward page="lecturer/blank.jsp" />
	</c:when>
	<c:otherwise>
		<!-- 			nothing 		-->
	</c:otherwise>
</c:choose>