<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<form id="lookup" method="POST">
  <table>
    <tr>
      <th><label for="lookup_id">ID</label></th>
      <td>
        <input id="lookup_id"
               type="text"
               name="id"
               value="${id}"
               size="36"
               title="UUID des gesuchten Eintrags"
               placeholder="12f51bcc-4c88-4004-ae8d-1168b69a359f"
               oninvalid="this.setCustomValidity('Angabe der ID im UUID-Format ist notwendig')"
               oninput="setCustomValidity('')"
               pattern="^[a-z0-9]{8}-([a-z0-9]{4}-){3}[a-z0-9]{12}$"
               required
        />
      </td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" name="action" value="Nachschlagen" /></td>
    </tr>
  </table>
</form>
