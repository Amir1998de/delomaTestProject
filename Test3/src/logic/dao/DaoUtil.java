package logic.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.SortOrder;

public class DaoUtil {
	public DaoUtil() {
	}

	public static String createHqlSelect(final Class<?> entityClass, final String alias, final boolean count) {
		return "SELECT " + (count ? "COUNT(*)" : alias) + " FROM " + entityClass.getSimpleName() + " AS " + alias;
	}

	public static String createHqlWhere(final Map<String, Object> filters, final String alias) {
		String hql = "";
		// add filter to hql
		if (filters != null && !filters.isEmpty()) {
			final StringJoiner sj = new StringJoiner(" AND ", " WHERE ", "");
			for (final Entry<String, Object> filter : filters.entrySet()) {
				final String field = filter.getKey();
				final Object value = filter.getValue();
				if (value != null)
					if (value instanceof String) {
						sj.add(alias + "." + field + " LIKE CONCAT(" + DaoUtil.createParamName(field, true) + ", '%')");
					} else {
						sj.add(alias + "." + field + " = " + DaoUtil.createParamName(field, true));
					}

			}
			hql += sj.toString();
			System.out.println("StoreRecDAO//createHQL.hql : " + hql);
		}
		return hql;
	}

	public static <T> void addQueryParams(final Query<T> query, final Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			for (final Entry<String, Object> entry : params.entrySet()) {

				final String field = entry.getKey();
				final Object value = entry.getValue();
				if (value != null) {
					query.setParameter(DaoUtil.createParamName(field, false), value);
					System.out.println("StoreRecDAO//addQueryParams : " + value);

				}
			}
		}
	}

	public static String createHqlOrderBy(final Map<String, SortOrder> sortBy, final String alias) {

		String hql = "";
		// sort
		if (sortBy != null && !sortBy.isEmpty()) {
			final StringJoiner sj = new StringJoiner(" , ", " ORDER BY ", "");
			for (final Entry<String, SortOrder> sort : sortBy.entrySet()) {
				final String field = sort.getKey();
				final String value = DaoUtil.sortOrderConvertor(sort.getValue());
				System.out.println("StoreRecDAO//createSortHQL.value : " + value);
				sj.add(alias + "." + field + " " + value);
			}
			hql += sj.toString();
		}

		return hql;

	}

	public static <T> String createHql(final Class<T> entityClass, final String alias, final boolean count,
			final Map<String, Object> filters, final Map<String, SortOrder> sortBy) {

		// SELECT
		String hql = DaoUtil.createHqlSelect(entityClass, alias, count);
		// WHERE
		hql += DaoUtil.createHqlWhere(filters, alias);
		// ORDER BY
		hql += DaoUtil.createHqlOrderBy(sortBy, alias);

		return hql;
	}

	public static String sortOrderConvertor(final SortOrder s) {
		if (s.equals(SortOrder.ASCENDING)) {
			return "ASC";
		} else if (s.equals(SortOrder.DESCENDING)) {
			return "DESC";
		} else {
			return "";
		}
	}

	public static String createParamName(final String field, final boolean colon) {
		return (colon ? ":" : "") + "param_" + field;
	}

	/*
	 * createCriteriaWhere
	 */
	public static <T> Predicate createCriteriaWhere(final CriteriaBuilder builder, final Root<T> root,
			final Map<String, Object> filters) {

		final List<Predicate> predicates = new ArrayList<>();
		for (final Map.Entry<String, Object> entry : filters.entrySet()) {
			final String field = entry.getKey();
			final Object value = entry.getValue();
			// predicates.add(builder.equal(root.get(field), value));

			if (value instanceof String) {
				predicates.add(builder.like(root.get(field), "%" + value + "%"));
			} else {
				predicates.add(builder.equal(root.get(field), value));
			}

		}

		// (name = "test") AND (description = "p")
		final Predicate predicateAnd = builder.and(predicates.toArray(new Predicate[0]));
		return predicateAnd;
	}

	/*
	 * createCriteriaOrderBy
	 */
	public static <T> List<Order> createCriteriaOrderBy(final CriteriaBuilder builder,
			final Map<String, SortOrder> sortBy, final Root<T> root) {
		// sortBy
		final List<Order> orders = new ArrayList<>();
		if (sortBy != null && !sortBy.isEmpty()) {
			for (final Map.Entry<String, SortOrder> entry : sortBy.entrySet()) {
				final String field = entry.getKey();
				final SortOrder sortOrder = entry.getValue();

				// name, id
				final Expression<?> expression = root.get(field);

				final Order order = sortOrder == SortOrder.ASCENDING ? builder.asc(expression)
						: builder.desc(expression);
				orders.add(order);
			}
		}
		return orders;
	}

	public static <T> T getEntity(final Session session, final Class<T> entity, final long id) {
		return null;
	}

	/*
	 * createCriteria
	 */
	public static <T> CriteriaQuery<T> createCriteriaList(final Class<T> entityClass, final Session session,
			final Map<String, Object> filters, final Map<String, SortOrder> sortBy, final boolean count) {
		// False -> for List
		// True -> for count

		// create CriteriaQuery
		final CriteriaBuilder builder = session.getCriteriaBuilder();
		final CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		final Root<T> root = criteria.from(entityClass);

		DaoUtil.createCriteria(builder, criteria, root, filters, sortBy);

		// projections COUNT, SUM,
		// select
		// criteria.

		criteria.select(root);

		return criteria;
	}

	// createCriteriaRowCount

	public static <T> CriteriaQuery<T> createCriteria(final CriteriaBuilder builder, final CriteriaQuery<T> criteria,
			final Root<T> root, final Map<String, Object> filters, final Map<String, SortOrder> sortBy) {

		// where

		criteria.where(DaoUtil.createCriteriaWhere(builder, root, filters));

		// Order
		List<Order> orders = new ArrayList<>();
		orders = DaoUtil.createCriteriaOrderBy(builder, sortBy, root);
		if (!orders.isEmpty()) {
			criteria.orderBy(orders);
		}

		return criteria;
	}

	// save
	public static void saveEntity(final Object entity, final SessionFactory sf) {
		System.out.print("StoreRecommendationDAO.save");
		final Session session = sf.openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();

	}

}
