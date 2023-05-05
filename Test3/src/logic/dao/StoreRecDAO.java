package logic.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import logic.entity.StoreRecommendation;

import org.hibernate.Session;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class StoreRecDAO {
	
		/*
		 * properties
		 */
	
	   private SessionFactory sessionFactory;
	   
	   /*
	    * constructor 
		*/
	    public StoreRecDAO(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;  
	    }
	    
	    /*
	     * method
	     */
	    
	    // save
	    public void save(StoreRecommendation storeRecommendation) {
	    	System.out.print("StoreRecommendationDAO.save");	
	        Session session = sessionFactory.openSession(); 
	        session.beginTransaction();
	        session.save(storeRecommendation);
	        session.getTransaction().commit();
	    }
	    
	    /*
	     * read
	     */
	    
	    /**
	     *  getAll
	     * @return
	     */
	    public List<StoreRecommendation> getAll() {
	    
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        List<StoreRecommendation> storeRecommendations = session.createQuery("FROM StoreRecommendation",StoreRecommendation.class).list();
	        session.getTransaction().commit();
	        return storeRecommendations;
	    }
	    
	    public int getRowCount(Map<String, FilterMeta> filterBy)
	    {
	    	  Session session = sessionFactory.openSession();
		        session.beginTransaction();
		        String hql = "SELECT sr. FROM StoreRecommendation AS sr ";
		       // String hql = "FROM StoreRecommendation ORDER BY description  ASC ";
		       
		        
		        
		        if (filterBy != null && !filterBy.isEmpty()) {
		        	
		        	StringJoiner sj = new StringJoiner(" AND ", " WHERE ", "");
		        	
		        	
		            for (String fieldName : filterBy.keySet()) {
		            	// for example description
		                FilterMeta filter = filterBy.get(fieldName);
		                
		                String field = filter.getField();
		                Object value = filter.getFilterValue();
		                
		                try {
		                	  if (value != null) {
		  
		                            sj.add("sr." + field + " LIKE " + "'" +value + "%'");
		                    }
						} catch (Exception e) {
							 throw new IllegalArgumentException("Unknown filter match mode: " + filter.getMatchMode());
						}
		              
		                }
		            
		            hql += sj.toString();
		            }
	    	return 0;
	    	
	    }
	    
	    /*
	     * getList
	     */
	    public List<StoreRecommendation> getList(int offset, int pageSize,Map<String, FilterMeta> filterBy,Map<String, 
	    		SortMeta> sortBy)
	    {
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        String hql = "SELECT sr FROM StoreRecommendation AS sr ";
	       // String hql = "FROM StoreRecommendation ORDER BY description  ASC ";
	        String searchName = "abcd";
	        
	       
	     
	        // filter 
	        
	        if (filterBy != null && !filterBy.isEmpty()) {
	        	
	        	StringJoiner sj = new StringJoiner(" AND ", " WHERE ", "");
	        	
	        	
	            for (String fieldName : filterBy.keySet()) {
	            	// for example description
	                FilterMeta filter = filterBy.get(fieldName);
	                
	                String field = filter.getField();
	                Object value = filter.getFilterValue();
	                
	                try {
	                	  if (value != null) {
	  
	                            sj.add("sr." + field + " LIKE " + "'" +value + "%'");
	                    }
					} catch (Exception e) {
						 throw new IllegalArgumentException("Unknown filter match mode: " + filter.getMatchMode());
					}
	              
	                }
	            
	            hql += sj.toString();
	            }
	      
	        
	       // sort 
	       if (sortBy != null && !sortBy.isEmpty()) {
	    	   
	    	   StringJoiner sj = new StringJoiner(" , ", " ORDER BY ", "");
	    	   
	            for (String fieldName : sortBy.keySet()) {
	                SortMeta sortMeta = sortBy.get(fieldName);
	                
	                sj.add("sr." +  sortMeta.getField() + " " +  sortOrderConvertor(sortMeta.getOrder()));
	               
	            }
	            hql += sj.toString();
	        } 
	        
	        
	     // HQL * 
	        Query<StoreRecommendation> query =  session.createQuery(hql);
	        query.setFirstResult(offset);
	        query.setMaxResults(pageSize);
	        List<StoreRecommendation> results = query.list();
	       
	        session.getTransaction().commit();
	    	return results;
	    }
	    
	    String sortOrderConvertor(SortOrder s){
	    	if(s.equals(s.ASCENDING)){
	    		return "ASC";
	    	}else if(s.equals(s.DESCENDING)){
	    		return "DESc";
	    	}else{
	    		return "";
	    	}
        }
	    
	    /*
	     * delete
	     */
	    
	    /**
	     * delete
	     * @param sr
	     * @return
	     */
	    public boolean delete(StoreRecommendation sr) {
	    	boolean result = true;
	    	Session session = sessionFactory.openSession(); 
	    	
	    	try{
		    	session.beginTransaction();
		    	session.remove(sr);
			    session.getTransaction().commit();
	    		
	    	}catch (Exception e) {
	    		result = false;
				if (session.getTransaction() != null) {
					session.getTransaction().rollback();
				}
				
			}finally{
				 session.close();
			}
	       
	       
	        return result;
	    } 
	    
	    // update
	    public boolean update(StoreRecommendation sr){
	    	boolean result = true;
	    	Session session = sessionFactory.openSession(); 
	    	
	    	try{
		    	session.beginTransaction();
		    	session.update(sr);
			    session.getTransaction().commit();
	    		
	    	}catch (Exception e) {
	    		result = false;
				if (session.getTransaction() != null) {
					session.getTransaction().rollback();
				}
				
			}finally{
				 session.close();
			}
	       
	       
	        return result;
	    	
	    }
	    
	    

	    public List<StoreRecommendation> testFilterHQL() {
		    
	        Session session = sessionFactory.openSession();
	        session.beginTransaction();
	        
	        
	        String searchName = "abcd";
	        
	        
	        // a) HQL Query
	        Query<StoreRecommendation> query =  session.createQuery("FROM StoreRecommendation WHERE id = 1",StoreRecommendation.class);
	        
	        
	        List<StoreRecommendation> storeRecommendations = query.list();

	        
	        session.getTransaction().commit();
	        return storeRecommendations;
	    }

	    public List<StoreRecommendation> testFilterCriteria() {
			return null;
	     }
	    }
	    

