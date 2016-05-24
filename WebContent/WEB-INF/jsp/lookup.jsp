<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<form id="lookup" method="POST">
  <table>
    <tr>
      <th>ID</th>
      <td>
        <input type="text"
               name="id"
               value="${id}"
               title="UUID des gesuchten Eintrags"
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
