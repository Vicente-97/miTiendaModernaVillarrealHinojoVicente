<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>FC - Error</title>
	<link type="text/css" rel="stylesheet" href="css/error.css" />
</head>
<body>
    <div>
        <h1>Error</h1>
        <h2><%= request.getParameter("msg") %></h2>
        <a href="index.jsp">Volver al login</a>
    </div>
</body>
</html>