package logic.test;


import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import logic.dao.StoreRecDAO;
import logic.entity.StoreRecommendation;

public class MainClass {
	private static SessionFactory sessionFactory;
	
	
	public static void main(String[] args) {
		
		  try {
	            // Erstelle die Hibernate-Konfiguration
	            Configuration configuration = new Configuration().configure();
	            sessionFactory = configuration.buildSessionFactory();
	            StoreRecDAO recommendationDAO = new StoreRecDAO(sessionFactory);

	            //** Erstelle eine neue StoreRecommendation-Instanz
	          /*  StoreRecommendation recommendation = new StoreRecommendation();
	            recommendation.setName("Backup2");
	            recommendation.setDescription("Dies ist ein ERORR für Backup2");
	            recommendation.setSeverity(Severity.ERROR);
	            recommendation.setActive(false);
	            recommendation.setTimeCreated(new Date());
	            recommendationDAO.save(recommendation);  */

	  
	            //** Aktualisiere die StoreRecommendation
	          /* recommendation.setDescription("Dies ist ein aktualisierter empfohlener Artikel");
	            recommendationDAO.update(recommendation);*/

	            
	           //**  Lese alle StoreRecommendations aus der Datenbank
	             List<StoreRecommendation> recommendations = recommendationDAO.getAll();
	            for (StoreRecommendation r : recommendations) {
	                System.out.println(r.getName() + " :   "+ r.getDescription() + " :    "+ r.getTimeCreated());
	               if(r.getName().equals("name1")){
	                	  
	                	 // System.out.println("result : " + recommendationDAO.delete(r)); 
	            	   r.setDescription("Hallo2");
	            	   System.out.println("result : " + recommendationDAO.update(r));
	                }
	                
	            } 

	            
	            // dao.getList(...)
	            
	            
	            
	            // Lösche die StoreRecommendation aus der Datenbank
	          //   recommendationDAO.delete(1);

	            // Schließe die SessionFactory
	            sessionFactory.close();

	            System.out.println("Hibernate-Verbindung erfolgreich");
	        } catch (Throwable ex) {
	            System.err.println("Initialisierung der SessionFactory fehlgeschlagen: " + ex);
	            throw new ExceptionInInitializerError(ex);
	        }

	}

}
