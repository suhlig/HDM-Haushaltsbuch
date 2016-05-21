<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Fehler</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<c:url value="/"/>"/>
<link rel="stylesheet" href="normalize-4.1.1.css">
<link rel="stylesheet" href="main.css">
</head>

<body>
  <%@include file="_menu.jspf"%>

  <h2>Fehler</h2>

  <div style="color: red;">
    ${error}
  </div>

</body>
</html>
