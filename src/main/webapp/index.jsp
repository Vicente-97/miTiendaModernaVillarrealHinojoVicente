<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FC - Iniciar sesión</title>
<link rel='stylesheet' type='text/css'  href='css/login.css'>
</head>
<body>
<% session.invalidate(); %>
<table align="center">
	<tr>
		<td colspan="2">
			<div class="title">
				<h1>Floristería Colás</h1>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="image">
				<img src="media/flores.jpg">
				</div>
		</td>
		<td>
			<div class="form-container">
				<form action="LoginServlet" method="post">
					<div class="form-title">
						<p>Iniciar sesión</p>
					</div>
					<div class="form-group">
						<label class="form-label" for="username">Nombre de usuario</label>
						<input type="text" class="form-input" placeholder="user123" name="username" required>
					</div>
					<div class="form-group">
						<label class="form-label" for="password">Contraseña</label>
						<input type="password" class="form-input" placeholder="*******" name="password" required>
					</div>
					<div class="form-button">
						<button type="submit" class="btn">Enviar</button><br><br>
						<a href="signup.html" class="btn" id="link">Crear cuenta</a>
					</div>
				</form>
			</div>
		</td>
	</tr>
</table>

</body>
</html>