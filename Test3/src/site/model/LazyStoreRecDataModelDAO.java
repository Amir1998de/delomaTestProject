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

import logic.dao.StoreRecDAO;
import logic.entity.StoreRecommendation;
import site.util.ShowcaseUtil;

import javax.faces.context.FacesContext;




public class LazyStoreRecDataModelDAO extends LazyDataModel<StoreRecommendation>{
	
	private static final long serialVersionUID = 9222784293154869519L;
	
	/*
	 * properties
	 */

	private StoreRecDAO storeRecDAO;
	
	private List<StoreRecommendation> datasource;
	/*
	 * constructor 
	 */
	 public LazyStoreRecDataModelDAO(StoreRecDAO dao) {
	        this.storeRecDAO = dao;
	    }
	 
	 /*
	  * methods
	  */
	@Override
	public StoreRecommendation getRowData(String rowKey) {
		 throw new UnsupportedOperationException();
	}

	@Override
	public String getRowKey(StoreRecommendation storeRecommendation) {
		return String.valueOf(storeRecommendation.getId()); 
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return 100;
		
	}
	
	
	 /*
	  * load
	  * 
	  * First is offset = 0 and pageSize = 4 so it will show object with Index 0,1,2,3
	  * if I select next page in paginator, is offset = 4 and pageSize = 4  so it will show object with Index 4,5,6,7
	  * And it continues...
	  */
	
	@Override
	public List<StoreRecommendation> load(int offset, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		 
		   datasource = this.storeRecDAO.getList(offset, pageSize,filterBy,sortBy);
		 //	datasource = this.storeRecDAO.getAll();
		
		// this.dao.getAll()
		
		System.out.println("storeRecommendation.size: " + datasource.size());
		System.out.println("storeRecommendation.offset: " + offset +"storeRecommendation.pageSize: " + pageSize);
		System.out.println("storeRecommendation.filterBy: " + filterBy);
		System.out.println("storeRecommendation.sortBy: " + sortBy);
		
		 /*
		  * filters and paginatorTemplate control
		  */
		

		return datasource;
		
	}
	
	
   
	

}
