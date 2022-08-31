<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix = "register.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="main_header" var="main_header"/>
	<fmt:message key="placeholder_login" var="placeholder_login"/>
	<fmt:message key="placeholder_password" var="placeholder_password"/>
	<fmt:message key="placeholder_name" var="placeholder_name"/>
	<fmt:message key="placeholder_surname" var="placeholder_surname"/>
	<fmt:message key="placeholder_email" var="placeholder_email"/>
	<fmt:message key="button_register" var="button_register"/>
	<fmt:message key="link_login" var="link_login"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
<head>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	<link href="<c:url value="/resources/css/register.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
	<script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>

<%@include file="nav.jsf" %>

<form action="${contextPath}/controller/register" id="login" method="post" style="top:300px">
	<h1>${main_header}</h1>
	<fieldset id="inputs">
		<input id="username" name="login" type="text" placeholder="${placeholder_login}" required autofocus />
		<input  id="password" name="password" type="password" placeholder="${placeholder_password}" required />
		<input  name="name" type="text" placeholder="${placeholder_name}" required />
		<input  name="surname" type="text" placeholder="${placeholder_surname}" required />
		<input  name="email" type="text" placeholder="${placeholder_email}" required />
		<div class="g-recaptcha"
			 data-sitekey="6Ld367khAAAAADEN8Wbv_RIUppssg2xk8SvHWn8r"></div>
	</fieldset>
	<p style="color:red">${fail_message}</p>
	<fieldset id="actions">
		<div style="color:red">${register_fail_message}</div>
		<input type="submit" id="submit" value="${button_register}">
		<a href="${contextPath}/controller/login">${link_login}</a>
	</fieldset>
</form>
</body>
</html>