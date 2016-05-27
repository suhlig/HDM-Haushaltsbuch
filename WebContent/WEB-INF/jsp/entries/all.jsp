<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
          <a href="entries?id=${entry.id}">Details</a>

          <%-- http://stackoverflow.com/a/33880971 --%>
          <form method="post" action="entries/delete" class="inline">
            <%-- Just a pseudo-link to have sensible text in the status bar --%>
            <a href="entries/delete?id=${entry.id}">
              <input type="hidden" name="id" value="${entry.id}"/>
              <button type="submit" class="link-button">Delete</button>
            </a>
          </form>
        </td>
      </tr>
    </c:forEach>
  </tbody>
</table>
