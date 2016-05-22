<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>Anzeigen</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<c:url value="/"/>"/>
<link rel="stylesheet" href="normalize-4.1.1.css">
<link rel="stylesheet" href="main.css">
</head>

<body>
  <%@include file="_menu.jspf"%>

  <h2>Eintrag</h2>

  <table class="entry">
    <tr>
      <th>Identifikation</th>
      <td class="id">${entry.id}</td>
    </tr>
    <tr>
       <th>Wertstellung</th>
      <td><fmt:formatDate pattern="yyyy-MM-dd" value="${entry.entryDate}" /></td>
    </tr>
    <tr>
       <th>Quelle / Ziel</th>
      <td>${entry.srcDst}</td>
    </tr>
    <tr>
       <th>Beschreibung</th>
      <td>${entry.description}</td>
    </tr>
    <tr>
       <th>Wert</th>
      <td>${entry.value}€</td>
    </tr>
    <tr>
       <th>Kategorie</th>
      <td>${entry.category}</td>
    </tr>
    <tr>
       <th>Zahlungstyp</th>
      <td>${entry.paymentType}</td>
    </tr>
  </table>
</body>
</html>
