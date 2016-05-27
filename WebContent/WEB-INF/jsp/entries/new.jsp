<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="new" method="POST">
<table>
<tr>
  <th>
    <label for="entry_srcDst">Quelle / Ziel</label>
  </th>
  <td>
    <input id="entry_srcDst"
           type="text"
           name="srcDst"
           value="${entry.srcDst}"
           title="Quelle oder Ziel der Buchung"
           placeholder="z.B. Girokonto"
           oninvalid="this.setCustomValidity('Angabe von Quelle oder Ziel ist notwendig')"
           oninput="setCustomValidity('')"
           required
    />
  </td>
</tr>
<tr>
  <th><label for="entry_description">Beschreibung</label>
  </th>
  <td>
    <input id="entry_description"
           type="text"
           name="description"
           value="${entry.description}"
           title="Beschreibung der Buchung"
           placeholder="z.B. Miete Mai 2016"
           oninvalid="this.setCustomValidity('Angabe der Beschreibung ist notwendig')"
           oninput="setCustomValidity('')"
           required
    />
   </td>
</tr>
<tr>
  <th><label for="entry_value">Wert</label>
  </th>
  <td>
    <input id="entry_value"
           type="number"
           name="value"
           value="${entry.value}"
           title="Betrag in Euro"
           placeholder="Betrag in Euro"
           oninvalid="this.setCustomValidity('Angabe des Buchungswertes als Zahl ist notwendig')"
           oninput="setCustomValidity('')"
           required
    /> €
  </td>
</tr>
<tr>
  <th><label for="entry_category">Kategorie</label>
  </th>
  <td>
    <input id="entry_category"
           type="text"
           name="category"
           value="${entry.category}"
           title="Kategorie der Buchung"
           placeholder="z.B. Lebensmittel"
    />
  </td>
</tr>
<tr>
  <th>
    <label for="entry_paymentType">Zahlungstyp</label>
  </th>
  <td>
    <input id="entry_paymentType"
           type="text"
           name="paymentType"
           value="${entry.paymentType}"
           title="Typ der Buchung"
           placeholder="z.B. Barabhebung"
           oninvalid="this.setCustomValidity('Zahlungstyp muß angegeben werden')"
           oninput="setCustomValidity('')"
           required
    />
  </td>
</tr>
<tr>
  <th>
  </th>
  <td>
    <input type="submit" name="action" value="Hinzufügen" />
</tr>
</table>
</form>
