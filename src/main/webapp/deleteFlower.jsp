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
<title>FC - Borrar artículo</title>
<link rel='stylesheet' type='text/css'  href='css/form.css'>
</head>
<body>
<% HttpSession se = request.getSession(); 
	User userSession = (User) se.getAttribute("user");
	if(userSession !=null && userSession.isAdmin()){ 
		Flower flower = FlowerControl.getFlower(Integer.parseInt(request.getParameter("flower")));
		String formSent = request.getParameter("sent");
		String formConfirmed = request.getParameter("confirmed");
		
		//si el formulario se ha enviado confirmo el borrado
		if(formSent != null && formSent.equals("true")){%>
			<form action="deleteFlower.jsp" method="post">
				<div class="form-group">
					<input type="hidden" name="flower" value="<%= flower.getCode() %>">
					<input type="hidden" name="confirmed" value="true">
					<label class="form-label" for="confirm">¿Estás seguro/a de que quieres borrar el artículo?</label> 
					<button type="submit" class="btn">Borrar</button>
					<a href="deleteFlower.jsp?flower='<%= flower.getCode() %>'" class="btn" id="link">Volver</a>
				</div>
			</form>
		<% }
		
		//si el formulario de confirmación se envía borro el artículo
		if(formConfirmed != null && formConfirmed.equals("true")){
			try{
				FlowerControl.deleteFlower(flower);%>
				Artículo borrado
				<a href="LoginServlet" class="btn" id="link">Volver al catálogo</a>
			<%}catch(Exception e){
						String message = e.getMessage();%>
			<jsp:forward page="errorBackToList.jsp"><jsp:param
					name="error" value="<%= message %>" /></jsp:forward>
			<%}
		}%>
	
			<div class="form-container">
				<form action="deleteFlower.jsp" method="post">
					<div class="form-title">
						<p>Datos del artículo</p>
					</div>
					<div class="form-group">
						<label class="form-label" for="code">Código</label> 
						<input type="text" class="form-input" name="code" value="<%= flower.getCode() %>" readonly>
					</div>
					<div class="form-group">
						<label class="form-label" for="name">Nombre</label> 
						<input type="text" class="form-input" value="<%= flower.getName() %>" name="name" readonly>
					</div>
					<div class="form-group">
						<label class="form-label" for="description">Descripción</label> 
						<input type="text" class="form-input" value="<%= flower.getDescription() %>"
							name="description" readonly>
					</div>
					<div class="form-group">
						<label class="form-label" for="price">Precio</label> 
						<input type="text" class="form-input" value="<%= flower.getPrice() %>" name="price" readonly>
					</div>
					<div class="form-group">
						<label class="form-label" for="color">Código de color</label> 
						<input type="text" class="form-input" value="<%= flower.getColor().getCode() %>" readonly>
					</div>
					<div class="form-buttton">
						<input type="hidden" name="flower" value="<%= flower.getCode() %>">
						<input type="hidden" name="sent" value="true">
						<button type="submit" class="btn">Borrar</button>
						<a href="LoginServlet" class="btn" id="link">Volver</a>
					</div>
				</form>
			</div>
	<% }

	else{%>
	<jsp:forward
		page="error.jsp?msg='No te has autenticado o no estás autorizado'"></jsp:forward>
	<% } %>

</body>
</html>