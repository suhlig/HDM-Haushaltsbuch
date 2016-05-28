<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<p>Es gibt Einträge in ${categories.size()} Kategorien:</p>

<ul id="categories">
<c:forEach items="${categories}" var="category">
  <li><a href="entries/by-category?name=${category}">${category}</a></li>
</c:forEach>
</ul>
