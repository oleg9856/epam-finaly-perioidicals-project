<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 27.07.2022
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>

<fmt:bundle basename="localization.local" prefix="publication_form.">
    <fmt:message key="title_add" var="title"/>
    <fmt:message key="main_header_add" var="main_header"/>
    <fmt:message key="theme_select" var="theme_select"/>
    <fmt:message key="type_select" var="type_select"/>
    <fmt:message key="version_ua" var="version_ua"/>
    <fmt:message key="version_en" var="version_en"/>
    <fmt:message key="name" var="name"/>
    <fmt:message key="description" var="description"/>
    <fmt:message key="price" var="price"/>
    <fmt:message key="picture" var="picture"/>
    <fmt:message key="add_button" var="add_button"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
<head>
    <c:set var="contexPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
</head>
<body>

<%@include file="/WEB-INF/jsp/nav.jsf" %>

<div class="container-fluid" style="margin-top: 60px">
    <div class="row">
        <%@include file="admin_nav.jsf" %>

        <main role="main" class="col-md-9 m-lg-auto col-lg-10 px-4">
            <div class="bg-success text-white text-center">
                <h4>${main_header}</h4>
            </div>

            <form action="${contextPath}/controller/admin/add-publication" method="post" enctype='multipart/form-data'>

                <div class="input-group mb-2">
                    <div class="input-group">
                        <label class="input-group-text" for="theme">${theme_select}:</label>
                    </div>
                    <select class="form-select form-select-sm" aria-label=".form-select-sm example" name="theme" id="theme">
                        <c:forEach items="${themes}" var="theme">
                            <option value="${theme.id}">${theme.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input-roup mb-2">
                    <div class="input-group">
                        <label class="input-group-text" for="type">${type_select}</label>
                    </div>
                    <select class="form-select form-select-sm" aria-label=".form-select-sm example" name="type" id="type">
                        <c:forEach items="${types}" var="type">
                            <option value="${type.id}">${type.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row">
                    <div class="col"
                         style="border-bottom: thin solid black; border-right: thin solid black; border-top: thin solid black;">
                        <div>
                            ${version_ua}
                        </div>
                        <div>
                            <input type="text" class="form-control" placeholder="${name}" name="name_ua" id="name_ua"
                                   required>
                        </div>
                        <div>
                            <input type="text" class="form-control" placeholder="${description}" name="description_ua"
                                   id="description_ua" required>
                        </div>
                    </div>
                    <div class="col" style="border-bottom : thin solid black; border-top: thin solid black;">
                        <div>
                            ${version_en}
                        </div>
                        <div>
                            <input type="text" class="form-control" placeholder="${name}" name="name_en" id="name_en"
                                   required>
                        </div>
                        <div>
                            <input type="text" class="form-control" placeholder="${description}" name="description_en"
                                   id="description_en" required>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-2">
                        <div>
                            ${price}
                            <input type="number" class="form-control" name="price" id="price" value="0" min="0" step="any"
                                   required>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="mb-3">
                        <label for="picture" class="form-label">${picture}</label>
                        <input class="form-control form-control-sm" name="picture" id="picture" type="file" required>
                    </div>
                    <div style="color: red">${publication_fail_message}</div>
                    <button type="submit" class="btn btn-success">${add_button}</button>
                </div>
            </form>
        </main>
    </div>
</div>
</body>
</html>