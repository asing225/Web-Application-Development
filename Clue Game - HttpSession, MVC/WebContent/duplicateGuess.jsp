<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:useBean id="playerGuess" type="com.asu.ser422.model.Guess" scope="session">
</jsp:useBean>
<head>
<meta charset="UTF-8">
<title>Duplicate Guess</title>
</head>
<body>
	<p style = "color:red">You've already made the </p>
	<% out.write("<p style=\"color:red\">" + playerGuess + ". Please try again</p>");%>
	
	<form method="GET" action="player-servlet">
            <input type="Submit" value="Continue"/>
   	</form>
	
</body>
</html>