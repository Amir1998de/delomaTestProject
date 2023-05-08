package logic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;

public class StoreRecDAO {

	/*
	 * properties
	 */

	public static Map<String, FilterMeta> filterByMap(final String field, final String filterValue) {
		final Map<String, FilterMeta> filterBy = new HashMap<>();
		if (filterValue != null && !filterValue.isEmpty()) {
			final FilterMeta filter = new FilterMeta();
			filter.setFilterValue(filterValue);
			filter.setMatchMode(org.primefaces.model.MatchMode.STARTS_WITH);
			filterBy.put(field, filter);
		}

		return filterBy;

	}

	/*
	 * main
	 */
	public static void main(final String[] args) {

		final Map<String, Object> filters = new HashMap<>();
		filters.put("name", "f");
		filters.put("description", "a");

		final StoreRecDAO dao = null;

		final List<StoreRecommendation> storeRecs = dao.testFilterHQL(filters);

		// sysstem out
	}

	/*
	 * method
	 */

	private final SessionFactory sessionFactory;

	/*
	 * constructor
	 */
	public StoreRecDAO(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

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
			final Map<String, SortMeta> sortBy) {
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// SELECT + WHERE
		String hql = this.createHQL(filters, false);

		// ORDER BY
		hql += StoreRecDAO.createHQL(sortBy);

		final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);
		// add params to query
		StoreRecDAO.addQueryParams(query, filters);

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
	 */
	public int getRowCount(final Map<String, Object> filters) {
		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		// SELECT + WHERE
		final String hql = this.createHQL(filters, true);

		final Query<Long> query = session.createQuery(hql, Long.class);

		StoreRecDAO.addQueryParams(query, filters);

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

	public String createHQL(final Map<String, Object> filters, final boolean count) {

		String hql = "SELECT " + (count ? "COUNT(*)" : "sr") + " FROM StoreRecommendation AS sr ";

		// add filter to hql
		if (filters != null && !filters.isEmpty()) {

			final StringJoiner sj = new StringJoiner(" AND ", " WHERE ", "");

			for (final Entry<String, Object> filter : filters.entrySet()) {

				final String field = filter.getKey();
				final Object value = filter.getValue();

				if (value != null) {
					// sj.add("sr." + field + " LIKE " + "'" + value +
					// "%'");
					sj.add("sr." + field + " LIKE ?param0");

				}
			}

			hql += sj.toString();

			// query = session.createQuery(hql).setParameter("param0", value +
			// "%");
		}

		return hql;
	}

	public List<StoreRecommendation> testFilterHQL(final Map<String, Object> filters) {

		final Session session = this.sessionFactory.openSession();
		session.beginTransaction();

		final String hql = this.createHQL(filters, false);

		// a) HQL Query
		final Query<StoreRecommendation> query = session.createQuery(hql, StoreRecommendation.class);

		// add params to query

		StoreRecDAO.addQueryParams(query, filters);

		final List<StoreRecommendation> storeRecommendations = query.list();

		session.getTransaction().commit();
		return storeRecommendations;
	}

	public StoreRecommendation testFilterCriteria(final long id) {
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

	public static <T> void addQueryParams(final Query<T> query, final Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			int index = 0;
			for (final Entry<String, Object> entry : params.entrySet()) {

				final Object value = entry.getValue();
				if (value != null) {
					query.setParameter("param" + index, value + "%");
					index++;
				}
			}
		}
	}

	public static String createHQL(final Map<String, SortMeta> sortBy) {

		String hql = "";
		// sort
		if (sortBy != null && !sortBy.isEmpty()) {
			final StringJoiner sj = new StringJoiner(" , ", " ORDER BY ", "");
			for (final String fieldName : sortBy.keySet()) {
				final SortMeta sortMeta = sortBy.get(fieldName);
				sj.add("sr." + sortMeta.getField() + " " + StoreRecDAO.sortOrderConvertor(sortMeta.getOrder()));
			}
			hql += sj.toString();
		}

		return hql;
	}

	public static String sortOrderConvertor(final SortOrder s) {
		if (s.equals(SortOrder.ASCENDING)) {
			return "ASC";
		} else if (s.equals(SortOrder.DESCENDING)) {
			return "DESc";
		} else {
			return "";
		}
	}
}
