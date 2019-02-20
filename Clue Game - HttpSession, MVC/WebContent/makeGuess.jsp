<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List, java.util.ArrayList" %>
<head>
<jsp:useBean id="dataObject" class="com.asu.ser422.model.Data" />
<title>Make Guess</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<%
String suspects = dataObject.getSuspects();
String rooms = dataObject.getRooms();
String weapons = dataObject.getWeapons();

out.write(suspects + "<br>");
out.write(rooms + "<br>");
out.write(weapons + "<br>");

List<String> playerSuspects = (ArrayList<String>) session.getAttribute("playerSuspectsList");
List<String> playerRooms = (ArrayList<String>) session.getAttribute("playerRoomsList");
List<String> playerWeapons = (ArrayList<String>) session.getAttribute("playerWeaponsList");

%>

<br>
<form method="POST" action="player-servlet">
<select id = "suspect" name = "suspect">
	<%for (String s: playerSuspects){
	    out.write("<option value=\"" + s + "\">" + s + "</option>");
	}
	%>
</select>
<br>
<select id = "room" name = "room">
	<%for (String s: playerRooms){
	    out.write("<option value=\"" + s + "\">" + s + "</option>");
	}
	%>
</select>
<br>
<select id = "weapon" name = "weapon">
	<%for (String s: playerWeapons){
	    out.write("<option value=\"" + s + "\">" + s + "</option>");
	}
	%>
</select>
<br>
<input type="submit" name = "action" value="Guess"/></form>
</body>
</html>
