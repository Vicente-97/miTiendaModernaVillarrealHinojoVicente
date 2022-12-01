package com.jacaranda.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.jacaranda.ddbb.ControlException;
import com.jacaranda.ddbb.FlowerControl;
import com.jacaranda.model.Cart;
import com.jacaranda.model.Flower;
import com.jacaranda.model.User;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//conseguir sesión del usuario
				HttpSession se = request.getSession(); 
				User userSession = (User) se.getAttribute("user");
				
				//comprobar sesión, si el usuario no es nulo recupero el item 
				if(userSession != null){
					//recupero los datos del formulario y el carrito
					int flowerCode = Integer.parseInt(request.getParameter("flower"));
					int quantity = Integer.parseInt(request.getParameter("quantity"));
					Cart cart = (Cart) se.getAttribute("cart");
					HashMap<Integer, Integer> items = cart.getItems();
					
					//compruebo que la cantidad no sea mayor al stock por si se enviaran los datos desde otro sitio
					if(!checkStock(flowerCode, quantity)) {
						response.sendRedirect("errorBackToList.jsp?msg='No hay tantos artículos disponibles'");
					}
					
					//compruebo que el artículo no esté ya en el carrito
					if (!items.containsKey(flowerCode)) {
						items.put(flowerCode, quantity);
						response.sendRedirect("LoginServlet");
					//si el carrito contiene el item sumo la cantidad
					}else {
						//consigo el stock
						int stock=0;
						try {
							stock = FlowerControl.getFlower(flowerCode).getStock();
						} catch (HibernateException | ControlException e) {
							// problema con la base de datos || no se ha encontrado la flor
						}
						
						//compruebo que la suma no sea mayor que el stock
						if(items.get(flowerCode) + quantity > stock) {
							response.sendRedirect("errorBackToList.jsp?msg='No hay tantos artículos disponibles'");
						}else {
							items.put(flowerCode, items.get(flowerCode) + quantity);
							response.sendRedirect("LoginServlet");
						}
					}

				}else {
					response.sendRedirect("error.jsp?msg='No te has autenticado o no estás autorizado'");
				}
	}

	/**
	 * Método que comprueba que una cantidad no es mayor que el stock
	 * @param flowerCode código de un artículo flower
	 * @param quantity cantidad de ese artículo
	 * @return false si el stock no es válido, true si lo es
	 */
	private boolean checkStock(int flowerCode, int quantity) {
		boolean result = false;
		
		try {
			Flower f = FlowerControl.getFlower(flowerCode);
			if(quantity <= f.getStock()) {
				result = true;
			}
		} catch (HibernateException e) { 
			//problema para acceder a la base de datos
		} catch (ControlException e) { 
			//no se ha encontrado la flor
		}
		
		return result;
	}


}
