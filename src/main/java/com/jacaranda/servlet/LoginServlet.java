package com.jacaranda.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.jacaranda.ddbb.ControlException;
import com.jacaranda.ddbb.FlowerControl;
import com.jacaranda.ddbb.UserControl;
import com.jacaranda.model.Cart;
import com.jacaranda.model.Flower;
import com.jacaranda.model.User;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Se accederá por get si se cancela un formulario o después de añadir un artículo al carrito
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map params = request.getParameterMap();
		if(params.size()== 0) {
			HttpSession se = request.getSession(); 
			User userSession = (User) se.getAttribute("user");
			if(userSession !=null){
				ArrayList<Flower> flowerList = FlowerControl.getFlowerList();
				response.getWriter().append(showFlowerPage(userSession, flowerList));
			}else {
				response.sendRedirect("error.jsp?msg='No te has autenticado o no estás autorizado'");
			}
		}else {
			response.sendRedirect("error.jsp?msg='No puedes enviar parámetros por GET a esta página'");
		}
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordEncript = DigestUtils.md5Hex(password);
		String error = null;
		
		//Comprobar que se introducen un usuario y una contraseña
		if(username == null || username.isBlank() ||username.isEmpty() ||
				password == null || password.isBlank() ||password.isEmpty()) {
			response.getWriter().append("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ " <meta charset='UTF-8'>"
					+ "	<title>FC - Error</title>\n"
					+ "	<link type=\"text/css\" rel=\"stylesheet\" href=\"css/error.css\" />\n"
					+ "</head>\n"
					+ "<body>\n"
					+ "    <div>\n"
					+ "        <h1>Error</h1>\n"
					+ "        <h2>Deben introducirse un usuario y una contraseña</h2>\n"
					+ "        <a href='index.jsp'>volver</a>\n"
					+ "    </div>\n"
					+ "</div>\n"
					+ "</body>\n"
					+ "</html>");
		}else {
			try {
				//Comprobar si el usuario es válido
				if(UserControl.checkUser(username, passwordEncript)) {
					//recuperar sesión con tipo User
					User user = UserControl.getUser(username);
					Cart cart = new Cart();
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					session.setAttribute("cart", cart);
					ArrayList<Flower> flowerList = FlowerControl.getFlowerList();
					response.getWriter().append(showFlowerPage(user, flowerList));
				}else {
					error = "El usuario o la contraseña no son correctos";
				}
			}catch(ControlException c) { //Comprobar el acceso a bbdd
				error = c.getMessage();
			}
			if(error != null) {
				response.getWriter().append("<!DOCTYPE html>\n"
						+ "<html>\n"
						+ "<head>\n"
						+ " <meta charset='UTF-8'>"
						+ "	<title>FC - Error</title>\n"
						+ "	<link type=\"text/css\" rel=\"stylesheet\" href=\"css/error.css\" />\n"
						+ "</head>\n"
						+ "<body>\n"
						+ "    <div>\n"
						+ "        <h1>Error</h1>\n"
						+ "        <h2>" + error + "</h2>\n"
						+ "        <a href=\"signup.html\">volver</a>\n"
						+ "    </div>\r\n"
						+ "</div>\r\n"
						+ "</body>\r\n"
						+ "</html>");
			}
		}
	}
	
	private String showFlowers(User user, ArrayList<Flower> flowers) {
		StringBuilder result = new StringBuilder();
		for(Flower f: flowers) {
			result.append("<div class=\"grid-item\">\n"
				+ "            <div class=\"image\">\n"
				+ "                <img src=\"media/flores.jpg\"> \n"
				+ "            </div>\n"
				+ "            <div class=\"info\">\n"
				+ f.toString() + "\n"
				+ "            </div>\n"
				+ showItemButtons(user, f)
				+ "        </div>");
		}
		return result.toString();
	}
	private String showAddButton(User user) {
		String result = "";
		if(user.isAdmin()) {
			result = "<div class='addButton'><a href='addFlower.jsp' class='btn' id='link'> <img src=\"media/add.svg\" width='10px' height='10px'> </a></div>"; 
		}
		return result;
	}
	private String showItemButtons(User user, Flower flower) {
		StringBuilder buttonsDiv = new StringBuilder("<div class='buttons'>");
		if(user.isAdmin()) {
			buttonsDiv.append("<a href='updateFlower.jsp?flower=" + flower.getCode() +"' class='btn' id='link'> <img src=\"media/update.svg\" width='10px' height='10px'></a>"
					+ "<a href='deleteFlower.jsp?flower=" + flower.getCode() +"' class='btn' id='link'> <img src=\"media/delete.svg\" width='10px' height='10px'> </a>");
		}
		if(flower.getStock()>0) {
			buttonsDiv.append("<form action='CartServlet' method='post'>"
					+ "<input type='number' name='quantity' min='1' max='" + flower.getStock() + "' size='4'>"
					+ "<button name='flower' value='" + flower.getCode() + "'><img src=\"media/cart.svg\" width='10px' height='10px'></button>"
					+ "</form>");
		}else {
			buttonsDiv.append("<h1><font color='black' size='5'>Sin stock</font></h1>");
		}
		buttonsDiv.append("</div>");
		
		return buttonsDiv.toString();
	}
	
	
	private String showFlowerPage(User user, ArrayList<Flower> flowerList) {
		String result = "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "    <meta charset='UTF-8'>\n"
				+ "    <title>Catálogo</title>\n"
				+ "    <link rel='stylesheet' type='text/css' href='css/flowerList.css'>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ "<section class=\"header\">"
				+ "<div class=\"logout\"><a href='index.jsp' class='btn' id='link'><img src=\"media/log-out.png\" width='10px' height='10px'></a></div>"
				+ "<div class='history'><a href='history.jsp' class='btn' id='link'>Mis pedidos</a></div>"
				+ "<div class='title'>Floristería Colás</div>"
				+ "<div class='user'>"
				+  showAddButton(user)
				+ "    Hola "+ user.getFirstName() + "\n" 
				+ "    <a href='cart.jsp' class='btn' id='link'> <img src=\"media/cart.svg\" width='10px' height='10px'></a>\n" 
				+ "</div>\n"
				+ "</section>\n"
				+ "<div class=\"grid-container\">"
				+ showFlowers(user, flowerList)
				+ "</div>\n"
				+ "</body>\n"
				+ "</html>";
		return result;
	}

}
