<!DOCTYPE HTML>
<%@ page import="haushaltsbuch.*" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>List</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
Entries: <%= ((List<Entry>) request.getAttribute("entries")).size() %> entries
</body>
</html>