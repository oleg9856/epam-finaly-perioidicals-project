<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 26.07.2022
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="ldf"%>
<%@page import="java.time.LocalDate" %>
<%@ page import ="com.gmail.fursovych20.entity.Role" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:bundle basename="localization/local" prefix="publication_page.">
    <fmt:message key="publication" var="publication_header"/>
    <fmt:message key="theme" var="theme_header"/>
    <fmt:message key="type" var="type_header"/>
    <fmt:message key="price" var="price_header"/>
    <fmt:message key="login_message" var="login_message"/>
    <fmt:message key="login_button" var="login_button"/>
    <fmt:message key="register_button" var="register_button"/>
    <fmt:message key="money_per_month" var="money_per_month"/>
    <fmt:message key="add_review_header" var="add_review_header"/>
    <fmt:message key="make_subscription_header" var="make_subscription_header"/>
    <fmt:message key="reviews" var="reviews_header"/>
    <fmt:message key="review_mark" var="review_mark"/>
    <fmt:message key="review_text" var="review_text"/>
    <fmt:message key="add_review_button" var="add_review_button"/>
    <fmt:message key="update_button" var="update_button"/>
    <fmt:message key="delete_button" var="delete_button"/>
    <fmt:message key="add_issue_header" var="add_issue_header"/>
    <fmt:message key="add_issue_button" var="add_issue_button"/>
    <fmt:message key="issue_description" var="issue_description"/>
    <fmt:message key="subscription_price" var="subscription_price"/>
    <fmt:message key="subscription_start_month" var="subscription_start_month"/>
    <fmt:message key="subscription_duration" var="subscription_duration"/>
    <fmt:message key="create_subscription_button" var="create_subscription_button"/>
    <fmt:message key="issue_date" var="issue_date"/>
    <fmt:message key="block_message" var="block_message"/>
</fmt:bundle>
<!DOCTYPE html>
<html>
<head>
    <c:set var="contextPage" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${publication.name}</title>
    <link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/publication_page.css" />" rel="stylesheet">
</head>
<body>

<%@include file="nav.jsf" %>

<div class="content">
    <div class="center">
        <div id="publication">
            <img src="<c:url value="/resources/img/${publication.picturePath}" />" id="picture" alt=""/>
            <div id="description">
                <p><strong>${publication_header}:<br/> </strong>${publication.name}, ${publication.id}</p>
                <p>${theme_header}: <strong>${theme.name}</strong></p>
                <p>${type_header}: <strong>${type.name}</strong></p>
                <p>${publication.description} </p>
                <p><strong>${price_header}: </strong>${publication.price} ${money_per_month}</p>
            </div>
        </div>
        <div id="subscription">
            <c:if test="${userRole == null}">
                <hr/>
                <div style="margin: 0 auto; width: 500px">
                    <strong><a href="${contextPath}/controller/login">${login_button}</a></strong>
                </div>
            </c:if>
            <c:if test="${userRole == Role.BLOCK}">
                <hr/>
                <div style="margin: 0 auto; width: 500px">
                    <strong>${block_message}</strong>
                </div>
            </c:if>
            <c:if test="${userRole == Role.CUSTOMER}">
                <hr/>
                <h2>${make_subscription_header}: </h2><br/>
                <form method="get" action="${pageContext.request.contextPath}/controller/user/subscribe">
                    <input type="hidden" name="publication_id" value="${publication.id}"/>
                    <input type="hidden" name="start_date" id="start_date" value="${LocalDate.now()}"/>
                    <label class="input-group-text" for="duration">${subscription_duration}: </label>
                    <div class="input-group mb-3">
                        <select class="form-select form-select-lg mb-3"
                                aria-label=".form-select-lg example"
                                name="duration" id="duration" onchange="countPrice()">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                    </div>
                    <div style="color: red">${param.subscription_fail_message}</div>
                    <div>${subscription_price}: <span id="total_price"></span></div>
                    <button type="submit" class="btn btn-primary">${create_subscription_button}</button>
                </form>
            </c:if>
        </div>
        <c:if test="${userRole == Role.ADMIN}">
            <div>
                <hr>
                <h3>${add_issue_header}:</h3>
                <form action="${contextPath}/controller/admin/upload-issue" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="publication_id" value="${publication.id }"/>
                    <div class="input-group">
                        <span class="input-group-text">${issue_description}:</span>
                        <textarea class="form-control" aria-label="With textarea" name="description" required></textarea>
                    </div>
                    <div class="mb-3">
                        <input class="form-control" type="file" required="required" name="issue_file" id="issue_file">
                    </div>
                    <div style="color: red">${issue_fail_message}</div>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px">${add_issue_button }</button>
                </form>
                <hr/>
                <form action="${contextPath}/controller/admin/delete-publication" method="post">
                    <input type="hidden" name="publication_id" value="${publication.id }"/>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px">${delete_button}</button>
                </form>
                <hr/>
                <form action="${contextPath}/controller/admin/edit-publication" method="get">
                    <input type="hidden" name="publication_id" value="${publication.id }"/>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px">${update_button}</button>
                </form>

            </div>
        </c:if>
        <div id="reviews">
            <c:if test="${userRole == Role.CUSTOMER}">
                <hr>
                <h3>${add_review_header}:</h3>
                <form action="${contextPath}/controller/user/add-review" method="post">
                    <input type="hidden" name="id_publication" value="${publication.id}"/>
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="mark">${review_mark}: </label>
                        <select class="form-select" id="mark" name="mark">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div class="input-group-lg">
                        <span class="input-group-text">${review_text}:</span>
                        <textarea class="form-control" aria-label="With textarea" name="text" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px">${add_review_button}</button>
                </form>
            </c:if>
            <hr>
            <c:if test="${reviews.size() != 0}">
                <h2>${reviews_header}:</h2>
            </c:if>
            <c:forEach items="${reviews}" var="review">
                <c:if test="${userRole != Role.ADMIN}">
                    <strong><ldf:localDate date="${review.dateOfPublication}" pattern="yyyy-MM-dd"/></strong><br>
                    ${review_mark}: ${review.mark}<br>
                    ${review.text}<br>
                    <hr>
                </c:if>
                <c:if test="${userRole == Role.ADMIN}">
                    <strong><ldf:localDate date="${review.dateOfPublication}" pattern="yyyy-MM-dd"/></strong>
                    <form method="post">
                        <input type="hidden" name="review_id" value="${review.id}" />
                        <div class="input-group mb-2" >
                            <div class="input-group-sm">
                                <label class="input-group-text" for="mark">${review_mark}: </label>
                            </div>
                            <label>
                                <select class="form-select-sm" name="mark">
                                    <option value="0">0</option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="${review.mark}" selected>${review.mark}</option>
                                </select>
                            </label>
                        </div>
                        <div class="input-group">
                            <label class="input-group-text" for="text">${review_text}: </label>
                            <textarea class="form-control" aria-label="With textarea" id="text" name="text">${review.text}</textarea>
                        </div>
                        <div style="color: red">${publication_fail_message}</div>
                        <button type="submit" formaction="${contextPath}/controller/admin/update-review" class="btn btn-primary" style="margin-top: 10px">${update_button }</button>
                        <button type="submit" formaction="${contextPath}/controller/admin/delete-review" class="btn btn-danger" style="margin-top: 10px">${delete_button }</button>
                    </form>
                    <hr>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>

<script type="text/javascript">
    function countPrice() {
        var price = ${publication.price};
        var duration = document.getElementById("duration").value;
        document.getElementById("total_price").innerText = price * duration;
    }
</script>
</body>
</html>
