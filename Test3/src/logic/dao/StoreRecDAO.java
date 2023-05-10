package logic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;
import site.beans.SessionFactoryBean;

public class StoreRecDAO 
{

	/*
	 * properties
	 */
	
	private final SessionFactory sessionFactory;
	
	
	

	/*
	 * main
	 */
	public static void main(final String[] args) 
	{
		/*
		 * A Map allows you to store and access key-value pairs
		 * Create a HashMap with String keys and Object values
		 * Add key-value pairs to the Map with put
		 *  Retrieve a value based on the key with get for example Object o = filters.get("name");
		 */
		final Map<String, Object> filters = new HashMap<>();
		
		final Map<String, SortOrder> sorts = new HashMap<>();
		filters.put("name", "Pay");
		//filters.put("description", "p");
		sorts.put("name", SortOrder.DESCENDING);
		
		// ?? 
		final StoreRecDAO dao = new StoreRecDAO(SessionFactoryBean.createSessionFactory());
		
		final List<StoreRecommendation> storeRecs = dao.testFilterHQL(filters,sorts);

		
		
		
		// sysstem out
		System.out.println("Name");
		storeRecs.forEach(action -> System.out.println(action.getName()));
		System.out.println("");
		System.out.println("Description");
		storeRecs.forEach(action -> System.out.println(action.getDescription()));
	}



	

	/*
	 * constructor
	 */
	public StoreRecDAO(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	/*
	 * method
	 */
	/**
	 * delete
	 *
	 * @param sr
	 * @return
	 */
	public boolean delete(final StoreRecommendation sr) {
		boolean result = true;
		final Session session = this.sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.remove(sr);
			session.getTransaction().commit();

		} catch (final Exception e) {
			result = false;
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}

		} finally {
			session.close();
		}

		return result;
	}

	public List<StoreRecommendation> getAll() {

		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		final List<StoreRecommendation> storeRecommendations = session
				.createQuery("FROM StoreRecommendation", StoreRecommendation.class).list();
		session.getTransaction().commit();
		return storeRecommendations;
	}

	/**
	 * getAll
	 *
	 * @return
	 */

	/*
	 * delete
	 */

	/*
	 * getList
	 */
	public List<StoreRecommendation> getList(final int offset, final int pageSize, final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) 
	{
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// createHql -> Select / where / SortBy 
		String hql  = DaoUtil.createHql(StoreRecommendation.class, "sr", false, filters, sortBy);
	    	

		final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);
		// add params to query
		DaoUtil.addQueryParams(query, filters);

		System.out.println("StoreRecDAO//getList.hql : " + hql);

		// HQL *

		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		final List<StoreRecommendation> results = query.list();

		session.getTransaction().commit();
		return results;
	}

	/*
	 * getRowCount
	 * 
	 */
	public int getRowCount(final Map<String, Object> filters) {
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// createHql -> Select / where / null 
		String hql  = DaoUtil.createHql(StoreRecommendation.class, "sr", true, filters, null);

		final Query<Long> query = session.createQuery(hql, Long.class);

		DaoUtil.addQueryParams(query, filters);

		final Long count = query.uniqueResult();
		session.getTransaction().commit();
		return count.intValue();

	}

	// save
	public void save(final StoreRecommendation storeRecommendation) {
		System.out.print("StoreRecommendationDAO.save");
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		session.save(storeRecommendation);
		session.getTransaction().commit();
	}

	public List<StoreRecommendation> testFilterCriteria() {
		return null;
	}

	/*
	 * testFilterHQL
	 */

	

	public List<StoreRecommendation> testFilterHQL(final Map<String, Object> filters,final Map<String, SortOrder> sortBy) {

		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		// createHql -> Select / where / SortBy
		String hql  = DaoUtil.createHql(StoreRecommendation.class, "sr", false, filters, sortBy);
		
		
		final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);
	
		// add params to query
		DaoUtil.addQueryParams(query, filters);

		final List<StoreRecommendation> storeRecommendations = query.list();

		session.getTransaction().commit();
		return storeRecommendations;
	}

	public StoreRecommendation testFilterCriteria(final long id) {
	
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<StoreRecommendation> criteria = builder.createQuery(StoreRecommendation.class);
		Root<StoreRecommendation> root = criteria.from(StoreRecommendation.class);
		
		//FilterCriteria titleCriteria = builder.like(root.get("name"), "%" + id + "%");
		
	return null;
	}

	public List<StoreRecommendation> testFilterCriteria(final Map<String, Object> filters) {

		return null;
	}
	/*
	 * sortOrderConvertor
	 */

	// update
	public boolean update(final StoreRecommendation sr) {
		boolean result = true;
		final Session session = this.sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.update(sr);
			session.getTransaction().commit();

		} catch (final Exception e) {
			result = false;
			if (session.getTransaction() != null) {
				session.getTransaction().rollback();
			}

		} finally {
			session.close();
		}

		return result;

	}

	/*
	 * HQL
	 */
	
	
	
	


	



	
	

}
