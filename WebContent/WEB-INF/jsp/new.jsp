<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<form id="new" method="POST">
  <table>
    <tr>
      <th>Quelle / Ziel</th>
      <td>
        <input type="text"
               name="srcDst"
               value="${entry.srcDst}"
               title="Quelle oder Ziel der Buchung"
               required
        />
      </td>
    </tr>
    <tr>
      <th>Beschreibung</th>
      <td>
        <input type="text"
               name="description"
               value="${entry.description}"
               title="Beschreibung der Buchung"
               required
        />
       </td>
    </tr>
    <tr>
      <th>Wert</th>
      <td>
        <input type="number"
               name="value"
               value="${entry.value}"
               title="Betrag in Euro"
               required
        /> €
      </td>
    </tr>
    <tr>
      <th>Kategorie</th>
      <td>
        <input type="text"
               name="category"
               value="${entry.category}"
               title="Kategorie der Buchung"
        />
      </td>
    </tr>
    <tr>
      <th>Zahlungstyp</th>
      <td>
        <input type="text"
               name="paymentType"
               value="${entry.paymentType}"
               title="Typ der Buchung"
               required
        />
      </td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" name="action" value="Hinzufügen" /></td>
    </tr>
  </table>
</form>
