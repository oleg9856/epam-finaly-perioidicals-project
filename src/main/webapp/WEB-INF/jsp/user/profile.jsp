<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 29.07.2022
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix="balance.">
    <fmt:message key="main_title" var="main_title"/>
    <fmt:message key="sum" var="sum"/>
    <fmt:message key="button" var="button"/>
</fmt:bundle>
<fmt:bundle basename="localization.local" prefix="profile.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="sur_name" var="sur_name"/>
    <fmt:message key="balance" var="balance"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="money" var="money"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsf" %>
<div class="container-fluid" style="margin-top: 60px">
    <div class="row">

        <%@include file="user-nav.jsf" %>

        <main role="main" class="col-md-9 col-lg-10 px-4">
            <h2>${title}</h2>
            <strong>${name}: </strong> ${user.name}<br/>
            <strong>${sur_name}: </strong> ${user.surName}<br/>
            <strong>${email}: </strong> ${user.email}<br/>
            <br/>
            ${balance}: ${user.balance} ${money}<br/>

            <div class="modal-body">
                <div style="color: red">${error_adding_funds}</div>
                <form action="${pageContext.request.contextPath}/controller/user/replenish-balance" method="post"
                      name="balance" id="balance">
                    <div class="input-group">
                        <span class="input-group-text">${sum}, ${money}:</span>
                        <input class="form-control" type="number" name="sum" pattern="[1-9]{1,3}.?[0-9]{0,2}" step="any"
                               required/>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <input class="btn btn-primary" id="submit" type="submit" value="${button}"/>
                        </div>
                    </div>
                </form>
            </div>
        </main>
    </div>
</div>
</body>
</html>
