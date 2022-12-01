package com.jacaranda.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import com.jacaranda.ddbb.ControlException;
import com.jacaranda.ddbb.FlowerControl;
import com.jacaranda.ddbb.PurchaseControl;
import com.jacaranda.model.Cart;
import com.jacaranda.model.Flower;
import com.jacaranda.model.Purchase;
import com.jacaranda.model.User;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession se = request.getSession(); 
		User userSession = (User) se.getAttribute("user");
		if(userSession !=null){
			Map params = request.getParameterMap();
			Cart cart = (Cart) se.getAttribute("cart");

			HashMap<Integer, Integer> items = cart.getItems();
			//el boton de comprar no envía parámetros
			if(params.size() == 0||params==null||cart==null||params.isEmpty()|| items==null||items.keySet().isEmpty()) {
				//recorro todos los items para crear un objeto de purchase con cada uno
				for(Integer code: items.keySet()) {
					Flower f = null;
					try {
						f = FlowerControl.getFlower(code);
					} catch (HibernateException | ControlException e) {
						// problema con la base de datos || no se ha encontrado la flor
					}
					LocalDate date = LocalDate.now();
					Purchase p = new Purchase(userSession, f, date, items.get(code), f.getPrice());
					try {
						PurchaseControl.addPurchase(p);
						
					} catch (ControlException e) {
						response.sendRedirect("errorBackToList.jsp?msg='" + e.getMessage() + "'");
					}
				}
				
				//vaciar carrito
				Cart newCart = new Cart();
				response.sendRedirect("LoginServlet");
				se.setAttribute("cart", newCart);
			}else {
				response.sendRedirect("LoginServlet");
			}
		}
			
	}

}
