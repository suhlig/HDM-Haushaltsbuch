<%@ page import="haushaltsbuch.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<table style="text-decoration: line-through;">
  <tr>
    <th>id</th>
    <td>${entry.id}</td>
  </tr>
  <tr>
    <th>srcDst</th>
    <td>${entry.srcDst}</td>
  </tr>
  <tr>
    <th>description</th>
    <td>${entry.description}</td>
  </tr>
  <tr>
    <th>value</th>
    <td>${entry.value}€</td>
  </tr>
  <tr>
    <th>category</th>
    <td>${entry.category}</td>
  </tr>
  <tr>
    <th>paymentType</th>
    <td>${entry.paymentType}</td>
  </tr>
</table>
