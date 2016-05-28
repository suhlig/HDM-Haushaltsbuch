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
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
  </head>

  <body>
    <%@ include file="navbar.jspf" %>

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
