package site.beans;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import logic.dao.StoreRecDAO;
import logic.entity.Severity;
import logic.entity.StoreRecommendation;
import site.model.LazyStoreRecDataModelDAO;

@Named(value="lazyViewBean")
@ViewScoped
public class LazyViewBean implements Serializable {
	

	private static final long serialVersionUID = 1649123465069154870L;
	
	/*
	 * properties
	 */
	
	private LazyDataModel<StoreRecommendation> lazyModel;
	
	private StoreRecommendation selectedItem;
	
	private StoreRecommendation createItem = new StoreRecommendation();
	
	@Inject
	@ManagedProperty(value="#{sessionFactoryBean.sessionFactory}")
	private SessionFactory sessionFactory;
	
	/*
	 * constructor / init 
	 * 
	 * 
	 * 
	 * nach der Konstruktion eines Managed Beans ausgeführt wird
	 */
	
    @PostConstruct
    public void init() {
    	 StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);

    	 this.lazyModel = new LazyStoreRecDataModelDAO(storeRecDAO);
    	 
    }
    
    /*
     * method
     */
    
    public void onRowSelect(SelectEvent<StoreRecommendation> event) {
        FacesMessage msg = new FacesMessage("Customer Selected", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	
	/*
     * save
     */
    

public void saveItempar(final StoreRecommendation sr) {
    	
    	System.out.print("RecommendationBean.saveItem with id x: " + sr.getId());	

    		StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);
    		
    			try {
	    				if(sr.getId() == 0){
							StoreRecommendation recommendation = new StoreRecommendation(sr.getName(),
								sr.getDescription(),
								sr.getSeverity(),
								sr.isActive(),
								sr.getPriority());
							storeRecDAO.save(recommendation);	 
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
	    				}else{
	    	    			storeRecDAO.update(sr);
	    	    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product updated"));
	    	    		}
					
				} catch (Exception e) {
					 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getLocalizedMessage()));
			         System.out.print("RecommendationBean.saveItem.ERORR" + e.getMessage()); 
				}
    			
    			// reset
    			if (sr == createItem)
    				createItem = new StoreRecommendation();
  
    } 
    
    
    /*
     * delete
     */
    public void deleteItem(){
    	try {
    			StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);
    			storeRecDAO.delete(this.selectedItem);
    	        this.selectedItem = null;
    	        
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted"));
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("RecommendationBean.deleteItem.ERORR" + e.getMessage()); 
		}
    }
    
	
    /*
     * getter and setter
     */

	public LazyDataModel<StoreRecommendation> getLazyModel() {
		return lazyModel;
	}

	public StoreRecommendation getSelectedItem()
	{
		return selectedItem;
	}

	public void setSelectedItem(StoreRecommendation selectedItem)
	{
		this.selectedItem = selectedItem;
	} 

	  public StoreRecommendation getCreateItem()
		{
			return createItem;
		}


}
