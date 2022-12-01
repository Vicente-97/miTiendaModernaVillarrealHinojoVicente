<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<meta charset="ISO-8859-1">
<title>FC - Actualizar artículo</title>
<link rel='stylesheet' type='text/css'  href='css/form.css'>
</head>
<body>
	<% HttpSession se = request.getSession(); 
	User userSession = (User) se.getAttribute("user");
	if(userSession !=null && userSession.isAdmin()){ 
		Map params = request.getParameterMap();
		Flower flower = FlowerControl.getFlower(Integer.parseInt(request.getParameter("flower")));
		if(params.size() > 1){
			//Guardo los valores del formulario
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			Float price = Float.parseFloat(request.getParameter("price"));
			String color = request.getParameter("color");
			
			try{
				//Actualizo la flor
				FlowerControl.updateFlower(flower.getCode(), name, description, price, color);%>
				<a href="LoginServlet" class="btn" id="link">Volver al catálogo</a>
			<%}catch(Exception e){
						String message = e.getMessage();%>
			<jsp:forward page="errorBackToList.jsp"><jsp:param
					name="error" value="<%= message %>" /></jsp:forward>
			<%}
		}else{%>
			<div class="form-container">
				<form action="updateFlower.jsp" method="post">
					<div class="form-title">
						<p>Actualizar artículo</p>
					</div>
					<div class="form-group">
						<label class="form-label" for="code">Código</label> 
						<input type="text" class="form-input" name="code" value="<%= flower.getCode() %>" readonly>
					</div>
					<div class="form-group">
						<label class="form-label" for="name">Nombre</label> 
						<input type="text" class="form-input" placeholder="Clavel" name="name" value="<%= flower.getName() %>" required>
					</div>
					<div class="form-group">
						<label class="form-label" for="description">Descripción</label> 
						<input type="text" class="form-input" placeholder="Dianthus caryophyllus" value="<%= flower.getDescription() %>" name="description">
					</div>
					<div class="form-group">
						<label class="form-label" for="price">Precio</label> 
						<input type="text" class="form-input" placeholder="1.20" name="price" value="<%= flower.getPrice() %>" required>
					</div>
					<div class="form-group">
						<label class="form-label" for="color">Código de color</label> 
						<select name="color" required>
							<% ArrayList<Color> colors = ColorControl.getColors();
				        			for(Color c: colors){%>
							<option value="<%= c.getCode() %>"><%= c.getName() %> -
								<%= c.getCode() %></option>
							<%}%>
						</select>
						<p>Código actual: <%= flower.getColor().getCode() %></p>
					</div>
					<div class="form-buttton">
						<input type="hidden" name="flower" value="<%= flower.getCode() %>">
						<button type="submit" class="btn">Enviar</button>
						<a href="LoginServlet" class="btn" id="link">Volver</a>
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