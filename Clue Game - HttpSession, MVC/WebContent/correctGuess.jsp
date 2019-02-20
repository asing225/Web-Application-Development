<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:useBean id="playerGuess" type="com.asu.ser422.model.Guess" scope="session">
</jsp:useBean>
<jsp:useBean id="compGuess" type="com.asu.ser422.model.Guess" scope="session">
</jsp:useBean>
<head>
<meta charset="UTF-8">
<title>Computer Wins</title>
</head>
<body>
<% 	out.write("<p>Your " + playerGuess + " was correct. You win!");
	out.write("<p style=\"color:red\">Computer's guess: " + compGuess + "<br>Computer guess is incorrect. Computer guessed ");
	String wrongCompGuess = (String)session.getAttribute("wrongComputerGuess");
	out.write(wrongCompGuess + " incorrectly.</p>");
	session.invalidate();
	%>
	<form method="GET" action="./">
	<input type="Submit" value = "Continue">
	</form>
</body>
</html>