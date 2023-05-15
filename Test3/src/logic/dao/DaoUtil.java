package logic.dao;

import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

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
					sj.add(alias + "." + field + " LIKE CONCAT(" + DaoUtil.createParamName(field, true) + ", '%')");
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

	// save
	public static void saveEntity(final Object entity, final SessionFactory sf) {
		System.out.print("StoreRecommendationDAO.save");
		final Session session = sf.openSession();
		session.beginTransaction();
		session.save(entity);
		session.getTransaction().commit();

	}

}
