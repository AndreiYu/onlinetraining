<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="currentPage" value="${pageContext.request.requestURI}" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%--             <c:choose>

                <c:when test="${not empty user}">
                    <c:set var="user_name" value="${user.first_name} ${user.first_name}"/>
                    <c:set var="user_role" value="${user.role}"/>
                </c:when>

                <c:otherwise>
                    <c:set var="user_name" value="Guest"/>
                    <c:set var="user_role" value="Guest"/>
                </c:otherwise>
            </c:choose> --%>


<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Using this tag will send the full URL of referring page only for requests within website's domain. -->
<meta name="referrer" content="same-origin">
<!-- CSS-->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/main.css">

<title><c:out value="${title}" /></title>