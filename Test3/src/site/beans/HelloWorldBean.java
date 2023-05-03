package site.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named(value="helloworldBean")
@RequestScoped
public class HelloWorldBean {
	
	private String name;

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
