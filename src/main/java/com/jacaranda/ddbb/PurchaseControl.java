package com.jacaranda.ddbb;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.jacaranda.model.Purchase;
import com.jacaranda.model.User;

public class PurchaseControl {

	public static boolean addPurchase(Purchase p) throws ControlException {
		boolean result = false;
		Session session = ConnectionDDBB.getSession();
		try {
			session.getTransaction().begin();
			session.save(p);
			session.getTransaction().commit();
			result = true;
		}catch(HibernateException h) {//no se ha podido conectar a la base de datos
				
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new ControlException("Se ha realizado la misma compra en el mismo día, no se puede registrar");
		}
		return result;
	}
	
	public static ArrayList<Purchase> getUserPurchases(User user) throws HibernateException{
		Session session = ConnectionDDBB.getSession();
		ArrayList<Purchase> purchases = (ArrayList<Purchase>) session.createQuery("SELECT p FROM com.jacaranda.model.Purchase p where username='" + user.getUsername() + "' ORDER BY date DESC", Purchase.class).getResultList();
		return purchases;
	}

}
