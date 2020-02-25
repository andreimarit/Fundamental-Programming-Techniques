package businessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable{
	

	private static final long serialVersionUID = 1L;
	protected String name;
	protected String description;
	
	public MenuItem() {}
	
	public MenuItem(String name, String description) {
		this.name = name;
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	abstract public int computePrice();
	

}
