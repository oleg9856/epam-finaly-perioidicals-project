<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 29.07.2022
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.gmail.fursovych20.entity.BalanceOperationType" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix="balance_operation_history_page.">
    <fmt:message key="title" var="title"/>
    <fmt:message key="date_header" var="date_header"/>
    <fmt:message key="money" var="money"/>
    <fmt:message key="sum_header" var="sum_header"/>
    <fmt:message key="type_header" var="type_header"/>
    <fmt:message key="refund_operation" var="refund_operation"/>
    <fmt:message key="payment_of_subscriptions_operation" var="payment_of_subscriptions_operation"/>
    <fmt:message key="balance_replenishment_operation" var="balance_replenishment_operation"/>
</fmt:bundle>

<c:set var="counter" value="${1}"/>

<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet"/>
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsf" %>
<div class="container-fluid" style="margin-top: 60px">
    <div class="row">
        <%@include file="user-nav.jsf" %>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
            <div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"
                 class="chartjs-size-monitor">
                <div class="chartjs-size-monitor-expand"
                     style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                    <div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div>
                </div>
                <div class="chartjs-size-monitor-shrink"
                     style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;">
                    <div style="position:absolute;width:200%;height:200%;left:0; top:0"></div>
                </div>
            </div>
            <h2>${title}</h2>
            <div class="table-responsive">
                <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>${date_header}</th>
                        <th>${sum_header}, ${money}</th>
                        <th>${type_header}</th>
                        <th>${status_header}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${balance_operations}" var="operation">
                            <tr>
                                <td>${counter}</td>
                                <c:set var="counter" value="${counter + 1}"/>
                                <td>${operation.localDate}</td>
                                <td>${operation.sum}</td>
                                <c:if test="${operation.type == BalanceOperationType.PAYMENT_OF_SUBSCRIPTION}">
                                    <td>${payment_of_subscriptions_operation}</td>
                                </c:if>
                                <c:if test="${operation.type == BalanceOperationType.BALANCE_REPLENISHMENT}">
                                    <td>${balance_replenishment_operation}</td>
                                </c:if>
                                <c:if test="${operation.type == BalanceOperationType.REFUND}">
                                    <td>${refund_operation}</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>
</body>
</html>