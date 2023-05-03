package site.model;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;
import site.util.ShowcaseUtil;

public class LazySorter implements Comparator<StoreRecommendation>{
	
	 private String sortField;
	 private SortOrder sortOrder;

	    public LazySorter(String sortField, SortOrder sortOrder) {
	        this.sortField = sortField;
	        this.sortOrder = sortOrder;
	    }

	    public int compare(StoreRecommendation StoreRecommendation1, StoreRecommendation StoreRecommendation2) {
	        try {
	            Object value1 = ShowcaseUtil.getPropertyValueViaReflection(StoreRecommendation1, sortField);
	            Object value2 = ShowcaseUtil.getPropertyValueViaReflection(StoreRecommendation2, sortField);

	            int value = String.valueOf(value1).compareToIgnoreCase(String.valueOf(value2));
	            
	            // // ((Comparable) value1).compareTo(value2);

	            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
	        }
	        catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }

}
