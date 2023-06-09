package logic.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class StoreRecommendation implements Serializable 
{
	private static final long serialVersionUID = -3010187278193039324L;

	public StoreRecommendation(final String name, final String description, @NotNull final Severity severity,
			final boolean active,final int priority) {
		super();
		this.name = name;
		this.description = description;
		this.severity = severity;
		this.active = active;
		this.priority = priority;

	}

	// @NotNull
	private Integer id;

	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	@Pattern(regexp = ".{10,}", message = "description should not be less than 10")
	@Length(min = 10)
	// @WordCounter(min=3, minNoun=3)
	private String description;

	@NotNull
	private Severity severity;

	@NotNull
	private boolean active;

	@NotNull
	private Date timeCreated = new Date();

	@NotNull
	@Min(value = 0, message = "priority should not be less than 0")
	private int priority;

	public int getPriority() {
		return this.priority;
	}

	public void setPriority(final int priority) {
		this.priority = priority;
	}

	public StoreRecommendation() {

	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Severity getSeverity() {
		return this.severity;
	}

	public void setSeverity(final Severity severity) {
		this.severity = severity;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public Date getTimeCreated() {
		return this.timeCreated;
	}

	public void setTimeCreated(final Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	// Konstruktor, Getter und Setter

}
