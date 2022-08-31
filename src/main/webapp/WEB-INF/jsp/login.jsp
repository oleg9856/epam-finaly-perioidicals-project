<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 13.07.2022
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:bundle basename="localization.local" prefix="login.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="button_login" var="button_login"/>
    <fmt:message key="main_header" var="main_header"/>
    <fmt:message key="msg_login_failed" var="msg_login_failed"/>
    <fmt:message key="link_register" var="link_register"/>
    <fmt:message key="placeholder_login" var="placeholder_login"/>
    <fmt:message key="placeholder_password" var="placeholder_password"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet" />
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>

<%@include file="nav.jsf" %>

<form action="${contextPath}/controller/login" id="login" method="post" onsubmit="">
    <h1>${main_header}</h1>
    <fieldset id="inputs">
        <input id="username" name="login_or_email" type="text" placeholder="${placeholder_login}" required autofocus />
        <input  id="password" name="password" type="password" placeholder="${placeholder_password}" />
        <div class="g-recaptcha"
             data-sitekey="6Ld367khAAAAADEN8Wbv_RIUppssg2xk8SvHWn8r"></div>
    </fieldset>
    <div style="color:red">${login_fail_message}</div>
    <fieldset id="actions">
        <input type="submit" id="submit" value="${button_login}">
        <a href="register">${link_register}</a>
    </fieldset>
</form>
</body>
</html>