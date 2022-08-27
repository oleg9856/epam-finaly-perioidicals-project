<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="currentDate" class="java.util.Date" />

<fmt:setLocale value="${sessionScope.locale}"/>	
<fmt:bundle basename="localization.local" prefix = "issues_page.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="date_of_publication" var="date_of_publication"/>
	<fmt:message key="description" var="description"/>
	<fmt:message key="no_issues" var="no_issues"/>
	<fmt:message key="show_button" var="show_button"/>
</fmt:bundle>

<!DOCTYPE html>
<html>
	<head>
		<c:set var="contextPath" value="${pageContext.request.contextPath}" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>${title}</title>
		<link href="<c:url value="/resources/css/bootstrap/bootstrap.min.css" />" rel="stylesheet">
		
	</head>
<body>
	
	<%@include file="/WEB-INF/jsp/nav.jsf" %>

	<div class="container-fluid" style="margin-top: 60px">
      <div class="row">
        
        <%@include file="user-nav.jsf" %>
        
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"><div style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;" class="chartjs-size-monitor"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>

          <h2>${title}</h2>
          <div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th>${date_of_publication}</th>
                  <th>${description}</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${issues}" var="issue">
              		<tr>
              			<td>${issue.localDateOfPublication}</td>
	              		<td>${issue.description}</td>
	              		<td><form method="get" action="${contextPath}/controller/user/show-issue-file">
		                	<input type="hidden" name="issue_id" value="${issue.id}"/>
		                	<button type="submit" class="btn btn-primary">${show_button }</button>
		                </form></td>		               	                
		            </tr>
              	</c:forEach>
              </tbody>
            </table>
            <c:if test="${issues.size() == 0}">
            	<br>
            	<h3>${no_issues }</h3>
            </c:if>
          </div>
        </main>
      </div>
    </div>
    
</body>
</html>