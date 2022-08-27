<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 06.08.2022
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.gmail.fursovych20.entity.Role" %>

<fmt:bundle basename="localization.local" prefix="change_user_roles.">
    <fmt:message key="login" var="login"/>
    <fmt:message key="email" var="email"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="surname" var="surname"/>
    <fmt:message key="money" var="money"/>
    <fmt:message key="role" var="role" />
    <fmt:message key="user_block" var="user_block"/>
    <fmt:message key="user_admin" var="user_admin"/>
    <fmt:message key="user_unblock" var="user_unblock"/>
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
        <%@include file="admin_nav.jsf" %>
        <main role="main" class="col-md-9 m-lg-auto col-lg-10 px-4">
            <div class="bg-success text-white text-center">
                <h4>${all_users}:</h4>
            </div>
            <table class="table table-success table-striped">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">${login}</th>
                    <th scope="col">${email}</th>
                    <th scope="col">${name}</th>
                    <th scope="col">${surname}</th>
                    <th scope="col">${money}</th>
                    <th scope="col" colspan="2">${role}</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user">
                <tr>
                    <td scope="row">${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>${user.name}</td>
                    <td>${user.surName}</td>
                    <td>${user.balance}</td>
                    <c:if test="${user.role == Role.CUSTOMER}">
                        <td>
                            <form method="post" action="${contextPath}/controller/admin/change-user-role?role=BLOCK">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button type="submit" class="btn btn-danger">${user_block}</button>
                            </form>
                        </td>
                        <td>
                            <form method="post" action="${contextPath}/controller/admin/change-user-role?role=ADMIN">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button type="submit" class="btn btn-success">${user_admin}</button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${user.role == Role.BLOCK}">
                        <td colspan="2">
                            <form method="post" action="${contextPath}/controller/admin/change-user-role?role=CUSTOMER">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button type="submit" class="btn btn-primary">${user_unblock}</button>
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${user.role == Role.ADMIN}">
                        <td colspan="2">ADMIN</td>
                    </c:if>
                </tr>
                </c:forEach>
                </tbody>
            </table>

        </main>
    </div>
</div>

</body>
</html>
