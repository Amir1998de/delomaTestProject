package logic.dao;

import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;

public class DaoUtil
{
	public DaoUtil()
	{
	}
	
	public static String createHqlSelect(Class<?> entityClass, final String alias, final boolean count) {
		return "SELECT " + (count ? "COUNT(*)" : alias ) + " FROM "+ entityClass.getSimpleName() +" AS "+  alias;
	} 
	
	public static String createHqlWhere(final Map<String, Object> filters, String  alias) 
	{
		String hql="";
			// add filter to hql
			if (filters != null && !filters.isEmpty()) {
				final StringJoiner sj = new StringJoiner(" AND ", " WHERE ", "");
				for (final Entry<String, Object> filter : filters.entrySet()) {
					final String field = filter.getKey();
					final Object value = filter.getValue();
					if (value != null)
						sj.add(alias+"."+ field + " LIKE CONCAT(" + createParamName(field, true) + ", '%')");
				}
				hql += sj.toString();
				System.out.println("StoreRecDAO//createHQL.hql : " + hql);
			}
			return hql;
		}
	
	public static <T> void addQueryParams(final Query<T> query, final Map<String, Object> params) 
	{
		if (params != null && !params.isEmpty()) 
		{
			for (final Entry<String, Object> entry : params.entrySet()) {

				String field = entry.getKey();
				final Object value = entry.getValue();
				if (value != null) {
				    query.setParameter(createParamName(field, false), value);
					System.out.println("StoreRecDAO//addQueryParams : " + value);
	
				}
			}
		}
	}
	
	
	public static String createHqlOrderBy(final Map<String, SortOrder> sortBy,String alias) {

		String hql = "";
		// sort
		if (sortBy != null && !sortBy.isEmpty()) {
			final StringJoiner sj = new StringJoiner(" , ", " ORDER BY ", "");
			for (final Entry<String, SortOrder> sort : sortBy.entrySet()) {
				final String field = sort.getKey();
				final String value = sortOrderConvertor(sort.getValue());
				System.out.println("StoreRecDAO//createSortHQL.value : " + value);
				sj.add(alias + "." + field + " " + value);
			}
			hql += sj.toString();
		}

		return hql;

	}
	
	public static String createHql(Class<?> entityClass, final String alias, final boolean count,final Map<String, Object> filters,
		final Map<String, SortOrder> sortBy)
	{

		// SELECT 
		String hql =createHqlSelect(entityClass, alias, count);
		//  WHERE
		hql += DaoUtil.createHqlWhere(filters,alias);
		// ORDER BY
	    hql += DaoUtil.createHqlOrderBy(sortBy,alias);
	    
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
	
	public static String createParamName(String field, boolean colon)
	{
		return (colon ? ":" : "") + "param_" + field;
	}

}
