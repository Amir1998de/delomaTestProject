package site.model;

import java.beans.IntrospectionException;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

import logic.entity.StoreRecommendation;
import site.util.ShowcaseUtil;

import javax.faces.context.FacesContext;




public class LazyStoreRecDataModelMemory extends LazyDataModel<StoreRecommendation>{
	
	private static final long serialVersionUID = 9222784293154869519L;
	
	/*
	 * properties
	 */

	private List<StoreRecommendation> datasource;
	
	/*
	 * constructor 
	 */
	 public LazyStoreRecDataModelMemory(List<StoreRecommendation> datasource) {
	        this.datasource = datasource;
	    }
	 
	 /*
	  * methods
	  */
	@Override
	public StoreRecommendation getRowData(String rowKey) {
		 for (StoreRecommendation storeRecommendation : datasource) {
	            if (storeRecommendation.getId() == Integer.parseInt(rowKey)) {
	                return storeRecommendation;
	            }
	        }

	        return null;
	}

	@Override
	public String getRowKey(StoreRecommendation storeRecommendation) {
		 return String.valueOf(storeRecommendation.getId());
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
	
		return datasource.size();
	}
	
	
	 /*
	  * load
	  */
	
	@Override
	public List<StoreRecommendation> load(int offset, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		 
		
		System.out.println("datasource.size: " + datasource.size());
		
		 /*
		  * filters and paginatorTemplate control
		  */
		
		List<StoreRecommendation> storeRecommendation = datasource.stream()
					.filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
					.skip(offset)
					.limit(pageSize)
					.collect(Collectors.toList());
		
		
		 /*
		  * sort
		  */
	
	     
	     if (!sortBy.isEmpty()) {
	            List<Comparator<StoreRecommendation>> comparators = sortBy.values().stream()
	                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
	                    .collect(Collectors.toList());
	            Comparator<StoreRecommendation> cp = ComparatorUtils.chainedComparator(comparators); // from apache
	            storeRecommendation.sort(cp);
	        }

		
        return storeRecommendation;
	}
	
	
	 /*
	  * filter
	  */
    public boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
        boolean matching = true;

        for (FilterMeta filter : filterBy) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();

            try {
                Object columnValue = String.valueOf(ShowcaseUtil.getPropertyValueViaReflection(o, filter.getField()));
                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
            }
            catch (ReflectiveOperationException | IntrospectionException e) {
                matching = false;
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }
   
	

}
