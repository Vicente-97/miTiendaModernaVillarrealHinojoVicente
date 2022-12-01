<%@page import="com.jacaranda.model.Cart"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
//Se recupera el mapa (carrito) guardado en la session.
Cart carrito = (Cart) session.getAttribute("cart");


//Se recupera los Items y se limpian.
carrito.getItems().keySet().clear();


//Se redirige a la lista de flores.
response.sendRedirect("LoginServlet");

%>

</body>
</html>