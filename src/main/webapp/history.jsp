<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="com.jacaranda.model.User" %>
    <%@ page import ="com.jacaranda.model.Purchase" %>
    <%@ page import ="com.jacaranda.ddbb.PurchaseControl" %>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FC - Mis pedidos</title>
<link rel='stylesheet' type='text/css'  href='css/cart.css'>
</head>
<body>
	<%HttpSession se = request.getSession(); 
	User userSession = (User) se.getAttribute("user");
	if(userSession !=null){%>
		<section class="header"><!--flex container-->
		<div class="logout"><a href='index.jsp' class='btn' id='link'>Cerrar sesión</a></div>
		<div class="title">Floristería Colás</div>
		<div class='user'>Hola <%=userSession.getFirstName() %> </div> 
		</section>
		<a href='LoginServlet' class='btn' id='link'>Volver al catálogo</a>
		<div class="grid-container">
		Historial de compras
		<% ArrayList<Purchase> purchases = PurchaseControl.getUserPurchases(userSession);
		for(Purchase p: purchases){ %>
			<div class="grid-item">
			<%= p.toString() %>
			</div>
		<% } %>
		</div>
	<%}%>

</body>
</html>