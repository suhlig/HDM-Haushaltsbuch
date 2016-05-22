<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Gelöscht</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<c:url value="/"/>"/>
<link rel="stylesheet" href="normalize-4.1.1.css">
<link rel="stylesheet" href="main.css">
</head>
<body>
  <%@include file="_menu.jspf"%>
  <h1>Eintrag gelöscht</h1>

  <table style="text-decoration: line-through;">
    <tr>
      <th>id</th>
      <td>${entry.id}</td>
    </tr>
    <tr>
      <th>srcDst</th>
      <td>${entry.srcDst}</td>
    </tr>
    <tr>
      <th>description</th>
      <td>${entry.description}</td>
    </tr>
    <tr>
      <th>value</th>
      <td>${entry.value}€</td>
    </tr>
    <tr>
      <th>category</th>
      <td>${entry.category}</td>
    </tr>
    <tr>
      <th>paymentType</th>
      <td>${entry.paymentType}</td>
    </tr>
  </table>
</body>
</html>
