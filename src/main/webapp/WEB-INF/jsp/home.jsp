<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 26.07.2022
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pag" uri="http://corporation.com/custom-tag/paginator" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix="home.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="search" var="search"/>
    <fmt:message key="search_publication" var="search_publication"/>
    <fmt:message key="main_header" var="main_header"/>
    <fmt:message key="show_button" var="show_button"/>
    <fmt:message key="theme_option" var="theme_option"/>
    <fmt:message key="type_option" var="type_option"/>
    <fmt:message key="sort_option" var="sort_option"/>
    <fmt:message key="index_header" var="index_header"/>
    <fmt:message key="publication_header" var="publication_header"/>
    <fmt:message key="type_header" var="type_header"/>
    <fmt:message key="price_header" var="price_header"/>
    <fmt:message key="theme_option_all" var="theme_option_all"/>
    <fmt:message key="type_option_all" var="type_option_all"/>
    <fmt:message key="sort_option_alphbetically" var="sort_option_alphabetically"/>
    <fmt:message key="sort_option_price" var="sort_option_price"/>
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
<%@include file="nav.jsf" %>


<div class="content">
    <div class="left" style="margin-top: 50px">
        <div class="input-group mb-4">
            <label class="input-group-text">${theme_option}:</label>
            <select class="form-select" id="theme" name="theme" onchange="goToPage()">
                <c:if test="${search_criteria.themeId == 0}">
                    <option value="0" selected>${theme_option_all}</option>
                </c:if>
                <c:if test="${search_criteria.themeId != 0}">
                    <option value="0">${theme_option_all}</option>
                </c:if>
                <c:forEach items="${themes}" var="theme">
                    <c:if test="${search_criteria.themeId == theme.id}">
                        <option value="${theme.id}" selected>${theme.name}</option>
                    </c:if>
                    <c:if test="${search_criteria.themeId != theme.id}">
                        <option value="${theme.id}">${theme.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="input-group mb-4">
            <label class="input-group-text">${type_option}:</label>
            <select class="form-select" id="type" name="type" onchange="goToPage()">
                <c:if test="${search_criteria.typeId == 0}">
                    <option value="0" selected>${type_option_all}</option>
                </c:if>
                <c:if test="${search_criteria.typeId != 0}">
                    <option value="0">${type_option_all}</option>
                </c:if>
                <c:forEach items="${types}" var="type">
                    <c:if test="${search_criteria.typeId == type.id}">
                        <option value="${type.id}" selected>${type.name}</option>
                    </c:if>
                    <c:if test="${search_criteria.typeId != type.id}">
                        <option value="${type.id}">${type.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="input-group mb-4">
            <label class="input-group-text">${sort_option}:</label>
            <select class="form-select" id="order" name="order" onchange="goToPage()">
                <c:if test="${search_criteria.orderId == 1}">
                    <option value="1" selected>${sort_option_alphabetically}</option>
                </c:if>
                <c:if test="${search_criteria.orderId != 1}">
                    <option value="1">${sort_option_alphabetically}</option>
                </c:if>
                <c:if test="${search_criteria.orderId == 2}">
                    <option value="2" selected>${sort_option_price}</option>
                    >
                </c:if>
                <c:if test="${search_criteria.orderId != 2}">
                    <option value="2">${sort_option_price}</option>
                </c:if>
            </select>
        </div>
        <div class="input-group mb-4">
            <form action="${contextPath}/controller/search-publication" method="get">
                <div style="color: red">${publication_fail_message}</div>
                <div class="input-group mb-4">
                    <button class="btn btn-warning" type="submit" id="button" >${search}</button>
                    <input type="text" class="form-control" placeholder="${search_publication}" aria-label="Search"
                           aria-describedby="button" name="publication_name" id="publication_name">
                </div>
            </form>
        </div>
    </div>
    <div class="right">
        <h2>${main_header}</h2>
        <ul class="list-group">
            <li class="list-group-item">
                <div class="row">
                    <div class=col-md-1>
                        ${index_header}
                    </div>
                    <div class=col-md-4>
                        ${publication_header}
                    </div>
                    <div class=col-md-2>
                        ${type_header}
                    </div>
                    <div class=col-md-1>
                        ${price_header}
                    </div>
                </div>
            </li>
            <c:forEach items="${publications}" var="publication">
                <a href="${contextPath}/controller/publication?id=${publication.id}">
                    <li class="list-group-item">
                        <div class="row">
                            <div class=col-md-1>
                                    ${publication.id}
                            </div>
                            <div class=col-md-4>
                                    ${publication.name}
                            </div>
                            <div class=col-md-2>
                                    ${typeNames.get(publication.typeId)}
                            </div>
                            <div class=col-md-1>
                                    ${publication.price}
                            </div>
                        </div>
                    </li>
                </a>
            </c:forEach>
        </ul>

        <div class="row">
            <nav aria-label="...">
                <ul class="pagination pagination-sm">
                    <li class="page-item"><a class="page-link"
                                             href="${contextPath}/controller/home?theme=${search_criteria.themeId}&type=${search_criteria.typeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=3">3</a>
                    </li>
                    <li class="page-item"><a class="page-link"
                                             href="${contextPath}/controller/home?theme=${search_criteria.themeId}&type=${search_criteria.typeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=5">5</a>
                    </li>
                    <li class="page-item"><a class="page-link"
                                             href="${contextPath}/controller/home?theme=${search_criteria.themeId}&type=${search_criteria.typeId}&order=${search_criteria.orderId}&currentPage=1&itemsPerPage=30">30</a>
                    </li>
                </ul>
            </nav>
            <nav aria-label="...">
                <ul class="pagination justify-content-center">
                    <pag:display currentPage="${search_criteria.currentPage}"
                                 totalPageCount="${search_criteria.pageCount}" viewPageCount="3"
                                 urlPattern="${contextPath}/controller/home?theme=${search_criteria.themeId}&type=${search_criteria.typeId}&order=${search_criteria.orderId}&itemsPerPage=${search_criteria.itemsPerPage}&"/>

                </ul>
            </nav>
        </div>
    </div>
</div>

<script type="text/javascript">
    function goToPage() {
        var theme = document.getElementById("theme").value;
        var type = document.getElementById("type").value;
        var order = document.getElementById("order").value;
        document.location.href = '${contextPath}/controller/home?theme=' + theme + '&type=' + type + '&order=' + order + '&itemsPerPage=' + '${search_criteria.itemsPerPage}' + '&currentPage=1';
    }
</script>
</body>
</html>
