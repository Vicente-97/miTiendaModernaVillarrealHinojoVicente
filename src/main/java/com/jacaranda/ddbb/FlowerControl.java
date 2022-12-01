package com.jacaranda.ddbb;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.jacaranda.model.Flower;

public class FlowerControl {

	public static boolean addFlower(Flower f) throws ControlException {
		boolean result = false;
		Session session = ConnectionDDBB.getSession();
		try {
			session.getTransaction().begin();
			session.save(f);
			session.getTransaction().commit();
			result = true;
		}catch(HibernateException h) {//no se ha podido conectar a la base de datos
				
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new ControlException("El artículo ya existe en la base de datos");
		}
		return result;
	}
	
	public static Flower getFlower(int code) throws ControlException, HibernateException {
		Session session = ConnectionDDBB.getSession();
		Flower f = session.get(Flower.class, code);
		if(f==null) {
			throw new ControlException("No se ha encontrado el artículo en la base de datos");
		}
		return f;
	}
	
	public static ArrayList<Flower> getFlowerList() throws HibernateException{
		Session session = ConnectionDDBB.getSession();
		String select = "SELECT * FROM FLOWER";
		Query<Flower> query = session.createNativeQuery(select, Flower.class);
		ArrayList<Flower> flowers = (ArrayList<Flower>) query.getResultList();
		return flowers;
	}
	
	public static ArrayList<Flower> getCategoryFlowerList(String colorCode) throws HibernateException{
		Session session = ConnectionDDBB.getSession();
		ArrayList<Flower> flowers = (ArrayList<Flower>) session.createQuery("SELECT f FROM com.jacaranda.model.Flower f where color='" + colorCode + "'", Flower.class).getResultList();
		return flowers;
	}
	
	public static boolean deleteFlower(Flower f) throws HibernateException, ControlException{
		boolean result = false;
		Session session = ConnectionDDBB.getSession();
		try {
			session.getTransaction().begin();
			session.delete(f);
			session.getTransaction().commit();
			result = true;
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new ControlException("No se ha encontrado el artículo en la base de datos");
		}
		return result;
		
	}
	
	public static boolean updateFlower(Integer code, String name, String description, Float price, String color) throws HibernateException, ControlException{
		boolean result = false;
		Session session = ConnectionDDBB.getSession();
		//consigo el artículo a actualizar
		Flower f = FlowerControl.getFlower(code);
		
		try {
			//si los valores no son los mismos los cambio en el objeto
			if(!f.getName().equals(name)) {
				f.setName(name);
				result = true;
			}
			if(!f.getDescription().equals(description)) {
				f.setDescription(description);
				result = true;
			}
			if(f.getPrice() != price) {
				f.setPrice(price);
				result = true;
			}
			if(!f.getColor().getCode().equals(color)) {
				f.setColor(ColorControl.getColor(color));
				result = true;
			}
			
			//actualizo la flor en la base de datos
			session.getTransaction().begin();
			session.update(f);
			session.getTransaction().commit();
			result = true;
		}catch (Exception e) {
			session.getTransaction().rollback();
			throw new ControlException("No se ha podido actualizar el artículo en la base de datos");
		}
		return result;
		
	}

}
