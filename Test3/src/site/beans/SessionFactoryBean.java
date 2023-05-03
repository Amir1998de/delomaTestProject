package site.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Named(value="sessionFactoryBean")
@ApplicationScoped
public class SessionFactoryBean {

	private SessionFactory sessionFactory;
	
	public SessionFactoryBean()
	{
		System.out.println("SessionFactoryBean.constructor");
    	this.sessionFactory = SessionFactoryBean.createSessionFactory();
	}

    
    
    public static SessionFactory createSessionFactory()
    {
    	try {
     	    Configuration configuration = new Configuration().configure();
     	    

     	    System.out.println("sessionFactory created");
     	    return configuration.buildSessionFactory();
     	    
             
         } catch (Throwable ex) {
             System.err.println("Initialisierung der SessionFactory fehlgeschlagen: " + ex);
             throw new ExceptionInInitializerError(ex);
         } 
    }
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
}
