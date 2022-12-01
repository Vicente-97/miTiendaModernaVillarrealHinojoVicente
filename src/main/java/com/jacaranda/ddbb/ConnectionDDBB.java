package com.jacaranda.ddbb;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConnectionDDBB {

	/**
	 * Atributos estáticos porque son compartidos por todos los objetos
	 */
	private static StandardServiceRegistry sr = new StandardServiceRegistryBuilder().configure().build();
	private static SessionFactory sf = new MetadataSources(sr).buildMetadata().buildSessionFactory();
	private static Session session = sf.openSession();
		
	/**
	 * Método que obtiene la sesión en la base de datos
	 * @return la sesión en la base de datos
	 * @throws HibernateException si no se puede acceder a la base de datos (no configurado ahora mismo)
	 */
	public static Session getSession() {
		//session = sf.openSession();
		return session;
	}

}
