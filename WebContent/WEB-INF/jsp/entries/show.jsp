<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<table id="${entry.id}" class="entry">
  <tr>
    <th>Identifikation</th>
    <td class="id">${entry.id}</td>
  </tr>
  <tr>
    <th>Wertstellung</th>
    <td class="entryDate"><fmt:formatDate pattern="yyyy-MM-dd" value="${entry.entryDate}" /></td>
  </tr>
  <tr>
    <th>Quelle / Ziel</th>
    <td class="srcDst">${entry.srcDst}</td>
  </tr>
  <tr>
    <th>Beschreibung</th>
    <td class="description">${entry.description}</td>
  </tr>
  <tr>
    <th>Wert</th>
    <td class="value">${entry.value}€</td>
  </tr>
  <tr>
    <th>Kategorie</th>
    <td class="category">${entry.category}</td>
  </tr>
  <tr>
    <th>Zahlungstyp</th>
    <td class="paymentType">${entry.paymentType}</td>
  </tr>
  <tr>
    <th></th>
    <td class="actions">
      <%-- http://stackoverflow.com/a/33880971 --%>
      <form method="post" action="entries/delete" class="inline">
        <input type="hidden" name="id" value="${entry.id}" />
        <button type="submit">Delete!</button>
      </form>
    </td>
  </tr>
</table>

