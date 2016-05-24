<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="new" method="POST">
<p>
  <label>Quelle / Ziel
    <input type="text"
           name="srcDst"
           value="${entry.srcDst}"
           title="Quelle oder Ziel der Buchung"
           placeholder="z.B. Girokonto"
           oninvalid="this.setCustomValidity('Angabe von Quelle oder Ziel ist notwendig')"
           oninput="setCustomValidity('')"
    />
  </label>
  <span class="validation-error" style="color: red;">
    ${validationErrors['srcDst']}
  </span>
</p>
<p>
  <label>Beschreibung
    <input type="text"
           name="description"
           value="${entry.description}"
           title="Beschreibung der Buchung"
           placeholder="z.B. Miete Mai 2016"
           oninvalid="this.setCustomValidity('Angabe der Beschreibung ist notwendig')"
           oninput="setCustomValidity('')"
           required
    />
   </label>
</p>
<p>
  <label>Wert      
    <input type="number"
           name="value"
           value="${entry.value}"
           title="Betrag in Euro"
           placeholder="Betrag in Euro"
           oninvalid="this.setCustomValidity('Angabe des Buchungswertes als Zahl ist notwendig')"
           oninput="setCustomValidity('')"
           required
    /> €
  </label>
</p>
<p>
  <label>Kategorie
    <input type="text"
           name="category"
           value="${entry.category}"
           title="Kategorie der Buchung"
           placeholder="z.B. Lebensmittel"
    />
  </label>
</p>
<p>
  <label>Zahlungstyp
    <input type="text"
           name="paymentType"
           value="${entry.paymentType}"
           title="Typ der Buchung"
           placeholder="z.B. Barabhebung"
           oninvalid="this.setCustomValidity('Zahlungstyp muß angegeben werden')"
           oninput="setCustomValidity('')"
           required
    />
  </label>
</p>
<p>
  <input type="submit" name="action" value="Hinzufügen" />
</p>
</form>
