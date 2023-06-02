package site.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
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

import logic.dao.StoreRecDAO;
import logic.entity.Severity;
import logic.entity.StoreRecommendation;


@Named(value="recommendationBean")
@ViewScoped
public class RecommendationBean implements Serializable {
	
	/*
	 * constants
	 */

	private static final long serialVersionUID = 3967972460997193756L;

	/*
	 * properties
	 */
	
	@Inject
	@ManagedProperty(value="#{sessionFactoryBean.sessionFactory}")
	private SessionFactory sessionFactory;
	
	private List<StoreRecommendation> items = null;
    private StoreRecommendation selectedItem;
    private String testname = "hallo";

	
	
	/*
	 * constructor
	 */

	public RecommendationBean() {
    	System.out.println("RecommendationBean.constructor");
    }
	
	/*
	 * init
	 */
	    
    @PostConstruct
    public void init()
    {
    	 System.out.print("RecommendationBean.init");
    	 StoreRecDAO recommendationDAO = new StoreRecDAO(this.sessionFactory);
         this.items = recommendationDAO.getAll();
    }

    
    /*
     * method
     */
    

    
	public List<StoreRecommendation> getItems() {
		System.out.print("RecommendationBean.getItems: size: " + this.items.size());
		return this.items;
			
	}

	
	public void openNew() {
		System.out.print("RecommendationBean.openNew");	
		this.selectedItem = new StoreRecommendation();
		
	}
    public void saveItem() {
    	System.out.print("RecommendationBean.saveItem");	
    	
    		StoreRecDAO recommendationDAO = new StoreRecDAO(this.sessionFactory);
    		
    			try {
	    				if(selectedItem.getId() == 0){
							StoreRecommendation recommendation = new StoreRecommendation (selectedItem.getName(),
																						  selectedItem.getDescription(),
																						  selectedItem.getSeverity(),
																						  selectedItem.isActive(),
																						  selectedItem.getPriority());
							recommendationDAO.save(recommendation);	 
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Added"));
							PrimeFaces.current().ajax().update("pageMessages");
							PrimeFaces.current().executeScript("PF('manageProductDialogWidgetVar').hide()");
	    				}else{
	    	    			recommendationDAO.update(this.selectedItem);
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
    
    public void deleteItem(){
    	try {
    			StoreRecDAO recommendationDAO = new StoreRecDAO(this.sessionFactory);
    			recommendationDAO.delete(this.selectedItem);
    	        this.selectedItem = null;
    	        
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product deleted"));
                PrimeFaces.current().ajax().update("pageMessages");
                PrimeFaces.current().executeScript("PF('manageProductDialogWidgetVar').hide()");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("RecommendationBean.deleteItem.ERORR" + e.getMessage()); 
		}
    	 init();
    }
    

    
    


	public void test(StoreRecommendation st) {
		
		System.out.print("Test  " + st.getName());
		System.out.print("Test");
		System.out.print("name :  " + selectedItem.getName());
		System.out.print("Description :  " + selectedItem.getDescription());
		System.out.print("Severity :  " + selectedItem.getSeverity());
		System.out.print("Active :  " + selectedItem.isActive());
		}

	
	/*
	 * getter setter
	 */
	
    public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public StoreRecommendation getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(StoreRecommendation selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	
    public List<Severity> getSeverity() {
        return Arrays.asList(Severity.values());
    } 

  

}
