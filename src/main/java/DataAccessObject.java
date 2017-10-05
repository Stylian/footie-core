package main.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import main.java.dtos.Team;

public class DataAccessObject<T> {

	private Session session;
	
	public DataAccessObject(Session session) {
		this.session = session;
	}
	
	public int save(T t) {

		Integer id = null;
    
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			id = (Integer) session.save(t);
			tx.commit();
		}catch (HibernateException e) {
      if (tx!=null) {
      	tx.rollback();
      }
      e.printStackTrace(); 
		}
		
		return id;
		
	}
	
	public T getById(int id, Class<T> clazz) {
		
		return (T) session.get(clazz, id);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> list(String table) {

		List<T> list = new ArrayList<>();
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List teams = session.createQuery("FROM " + table).list();

			for (Iterator iterator = teams.iterator(); iterator.hasNext();) {
			
				list.add( (T) iterator.next() );

			}

			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		
		return list;
		
	}

	public void delete(T t){
	
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			
			session.delete(t);
			
			tx.commit();
		}catch (HibernateException e) {
      if (tx!=null) {
      	tx.rollback();
      }
      e.printStackTrace(); 
		}
		
	}
	
}
