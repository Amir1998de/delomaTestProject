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
	
	private StoreRecommendation selectedRecommendation;
	
	@Inject
	@ManagedProperty(value="#{sessionFactoryBean.sessionFactory}")
	private SessionFactory sessionFactory;
	
	/*
	 * constructor / init 
	 * 
	 * nach der Konstruktion eines Managed Beans ausgeführt wird
	 */
	
    @PostConstruct
    public void init() {
    	 StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);
    	 /*
    	  * send a List<StoreRecommendation>
    	  *
          * this.lazyModel = new LazyStoreRecDataModelMemory(storeRecDAO.getAll()) ; xxx
          */
    	 
    	 this.lazyModel = new LazyStoreRecDataModelDAO(storeRecDAO);
    	 
    }
    
    /*
     * method
     */
    
    public void onRowSelect(SelectEvent<StoreRecommendation> event) {
        FacesMessage msg = new FacesMessage("Customer Selected", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
	public void openNew() {
		System.out.print("RecommendationBean.openNew");	
		this.selectedRecommendation = new StoreRecommendation();
	}
	
	/*
     * save
     */
    public void saveItem() {
    	System.out.print("RecommendationBean.saveItem");	
    	
    		StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);
    		
    			try {
	    				if(selectedRecommendation.getId() == 0){
							StoreRecommendation recommendation = new StoreRecommendation(selectedRecommendation.getName(),
									selectedRecommendation.getDescription(),
									selectedRecommendation.getSeverity(),
									selectedRecommendation.isActive(),
									selectedRecommendation.getPriority());
							storeRecDAO.save(recommendation);	 
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
							PrimeFaces.current().ajax().update("pageMessages");
							PrimeFaces.current().executeScript("PF('manageProductDialogWidgetVar').hide()");
	    				}else{
	    	    			storeRecDAO.update(this.selectedRecommendation);
	    	    			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product updated"));
	    	                PrimeFaces.current().ajax().update("pageMessages");
	    	                PrimeFaces.current().executeScript("PF('manageProductDialogWidgetVar').hide()");
	    	    		}
					
				} catch (Exception e) {
					 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Error"));
			         PrimeFaces.current().ajax().update("formDialog:dialogMessages");
			         System.out.print("RecommendationBean.saveItem.ERORR" + e.getMessage()); 
				}
    			
    			init();
    }
    
    
    /*
     * delete
     */
    public void deleteItem(){
    	try {
    			StoreRecDAO storeRecDAO = new StoreRecDAO(this.sessionFactory);
    			storeRecDAO.delete(this.selectedRecommendation);
    	        this.selectedRecommendation = null;
    	        
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted"));
                PrimeFaces.current().ajax().update("pageMessages");
                PrimeFaces.current().executeScript("PF('manageProductDialogWidgetVar').hide()");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("RecommendationBean.deleteItem.ERORR" + e.getMessage()); 
		}
    	 init();
    }
    
	
    /*
     * getter and setter
     */

	public LazyDataModel<StoreRecommendation> getLazyModel() {
		return lazyModel;
	}

	public StoreRecommendation getSelectedRecommendation() {
		return selectedRecommendation;
	}

	public void setSelectedRecommendation(StoreRecommendation selectedRecommendation) {
		this.selectedRecommendation = selectedRecommendation;
	}
	
    public List<Severity> getSeverity() {
        return Arrays.asList(Severity.values());
    } 

	

}
