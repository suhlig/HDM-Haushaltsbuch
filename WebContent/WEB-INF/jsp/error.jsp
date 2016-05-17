<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Fehler</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://necolas.github.io/normalize.css/4.1.1/normalize.css">
<link rel="stylesheet" href="main.css">
</head>

<body>
  <%@include file="_menu.jspf"%>
  <h1>Haushaltsbuch</h1>
  <h2>Fehler</h2>

  <div style="color: red;">
    ${error}
  </div>

</body>
</html>
