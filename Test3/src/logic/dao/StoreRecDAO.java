package logic.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;
import site.beans.SessionFactoryBean;

public class StoreRecDAO {

	/*
	 * properties
	 */

	private final SessionFactory sessionFactory;

	/*
	 * main xx
	 */
	public static void main(String[] args) {
		System.out.println("---------------------------------------------------");
        // Aktuelles Datum und Uhrzeit
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String formattedCurrentDate = formatter.format(currentDate);
        System.out.println(formattedCurrentDate);
       
        // Spezifisches Datum und Uhrzeit
        Date specificDate = new Date(123, 5, 24, 12, 10, 30); // Jahr: 2022, Monat: Juli (0-basiert), Tag: 15, Stunde: 14, Minute: 30, Sekunde: 45
        String formattedSpecificDate = formatter.format(specificDate);
        System.out.println(formattedSpecificDate);
        
        
		final Map<String, Object> filters = new HashMap<>();

		final Map<String, SortOrder> sorts = new HashMap<>();
		filters.put("timeCreated", specificDate);
		// filters.put("description", "D");
		sorts.put("name", SortOrder.ASCENDING);
		// sorts.put("description", SortOrder.DESCENDING);

		final StoreRecDAO dao = new StoreRecDAO(SessionFactoryBean.createSessionFactory());

		// 1. test creiteria single
		final StoreRecommendation storeRecCriteria = dao.testFilterCriteria(85);
		System.out.println("last Description : "+storeRecCriteria.getDescription());
		storeRecCriteria.setDescription("Test test Tessdfst");
		dao.update(storeRecCriteria);

		
		// 2. test criteria list dynamic
		final List<StoreRecommendation> storeRecsCriteria = dao.testFilterCriteria(filters, sorts);
		
		// sysstem out

		storeRecsCriteria.forEach(action -> System.out
				.println("name : " + action.getName() + "\t Description : " + action.getDescription()));

		System.out.println("RowCount : " + dao.getRowCount(filters));

		/*
		 * A Map allows you to store and access key-value pairs Create a HashMap
		 * with String keys and Object values Add key-value pairs to the Map
		 * with put Retrieve a value based on the key with get for example
		 * Object o = filters.get("name");
		 */

		// ??
		/*
		 * final StoreRecDAO dao = new
		 * StoreRecDAO(SessionFactoryBean.createSessionFactory());
		 *
		 * final List<StoreRecommendation> storeRecs =
		 * dao.testFilterHQL(filters, sorts);
		 *
		 * // System out System.out.println("Name"); storeRecs.forEach(action ->
		 * System.out.println(action.getName())); System.out.println("");
		 * System.out.println("Description"); storeRecs.forEach(action ->
		 * System.out.println(action.getDescription()));
		 */

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
	public void delete(final StoreRecommendation sr) 
	{
		final Session session = this.sessionFactory.openSession();

		Runnable run = () -> session.remove(sr);
		
		DaoUtil.run(session, run);
	}

	public List<StoreRecommendation> getAll() {
		
		final Session session = this.sessionFactory.openSession();
		
		final List<StoreRecommendation> storeRecommendations = session
			.createQuery("FROM StoreRecommendation", StoreRecommendation.class).list();
		
		try
		{
			session.beginTransaction();

			session.getTransaction().commit();
			
			return storeRecommendations;
			
		}
		catch (Exception e)
		{
			
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}finally {
			
			session.close();
			
		}
		
		return storeRecommendations;

	}

	/**
	 * getAll
	 *
	 * @return
	 */

	/*
	 * getList
	 */
	public List<StoreRecommendation> getList(final int offset, final int pageSize, final Map<String, Object> filters,
		final Map<String, SortOrder> sortBy) {
		
		final Session session = this.sessionFactory.openSession();
		
		List<StoreRecommendation> results = null;

		try
		{
			session.beginTransaction();
			
			// CriteriaAPI
			final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
				session, filters, sortBy, false);
			
			final Query<StoreRecommendation> query = session.createQuery(criteria);
			
			results = query.getResultList();
			
			query.setFirstResult(offset);
			
			query.setMaxResults(pageSize);
			
			session.getTransaction().commit();
			// Query<StoreRecommendation> query = null;
			// if (criteria)
		
			// setFirst

			// HQL :
			/*
			 * // createHql -> Select / where / SortBy final String hql =
			 * DaoUtil.createHql(StoreRecommendation.class, "sr", false, filters,
			 * sortBy); final Query<StoreRecommendation> query =
			 * session.createQuery(hql, StoreRecommendation.class); // add params to
			 * query DaoUtil.addQueryParams(query, filters);
			 *
			 */
			
		}catch (Exception e) {
			
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}
		finally
		{
			session.close();
		}

		return results;
	}

