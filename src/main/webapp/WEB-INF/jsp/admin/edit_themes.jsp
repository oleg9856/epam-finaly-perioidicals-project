<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import ="com.gmail.fursovych20.entity.LocaleType" %>

<fmt:setLocale value="${sessionScope.locale}"/>	

<fmt:bundle basename="localization.local" prefix = "edit_themes.">
	<fmt:message key="title" var="title"/>
	<fmt:message key="add_theme_header" var="add_theme_header"/>
	<fmt:message key="current_themes_header" var="current_themes_header"/>
	<fmt:message key="add_theme_button" var="add_theme_button"/>
	<fmt:message key="version_ua" var="version_ua"/>
	<fmt:message key="version_en" var="version_en"/>
	<fmt:message key="update_theme_button" var="update_theme_button"/>
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
      
        <%@include file="admin_nav.jsf" %>

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">

		<div class="bg-success text-white text-center" >
			<h4>${add_theme_header }:</h4>
		</div>

		<form action="${contextPath}/controller/admin/add-theme" method="post">			 	
			<div class="row">
					<div class="form-check col-5">
						<input type="text" class="form-control" placeholder="${version_ua}" name="name_ua" id="name_id_ua">
					</div>
					<div class="form-check col-5">
						<input type="text" class="form-control" placeholder="${version_en}" name="name_en" id="name_id_en">
					</div>
					<div class="form-check col-2">
						<button type="submit" class="btn btn-success">${add_theme_button }</button>
					</div>
			</div>	
			<div style="color:red">${theme_fail_message}</div>			
		</form>	
		<div class="bg-success text-white text-center" >
			<h4>${current_themes_header }:</h4>
		</div>
		<c:forEach items="${themes}" var="theme">
			<form action="${contextPath}/controller/admin/update-theme" method="post">		 	
				<div class="row">
						<div class="form-check col-1">
							<input type="text" class="form-control" name="theme" value="${theme.id}" readonly="readonly"/>
						</div>
						<div class="form-check col-5">
							<input type="text" class="form-control" placeholder="${version_ua}" name="name_ua" id="name_ua" value="${theme.getLocalizedNames().get(LocaleType.uk_UA)}">
						</div>
						<div class="form-check col-5">
							<input type="text" class="form-control" placeholder="${version_en}" name="name_en" id="name_en" value="${theme.getLocalizedNames().get(LocaleType.en_US)}">
						</div>
						<div class="form-check col-1">
							<button type="submit" class="btn btn-success">${update_theme_button}</button>
						</div>
				</div>				
			</form>
		</c:forEach>	
        </main>
      </div>
    </div>
    
</body>
</html>