<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="title"><fmt:message key="label.login"/></c:set>
<c:import url="fragment/head.jsp"/>

<c:import url="fragment/forward.jsp" />

</head>
<body>
	<section class="material-half-bg">
		<div class="cover"></div>
	</section>
	<section class="login-content">
		<div class="logo">
			<h1>
				<fmt:message key="label.login.header" />
			</h1>
		</div>

		<div class="login-box">
			<form class="login-form" action="${contextPath}/controller" method="POST">
				<input type="hidden" name="command" value="login" />
				<h3 class="login-head">
					<i class="fa fa-lg fa-fw fa-user"></i>
					<fmt:message key="label.login.login_message" />
				</h3>
				<div class="form-group">
					<!-- error message printing -->
					<msgtag:display_msg />
					<!--    	end	tag			 -->
					<label class="control-label"><fmt:message key="label.login.username" /></label> <input class="form-control" type="text"
						name="login" placeholder="<fmt:message key="label.login.login_placeholder"/>" required autofocus>
				</div>
				<div class="form-group">
					<label class="control-label"><fmt:message key="label.login.password" /></label> <input class="form-control" type="password"
						name="password" placeholder="<fmt:message key="label.login.password_placeholder"/>" required>
				</div>
				<div class="card">
					<div class="utility">
						<div class="animated-radio-button">
							<label> <input type="radio" name="userLocale" value="ru_RU"
								onclick="location.href='${contextPath}?userLocale=ru_RU'" ${userLocale == 'ru_RU' ? 'checked' : ''} /><span
								class="label-text">Русский</span>
							</label> <label> <input type="radio" name="userLocale" value="en_US"
								onclick="location.href='${contextPath}?userLocale=en_US'" ${userLocale == 'en_US' ? 'checked' : ''} /><span
								class="label-text">English</span>
							</label>
						</div>
						<p class="semibold-text mb-0">
							<a href="${contextPath}/jsp/register.jsp"><fmt:message key="label.login.sign_up_message" /></a>
						</p>
					</div>
				</div>
				<div class="form-group btn-container">
					<button class="btn btn-primary btn-block" name="submit" type="submit">
						<fmt:message key="label.login.login_button" />
						<i class="fa fa-sign-in fa-lg"></i>
					</button>
				</div>
			</form>
		</div>

	</section>
</body>

<c:import url="fragment/footer.jsp" />

