package logic.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import logic.entity.StoreRecommendation;

import org.hibernate.Session;
import java.util.List;

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
	    
	    public List<StoreRecommendation> getList(int offset, int pageSize)
	    {
	    	
	    	return null;
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
	    