	/*
	 * getRowCount
	 *
	 */
	public int getRowCount(final Map<String, Object> filters) {
		
		final Session session = this.sessionFactory.openSession();
		
		int erg = 0 ;
		
		try
		{
			session.beginTransaction();
			
			// create CriteriaQuery
			final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
					session, filters, null, true);
			// criteria
			erg = session.createQuery(criteria).getResultList().size(); 
			
			 // createHql -> Select / where / null 
			/*  final String hql =  DaoUtil.createHql(StoreRecommendation.class, "sr", true, filters, null);
			  final Query<Long> query = session.createQuery(hql, Long.class);
			  DaoUtil.addQueryParams(query, filters);
			  final Long count = query.uniqueResult();
			  session.getTransaction().commit(); return count.intValue();*/
			
		}catch (Exception e) {
			
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}
		finally
		{
			session.close();
		}
		
		return erg ; 
		 
	}

	// save
	public void save(final StoreRecommendation sr) {

		final Session session = this.sessionFactory.openSession();
		
		Runnable run = () -> session.save(sr);
		
		DaoUtil.run(session, run);
	}

	/*
	 * testFilterHQL
	 */

	public List<StoreRecommendation> testFilterHQL(final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) {
		
		final Session session = this.sessionFactory.openSession();
		
		List<StoreRecommendation> storeRecommendations = null;

		try
		{
			session.beginTransaction();

			// createHql -> Select / where / SortBy
			final String hql = DaoUtil.createHql(StoreRecommendation.class, "sr", false, filters, sortBy);

			final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);

			// add params to query
			DaoUtil.addQueryParams(query, filters);

			storeRecommendations = query.list();

			session.getTransaction().commit();
			
		}
		catch (Exception e)
		{
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}finally {
			
			session.close();
		}

		return storeRecommendations;

	}

	/*
	 * FilterCriteria -> Filter with id
	 */

	public StoreRecommendation testFilterCriteria(final long id) {
		
		final Session session = this.sessionFactory.openSession();
		
		 StoreRecommendation sr = null;
		try
		{
			session.beginTransaction();
			
			// session.get(StoreRecommendation.class, id);

			final CriteriaBuilder builder = session.getCriteriaBuilder();
			
			final CriteriaQuery<StoreRecommendation> criteria = builder.createQuery(StoreRecommendation.class);
			
			final Root<StoreRecommendation> root = criteria.from(StoreRecommendation.class);

			criteria.select(root).where(builder.equal(root.get("id"), id));

			sr = session.createQuery(criteria).getSingleResult();

		
		}
		catch (Exception e)
		{
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}finally {
			
			session.close();
		}
		
		return sr;
	}

	/*
	 * FilterCriteria -> Filter with Mapping
	 */

	public List<StoreRecommendation> testFilterCriteria(final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) {
		
		final Session session = this.sessionFactory.openSession();
		
		List<StoreRecommendation> sr = null;
		
		try
		{
			session.beginTransaction();

			// create CriteriaQuery
			final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
																											 session, 
																											 filters, 
																											  sortBy,
																										  	  false);
			
			sr = session.createQuery(criteria).getResultList();


		}
		catch (Exception e)
		{
			
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			
		}finally {
			
			session.close();
		}
		
		return sr;

	}

	// update
	public void update(final StoreRecommendation sr) {
		
		final Session session = this.sessionFactory.openSession();

		Runnable run = () -> session.update(sr);
		
		DaoUtil.run(session, run);
	}

	/*
	 * HQL
	 */

}
