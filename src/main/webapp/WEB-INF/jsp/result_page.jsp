<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 29.07.2022
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix = "result_page.">
    <fmt:message key="back_button" var="back_button"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/> " rel="stylesheet">
</head>
<%@include file="nav.jsf" %>
<body>

<div class="container-fluid" style="margin-top: 60px">
    <div class="row">

        <main role="main" class="col-md-9 m-lg-auto col-lg-10 px-4">
            <div>
                <h2>${message}</h2>
            </div>
            <hr/>
            <div class="row">
                <div class="btn-group col-2">
                    <a href="${return_page}">
                        <button type="button" class="btn btn-success">${back_button}</button>
                    </a>
                </div>
            </div>
            <hr/>
            <div class="row">
                <div class="btn-group col-2">
                    <a href="home.jsp">
                        <button type="button" class="btn btn-success">HOME</button>
                    </a>
                </div>
            </div>
        </main>
    </div>
</div>

</body>
</html>
