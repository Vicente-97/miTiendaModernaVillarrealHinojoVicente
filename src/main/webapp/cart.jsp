<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="com.jacaranda.model.User" %>
    <%@ page import ="com.jacaranda.model.Flower" %>
    <%@ page import ="com.jacaranda.model.Cart" %>
    <%@ page import ="com.jacaranda.ddbb.FlowerControl" %>
    <%@ page import="java.util.HashMap" %>
    <%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FC - Carrito</title>
<link rel='stylesheet' type='text/css'  href='css/cart.css'>
</head>
<body>

	<%HttpSession se = request.getSession(); 
	User userSession = (User) se.getAttribute("user");
	if(userSession !=null){
		Map params = request.getParameterMap();
		Cart cart = (Cart) se.getAttribute("cart");
		HashMap<Integer, Integer> items = cart.getItems();
		
		//compruebo si se ha cambiado la cantidad de algún artículo y la actualizo
		if(params.size() > 0){
			Integer flowerCode = Integer.parseInt(request.getParameter("flower"));
			Integer quantity = Integer.parseInt(request.getParameter("quantity"));
			items.put(flowerCode, quantity);
			//TO DO comprobar que por parámetro no se pase más cantidad que el stock
		}%>
		
		<section class="header"><!--flex container-->
			<div class="logout"><a href='index.jsp' class='btn' id='link'>Cerrar sesión</a></div>
			<div class="title">Floristería Colás</div>
			<div class='user'>Hola <%=userSession.getFirstName() %> </div> 
		</section>

		<div class="grid-container">
			Mi carrito
			<% float total = 0;
			for(Integer code: items.keySet()){
			Flower f = FlowerControl.getFlower(code); 
			total += f.getPrice()*items.get(code); %>
				<div class="grid-item">
					<div class="image">
					</div>
					<div class="info">
					Item: <%= f.getName() %>
					Precio: <%= f.getPrice() %>€
					</div>
					<div class="buttons">
						<form method="post" action="cart.jsp">
						<input type='number' name='quantity' min='1' max='<%= f.getStock() %>' value='<%= items.get(code) %>' size='4'>
						<button name='flower' value='<%= code %>'>Actualizar cantidad</button>
						</form>
					</div>
				</div>
			<%}%>
			<div class="cart-buttons">
				<a href="LoginServlet">Volver al catálogo</a>
				<form method="post" action="PurchaseServlet">
				<p>Total: <%= total %>€</p>
				<%if(cart==null||cart.getItems()==null||cart.getItems().keySet().isEmpty()||
					cart.getItems().keySet()==null||cart.getItems().isEmpty()){
					
				}else{%>
					<button>Comprar</button>
					
				<% }%>
			
				<!-- <button>Comprar</button> -->
				</form>
				<a href="vaciarCarrito.jsp"><button>Vaciar carrito</button></a>
			</div>
		</div>
		
	<% }else {
		response.sendRedirect("error.jsp?msg='No te has autenticado o no estás autorizado'");
	} %>

</body>
</html>