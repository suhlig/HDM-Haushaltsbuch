<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<title>Liste</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<c:url value="/"/>"/>
<link rel="stylesheet" href="normalize-4.1.1.css">
<link rel="stylesheet" href="main.css">
</head>

<body>
  <%@include file="_menu.jspf"%>
  
  <h2>Alle Einträge</h2>

  <p>Es gibt insgesamt ${entries.size()} Einträge:</p>

  <table>
    <thead>
      <tr>
        <th>Wertstellung</th>
        <th>Quelle / Ziel</th>
        <th>Beschreibung</th>
        <th>Wert</th>
        <th>Kategorie</th>
        <th>Zahlungstyp</th>
        <th>Aktion</th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="${entries}" var="entry">
        <tr>
          <td><fmt:formatDate pattern="yyyy-MM-dd" value="${entry.entryDate}" /></td>
          <td>${entry.srcDst}</td>
          <td>${entry.description}</td>
          <td>${entry.value}€</td>
          <td>${entry.category}</td>
          <td>${entry.paymentType}</td>
          <td>
            <a href="show?id=${entry.id}">Details</a>

            <%-- http://stackoverflow.com/a/33880971 --%>
            <form method="post" action="delete" class="inline">
              <%-- Just a pseudo-link to have sensible text in the status bar --%>
              <a href="delete?id=${entry.id}">
                <input type="hidden" name="id" value="${entry.id}"/>
                <button type="submit" class="link-button">Delete</button>
              </a>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>
