package com.jacaranda.ddbb;

import com.jacaranda.model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UserControl {
	
	public static boolean addUser(User u) throws ControlException {
		boolean result = false;
		Session session = ConnectionDDBB.getSession();
		try {
			session.getTransaction().begin();
			session.save(u);
			session.getTransaction().commit();
			result = true;
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new ControlException("Ese usuario ya existe en la base da datos, elige otro nombre de usuario");
		}
		
		return result;
	}
	
	public static User getUser(String username) throws ControlException, HibernateException {
		Session session = ConnectionDDBB.getSession();
		User result = (User)session.get(User.class, username);
//		Query query = session.createQuery("FROM com.jacaranda.model.User WHERE username= :username"); //mirar sintaxis
//		query.setParameter("username", username);
//		User result = (User) query.getSingleResult();
		if(result == null) {
			session.getTransaction().rollback();
			throw new ControlException("No se ha encontrado el usuario en la base de datos");
		}
		return result;
	} 
	/**
	 * Método que controla los datos del usuario para hacer login
	 * @param name nombre de usuario
	 * @param passwd contraseña del usuario
	 * @return false si el usuario no se ha encontrado, true si el usuario coincide
	 * @throws ControlException si no se puede acceder a la base de datos, recoge la HibernateException
	 */
	public static boolean checkUser(String name, String passwd) throws ControlException { //contraseña ya encriptada
		boolean result = false;
		try {
			User u = getUser(name);
			if(u.getPassword().equals(passwd)) {
				result = true;
			}
		}catch(ControlException e) {//si no se encuentra el usuario por el código pasado por parámetro recoge la exception de getUser
			
		}catch(HibernateException h) {
			throw new ControlException("No se ha podido acceder a la base de datos");
		}
		return result;
	}

}
