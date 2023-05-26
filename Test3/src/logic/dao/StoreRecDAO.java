package logic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	 * main
	 */
	public static void main(final String[] args) {

		final Map<String, Object> filters = new HashMap<>();

		final Map<String, SortOrder> sorts = new HashMap<>();
		filters.put("severity", logic.entity.Severity.INFO);
		// filters.put("description", "D");
		sorts.put("name", SortOrder.ASCENDING);
		// sorts.put("description", SortOrder.DESCENDING);

		final StoreRecDAO dao = new StoreRecDAO(SessionFactoryBean.createSessionFactory());

		// 1. test creiteria single
		final StoreRecommendation storeRecCriteria = dao.testFilterCriteria(85);
		System.out.println(storeRecCriteria.getName());

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
	 * getList
	 */
	public List<StoreRecommendation> getList(final int offset, final int pageSize, final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) {
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();
		// CriteriaAPI

		final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
				session, filters, sortBy, false);
		final Query<StoreRecommendation> query = session.createQuery(criteria);
		query.setFirstResult(offset);
		query.setMaxResults(pageSize);
		System.out.println("StoreRecDAO//getList.CriteriaAPI : ");
		final List<StoreRecommendation> results = query.getResultList();

		// Query<StoreRecommendation> query = null;
		// if (criteria)
		{

		}

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

		// create CriteriaQuery
		/*final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
				session, filters, null, true);
		// criteria
		return session.createQuery(criteria).getResultList().size(); */

		
		 // createHql -> Select / where / null 
		final String hql =  DaoUtil.createHql(StoreRecommendation.class, "sr", true, filters, null);
		  final Query<Long> query = session.createQuery(hql, Long.class);
		  DaoUtil.addQueryParams(query, filters);
		  final Long count = query.uniqueResult();
		  session.getTransaction().commit(); return count.intValue();
		 
	}

	// save
	public void save(final StoreRecommendation storeRecommendation) {
		DaoUtil.saveEntity(storeRecommendation, this.sessionFactory);
	}

	/*
	 * testFilterHQL
	 */

	public List<StoreRecommendation> testFilterHQL(final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) {

		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// createHql -> Select / where / SortBy
		final String hql = DaoUtil.createHql(StoreRecommendation.class, "sr", false, filters, sortBy);

		final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);

		// add params to query
		DaoUtil.addQueryParams(query, filters);

		final List<StoreRecommendation> storeRecommendations = query.list();

		session.getTransaction().commit();
		return storeRecommendations;
	}

	/*
	 * FilterCriteria -> Filter with id
	 */

	public StoreRecommendation testFilterCriteria(final long id) {

		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// session.get(StoreRecommendation.class, id);

		final CriteriaBuilder builder = session.getCriteriaBuilder();
		final CriteriaQuery<StoreRecommendation> criteria = builder.createQuery(StoreRecommendation.class);
		final Root<StoreRecommendation> root = criteria.from(StoreRecommendation.class);

		criteria.select(root).where(builder.equal(root.get("id"), id));

		final StoreRecommendation sr = session.createQuery(criteria).getSingleResult();

		return sr;
	}

	/*
	 * FilterCriteria -> Filter with Mapping
	 */

	public List<StoreRecommendation> testFilterCriteria(final Map<String, Object> filters,
			final Map<String, SortOrder> sortBy) {

		System.out.println("StoreRecDAO/testFilterCriteria");
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// create CriteriaQuery
		final CriteriaQuery<StoreRecommendation> criteria = DaoUtil.createCriteriaList(StoreRecommendation.class,
				session, filters, sortBy,false);
		final List<StoreRecommendation> sr = session.createQuery(criteria).getResultList();

		return sr;
	}

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
