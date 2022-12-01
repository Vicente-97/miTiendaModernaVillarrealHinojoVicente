package com.jacaranda.ddbb;

import java.util.ArrayList;

import org.hibernate.Session;

import com.jacaranda.model.Color;

public class ColorControl {

	public static Color getColor(String code) throws ControlException {
		Session session = ConnectionDDBB.getSession();
		Color result = (Color) session.get(Color.class, code);
		if(result == null) {
			session.getTransaction().rollback();
			throw new ControlException("No se ha encontrado el color en la base de datos");
		}
		return result;
	}
	
	public static ArrayList<Color> getColors() {
		Session session = ConnectionDDBB.getSession();
		ArrayList<Color> colors = (ArrayList<Color>) session.createQuery("SELECT c FROM com.jacaranda.model.Color c ", Color.class).getResultList();
		return colors;
	}

}
