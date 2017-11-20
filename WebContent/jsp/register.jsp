<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/custom.tld" prefix="msgtag"%>

<fmt:setLocale value="${userLocale}" />
<fmt:setBundle basename="resources/localization.pagecontent" />

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="title"><fmt:message key="label.sign_up"/></c:set>
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
			<div class="incr">
				<form class="login-form" action="${contextPath}/controller" method="POST" enctype="multipart/form-data">
					<input type="hidden" name="command" value="sign_up" />
					<h3 class="login-head">
						<i class="fa fa-lg fa-fw fa-user-plus"></i>
						<fmt:message key="label.login.sign_up_message" />
					</h3>
					<div class="form-group">
						<!-- error message printing 			-->
						<msgtag:display_msg />
						<!--    	end	tag						-->
						<label class="control-label"><fmt:message key="label.registration.first_name" /></label> <input class="form-control"
							type="text" name="first_name" pattern="[а-яА-ЯёЁa-zA-Z]{2,40}" title="<fmt:message key="label.login.username_wrong_name"/>"
							placeholder="<fmt:message key="label.registration.first_name.placeholder"/>" value="<c:out value="${param.first_name}" />"
							required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.last_name" /></label> <input class="form-control"
							type="text" name="last_name" pattern="[а-яА-ЯёЁa-zA-Z]{2,40}" title="<fmt:message key="label.login.username_wrong_name"/>"
							placeholder="<fmt:message key="label.registration.last_name.placeholder"/>" value="<c:out value="${param.last_name}" />"
							required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.email" /></label> <input class="form-control"
							type="email" name="email" pattern="[\w\.-_]+@\w+\.[\.\w]+{2,40}"
							title="<fmt:message key="label.login.username_wrong_email"/>"
							placeholder="<fmt:message key="label.registration.email.placeholder"/>" value="<c:out value="${param.email}" />" required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.login" /></label> <input class="form-control" type="text"
							name="login" pattern="[_A-Za-z0-9-]{3,20}" title="<fmt:message key="label.login.username_wrong_regex"/>"
							placeholder="<fmt:message key="label.registration.login.placeholder"/>" value="<c:out value="${param.login}" />" required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.password" /></label> <input class="form-control"
							type="password" name="password" id="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
							title="<fmt:message key="label.login.password_wrong_regex"/>"
							placeholder="<fmt:message key="label.registration.password.placeholder"/>" required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.password" /></label> <input class="form-control"
							type="password" name="confirm_password" id="confirm_password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,20}"
							title="<fmt:message key="label.login.password_wrong_regex"/>"
							placeholder="<fmt:message key="label.registration.password_repeat.placeholder"/>" required>
					</div>
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.registration.role" /></label>
						<div class="radio">
							<label> <input type="radio" name="role" value="student" ${param.role == 'student' ? 'checked' : ''} required>
								<fmt:message key="label.registration.role.student" />
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="role" value="lecturer" ${param.role == 'lecturer' ? 'checked' : ''} required>
								<fmt:message key="label.registration.role.lecturer" />
							</label>
						</div>
					</div>
					<!-- Avatar uploading -->
					<div class="form-group">
						<label class="control-label"><fmt:message key="label.photo" /></label> <input class="form-control" type="file"
							accept=".png,.gif,.jpeg,.jpg,.PNG,.GIF,.JPEG,.JPG" name="avatar_user" id="avatar" />
						<!-- End of avatar uploading -->
					</div>
					<div class="form-group">
						<div class="checkbox">
							<label> <input type="checkbox" name="accep_rules" required> <fmt:message
									key="label.registration.accept_terms" />
							</label>
						</div>
					</div>
					<div class="form-group btn-container">
						<button class="btn btn-primary btn-block" name="submit" type="submit">
							<fmt:message key="label.login.signup_button" />
							<i class="fa fa-unlock fa-lg"></i>
						</button>
					</div>
					<div class="form-group mt-20">
						<p class="semibold-text mb-0">
							<a href="${contextPath}/jsp/login.jsp"><i class="fa fa-angle-left fa-fw"></i> <fmt:message
									key="label.login.back_button" /></a>
						</p>
					</div>
				</form>
			</div>
		</div>
	</section>
</body>

<c:import url="fragment/footer.jsp" />
