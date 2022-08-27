<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 30.07.2022
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.gmail.fursovych20.entity.SubscriptionStatus" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization.local" prefix="subscriptions_page.">
    <fmt:message key="title_subscription_history" var="title"/>
    <fmt:message key="publication_name" var="publication_name"/>
    <fmt:message key="start_date_header" var="start_date_header"/>
    <fmt:message key="end_date_header" var="end_date_header"/>
    <fmt:message key="price_header" var="price_header"/>
    <fmt:message key="status_header" var="status_header"/>
    <fmt:message key="status_terminated" var="status_terminated"/>
    <fmt:message key="status_expired" var="status_expired"/>
    <fmt:message key="show_issues" var="show_issues"/>
    <fmt:message key="no_subscriptions" var="no_subscription"/>
    <fmt:message key="money" var="money"/>
</fmt:bundle>

<c:set var="counter" value="${1}"/>

<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
</head>
<body>
<%@include file="/WEB-INF/jsp/nav.jsf"%>
<div class="container-fluid" style="margin-top: 60px">
    <div class="row">
        <%@include file="user-nav.jsf"%>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;" class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>

            <h2>${title}</h2>
            <div class="table-responsive">
               <table class="table table-striped table-sm">
                   <thead>
                   <tr>
                       <th>ID</th>
                       <th>${publication_name}</th>
                       <th>${start_date_header}</th>
                       <th>${end_date_header}</th>
                       <th>${price_header}, ${money}</th>
                       <th>${status_header}</th>
                       <th></th>
                   </tr>
                   </thead>
                   <tbody>
                   <c:forEach items="${subscriptions}" var="subscription">
                       <tr>
                           <td>${counter}</td>
                           <c:set var="counter" value="${counter + 1}"/>
                           <td>${publicationNames.get(subscription.publicationId)}</td>
                           <td>${subscription.startLocalDate}</td>
                           <td>${subscription.endLocalDate}</td>
                           <td>${subscription.price}</td>
                           <c:if test="${subscription.status == SubscriptionStatus.EXPIRED}">
                               <td>${status_expired}</td>
                           </c:if>
                           <c:if test="${subscription.status == SubscriptionStatus.TERMINATED}">
                               <td>${status_terminated}</td>
                           </c:if>
                           <td><form method="post" action="${contextPath}/controller/user/show-issues">
                               <input type="hidden" name="subscription_id" value="${subscription.id}"/>
                               <button type="submit" class="btn btn-primary">${show_issues}</button>
                           </form></td>
                       </tr>
                   </c:forEach>
                   </tbody>
               </table>
                <c:if test="${subscription.size() == 0}">
                    <br/>
                    <h3>${no_subscription}</h3>
                </c:if>
            </div>
        </main>
    </div>
</div>

</body>
</html>
