<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<%-- <%@ include file ="header.jspf" %>  --%>
<%-- <jsp:include page ="header.jspf" /> --%>

<fmt:setLocale value="${userLocale}" scope="session" />
<fmt:setBundle basename="resources.localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<html>
<c:import url="../fragment/head.jsp">
	<c:param name="title" value="Error Page" />
</c:import>

<body>
	<div class="page-error">
		<h1>
			<i class="fa fa-exclamation-circle"></i>Lorem ipsum
		</h1>
		<p>Lorem ipsum.</p>
		<br />
		<p>Message:
<msgtag:display_msg  />
			<a href="${contextPath}">Go back to main page</a>
		</p>
	</div>
</body>

<c:import url="../fragment/footer.jsp" />

</html>



