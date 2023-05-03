package logic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;



public class StoreRecommendation {


	    public StoreRecommendation(String name, String description, @NotNull Severity severity, boolean active) {
		super();
		this.name = name;
		this.description = description;
		this.severity = severity;
		this.active = active;
		
	}

	    @NotNull
		private int id;
	    
	    @NotNull
	    @Column(unique = true)
	    private String name;
	    
	    @NotNull
	    private String description;  
	    
		@NotNull
	    private Severity severity;
	    
		@NotNull
	    private boolean active;
		
		@NotNull
	    private Date timeCreated=new Date();

	    public StoreRecommendation(){
	    	
	    }
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Severity getSeverity() {
			return severity;
		}

		public void setSeverity(Severity severity) {
			this.severity = severity;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public Date getTimeCreated() {
			return timeCreated;
		}

		public void setTimeCreated(Date timeCreated) {
			this.timeCreated = timeCreated;
		}

	    
	    // Konstruktor, Getter und Setter
	    
	    

}
