<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import ="com.jacaranda.model.User" %>
    <%@ page import ="com.jacaranda.model.Flower" %>
    <%@ page import ="com.jacaranda.model.Color" %>
    <%@ page import ="com.jacaranda.ddbb.ColorControl" %>
    <%@ page import ="com.jacaranda.ddbb.FlowerControl" %>
    <%@ page import="java.util.ArrayList" %>
    <%@page import="java.util.Map"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FC - Añadir artículo</title>
<link rel='stylesheet' type='text/css'  href='css/form.css'>
</head>
<body>
	<% HttpSession se = request.getSession(); 
	User userSession = (User) se.getAttribute("user");
	if(userSession !=null && userSession.isAdmin()){ 
		Map params = request.getParameterMap();
		if(params.size() > 0){
			//Guardo los valores del formulario
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			Float price = Float.parseFloat(request.getParameter("price"));
			Color color = ColorControl.getColor(request.getParameter("color"));
			Integer stock = Integer.valueOf(request.getParameter("stock"));
			//Creo el objeto y lo guardo en la base de datos
			Flower newFlower = new Flower(name, description, price, color, stock);
			try{
				FlowerControl.addFlower(newFlower);%>
				Artículo añadido
				<a href="LoginServlet" class="btn" id="link">Volver al catálogo</a>
			<%}catch(Exception e){
						String message = e.getMessage();%>
			<jsp:forward page="errorBackToList.jsp"><jsp:param
					name="error" value="<%= message %>" /></jsp:forward>
			<%}
		}else{%>
			<div class="form-container">
				<form action="addFlower.jsp" method="post">
					<div class="form-title">
						<p>Añadir artículo</p>
					</div>
					<div class="form-group">
						<label class="form-label" for="name">Nombre</label> <input type="text"
							class="form-input" placeholder="Clavel" name="name" required>
					</div>
					<div class="form-group">
						<label class="form-label" for="description">Descripción</label> <input
							type="text" class="form-input" placeholder="Dianthus caryophyllus"
							name="description">
					</div>
					<div class="form-group">
						<label class="form-label" for="price">Precio</label> <input type="text"
							class="form-input" placeholder="1.20" name="price" required>
					</div>
					<div class="form-group">
						<label class="form-label" for="color">Código de color</label> <select
							name="color" required>
							<% ArrayList<Color> colors = ColorControl.getColors();
				        			for(Color c: colors){%>
							<option value="<%= c.getCode() %>"><%= c.getName() %> -
								<%= c.getCode() %></option>
							<%}%>
						</select>
					</div>
					<div class="form-buttton">
						<button type="submit" class="btn">Enviar</button>
						<a href="LoginServlet" class="btn" id="link">Volver</a>
					</div>
					<div class="form-group">
						<label class="form-label" for="stock">stock</label> <input type="number"
							class="form-input" placeholder="1" name="stock" required>
					</div>
				</form>
			</div>
	<% }

	}else{%>
	<jsp:forward
		page="error.jsp?msg='No te has autenticado o no estás autorizado'"></jsp:forward>
	<% } %>

</body>
</html>