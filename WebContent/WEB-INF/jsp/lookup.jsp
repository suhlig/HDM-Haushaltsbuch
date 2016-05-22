<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
<head>
<title>Suchen</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="normalize-4.1.1.css">
<link rel="stylesheet" href="main.css">
<base href="<c:url value="/"/>"/>
</head>
<body>
  <%@include file="_menu.jspf"%>

  <h2>Eintrag suchen</h2>

	<c:if test="${not empty error}">
	  <p id="error" style="color: red;">
	    ${error}
	  </p>
	</c:if>
  
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
</body>
</html>

