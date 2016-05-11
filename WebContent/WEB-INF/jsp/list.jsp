<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Liste</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body>
  <%@include file="_menu.jspf"%>
  <h1>Haushaltsbuch</h1>
  <h2>Alle ${entries.size()} Einträge</h2>

  <table>
    <thead>
      <tr>
        <th>id</th>
        <th>srcDst</th>
        <th>description</th>
        <th>value</th>
        <th>category</th>
        <th>paymentType</th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="${entries}" var="entry">
        <tr>
          <td><a href="show?${entry.id}">${entry.id}</a></td>
          <td>${entry.srcDst}</td>
          <td>${entry.description}</td>
          <td>${entry.value}€</td>
          <td>${entry.category}</td>
          <td>${entry.paymentType}</td>
          <td>
            <%-- see http://stackoverflow.com/a/33880971 --%>
            <form method="post" action="delete" class="inline">
              <button type="submit" name="id" value="${entry.id}" class="link-button">Delete!</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

</body>
</html>
