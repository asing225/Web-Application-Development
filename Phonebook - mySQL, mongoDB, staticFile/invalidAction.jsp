<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Invalid Action</title>
</head>
<body>
<%if(request.getParameter("Action") == null || request.getParameter("Action").length() == 0){
	out.write("No action provided.");
}
else{
	out.write("Please provide a valid action.");
}%>
<a href = "index.html">Go back</a>
</body>