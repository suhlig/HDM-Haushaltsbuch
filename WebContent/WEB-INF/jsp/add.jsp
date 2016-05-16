<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Hinzufügen</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://necolas.github.io/normalize.css/4.1.1/normalize.css">
<link rel="stylesheet" href="main.css">
</head>
<body>
  <%@include file="_menu.jspf"%>
  <h1>Haushaltsbuch</h1>
  <h2>Neuen Eintrag hinzufügen</h2>

  ${message}

  <form method="POST" action="insert">
    <table>
      <tr>
        <th>srcDst</th>
        <td><input type="text" name="srcDst" value="${entry.srcDst}" /></td>
      </tr>
      <tr>
        <th>description</th>
        <td><input type="text" name="description" value="${entry.description}" /></td>
      </tr>
      <tr>
        <th>value</th>
        <td><input type="text" name="value" value="${entry.value}" /></td>
      </tr>
      <tr>
        <th>category</th>
        <td><input type="text" name="category" value="${entry.category}" /></td>
      </tr>
      <tr>
        <th>paymentType</th>
        <td><input type="text" name="paymentType" value="${entry.paymentType}" /></td>
      </tr>
      <tr>
        <th></th>
        <td><input type="submit" name="action" value="Hinzufügen" /></td>
      </tr>
    </table>
  </form>
</body>
</html>
