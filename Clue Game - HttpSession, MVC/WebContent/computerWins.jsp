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
<% 	out.write("<p style=\"color:red\">Your " + playerGuess + " was incorrect. You guessed ");
	String wrongGuess = (String)session.getAttribute("wrongPlayerGuess");
	out.write(wrongGuess + " incorrectly.</p>");
	out.write("Computer's guess " + compGuess);
	%>
	
	<br><p>Computer guess is correct. Computer wins!</p>
	
	<%session.invalidate(); %>
	<form method="GET" action="./">
	<input type="Submit" value = "Continue">
	</form>
</body>
</html>