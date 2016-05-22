<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<form id="lookup" method="POST">
  <table>
    <tr>
      <th>ID</th>
      <td><input type="text" name="id" value="${id}" /></td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" name="action" value="Suchen" /></td>
    </tr>
  </table>
</form>
