<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<form id="new" method="POST">
  <table>
    <tr>
      <th>Quelle / Ziel</th>
      <td><input type="text" name="srcDst" value="${entry.srcDst}" /></td>
    </tr>
    <tr>
      <th>Beschreibung</th>
      <td><input type="text" name="description" value="${entry.description}" /></td>
    </tr>
    <tr>
      <th>Wert</th>
      <td><input type="text" name="value" value="${entry.value}" /></td>
    </tr>
    <tr>
      <th>Kategorie</th>
      <td><input type="text" name="category" value="${entry.category}" /></td>
    </tr>
    <tr>
      <th>Zahlungstyp</th>
      <td><input type="text" name="paymentType" value="${entry.paymentType}" /></td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" name="action" value="Hinzufügen" /></td>
    </tr>
  </table>
</form>
