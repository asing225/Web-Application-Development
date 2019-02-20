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
<title>Incorrect Guess</title>
</head>
<body>
<% 	out.write("<p style=\"color:red\">Your guess " + playerGuess + " was incorrect. You guessed ");
	String wrongGuess = (String)session.getAttribute("wrongPlayerGuess");
	out.write(wrongGuess + " incorrectly. Please try again.</p>");
	
	out.write("<p>Computer's guess " + compGuess + "</p><br>");
	String wrongCompGuess = (String)session.getAttribute("wrongComputerGuess");
	out.write("<p style=\"color:red\">Computer guessed " + wrongCompGuess + " incorrectly.</p>");		
			
	%>
	<form method="GET" action="player-servlet">
	<input type="Submit" value = "Continue">
	</form>
</body>
</html>