<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<html>
<head>
<title>List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<h1>Haushaltsbuch</h1>

${entries.size()} Einträge gesamt:

<table>
<tr>
	<th>id</th>
	<th>srcDst</th>
	<th>description</th>
	<th>value</th>
	<th>category</th>
	<th>paymentType</th>
</tr>

<c:forEach items="${entries}" var="entry">
  <tr>
    <td>${entry.id}</td>
	<td>${entry.srcDst}</td>
	<td>${entry.description}</td>
	<td>${entry.value}</td>
	<td>${entry.category}</td>
	<td>${entry.paymentType}</td>
  </tr>
</c:forEach>
</body>
</html>
</table>
