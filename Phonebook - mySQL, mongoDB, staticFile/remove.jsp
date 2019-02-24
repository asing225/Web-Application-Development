<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Remove entry</title>
</head>
<body> 
	<% 
	String pentry = request.getParameter("phone").toString();
	out.write("<a>Removed entry with phone :" + pentry + "</a>");
	%>
	<a href="index.html">Go back</a>
</body>
</html>