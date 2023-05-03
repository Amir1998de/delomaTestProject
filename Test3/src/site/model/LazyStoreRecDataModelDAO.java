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

	private StoreRecDAO dao;
	
	/*
	 * constructor 
	 */
	 public LazyStoreRecDataModelDAO(StoreRecDAO dao) {
	        this.dao = dao;
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
	
		throw new UnsupportedOperationException();
	}
	
	
	 /*
	  * load
	  */
	
	@Override
	public List<StoreRecommendation> load(int offset, int pageSize, Map<String, SortMeta> sortBy,
			Map<String, FilterMeta> filterBy) {
		 
		List<StoreRecommendation> storeRecommendation = this.dao.getList(offset, pageSize);
		
		// this.dao.getAll()
		
		System.out.println("storeRecommendation.size: " + storeRecommendation.size());
		
		 /*
		  * filters and paginatorTemplate control
		  */
		

		return storeRecommendation;
		
	}
	
	
   
	

}
