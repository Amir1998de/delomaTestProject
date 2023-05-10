package site.beans;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;

import org.hibernate.SessionFactory;

import logic.dao.StoreRecDAO;
import logic.entity.StoreRecommendation;

public class tetsBean
{
	@Inject
	@ManagedProperty(value="#{sessionFactoryBean.sessionFactory}")
	private static SessionFactory sessionFactory;
	
	private static SessionFactoryBean s;
	
	public static void main(String[] args)
	{
		
		/*
		 * A Map allows you to store and access key-value pairs
		 * Create a HashMap with String keys and Object values
		 * Add key-value pairs to the Map with put
		 *  Retrieve a value based on the key with get for example Object o = filters.get("name");
		 */
		final Map<String, Object> filters = new HashMap<>();
		filters.put("name", "f");
		filters.put("description", "a");
		
		 StoreRecDAO dao = new StoreRecDAO(sessionFactory);
		
		final List<StoreRecommendation> storeRecs = dao.getAll();


	}

}
