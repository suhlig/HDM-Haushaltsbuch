<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
  <tr>
     <th></th>
    <td>
      <%-- http://stackoverflow.com/a/33880971 --%>
      <form method="post" action="delete" class="inline">
        <input type="hidden" name="id" value="${entry.id}"/>
        <button type="submit" >Delete!</button>
      </form>
    </td>
  </tr>
</table>
