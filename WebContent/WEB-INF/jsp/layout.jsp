<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html lang="de">
	<head>
		<title>Haushaltsbuch - ${title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<c:url value="/"/>"/>
		<link rel="stylesheet" href="normalize-4.1.1.css">
		<link rel="stylesheet" href="main.css">
	</head>

	<body>
		<ul class="navbar">
    <%-- menu idea from http://stackoverflow.com/a/5928182/3212907 --%>
    <c:forEach items="${menu}" var="item">
        <c:set var="isRoot">${'index.jsp' == view && '.' == item.value}</c:set>
        <c:set var="viewJSP">${item.value}.jsp</c:set>
        <c:set var="isActive">${view == viewJSP}</c:set>
        <li>
          <a href="${item.value}" class="${(isRoot || isActive) ? 'active' : 'none'}">
            ${item.key}
          </a>
        </li>
    </c:forEach>
    </ul>
		
    <h1>${title}</h1>
		
		<c:if test="${not empty error}">
		  <p id="error" style="color: red;">
		    ${error}
		  </p>
		</c:if>

		<c:if test="${not empty message}">
		  <p id="message" style="color: green;">
		    ${message}
		  </p>
		</c:if>
		
	  <jsp:include page="${view}"></jsp:include>
	</body>
</html>
