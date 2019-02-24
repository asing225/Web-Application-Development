<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, edu.asu.ser422.phone.model.PhoneEntry"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List all</title>
</head>
<body>
<%
	List<PhoneEntry> list = (List<PhoneEntry>) request.getAttribute("entries");
%>
<b>Data:</b><br>
<%
	for(int i=0; i< list.size(); i++) {
		out.write("<b>"+i+": </b>" + "<a>" + list.get(i).getFirstName() + " " 
	+ list.get(i).getLastName()+ " " + list.get(i).getPhone() + "</a><br>");
	}
%>
<a href="index.html">Go back</a>
</body>
</html>