<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
        <a href="LoginServlet">Volver al catálogo</a>
    </div>
</body>
</html>