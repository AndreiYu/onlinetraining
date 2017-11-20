<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>


<%-- <%@ include file ="header.jspf" %>  --%>
<%-- <jsp:include page ="header.jspf" /> --%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />  

<c:set var="currentPage" value="${pageContext.request.requestURI}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>  

<html>
		    <c:import url="../fragment/head.jsp">
				<c:param name="title" value="Error Page"/>
			</c:import>

  <body>
    <div class="page-error">
      <h1><i class="fa fa-exclamation-circle"></i> Error Occured</h1>
      <p></p>
		<strong>Request from </strong> ${pageContext.errorData.requestURI} is failed
		<br/>
		<strong>Servlet name or type:</strong> ${pageContext.errorData.servletName}
		<br/>
		<strong>Status code: </strong>${pageContext.errorData.statusCode}
		<br/>
		<strong>Exception: </strong>${pageContext.errorData.throwable}
		<br/>
		<strong>Additional Message: </strong>
						<!-- error message printing 			-->
							<msgtag:display_msg/>
						<!--    	end	tag						-->
				${pageContext.exception.message}
		<br/>
		<br/><br/>
      <p><a href="javascript:window.history.back();">Go back to previous page</a></p>
    </div>
  </body>

<c:import url="../fragment/footer.jsp"/>
  
</html>

