package modules.company;

import java.io.Serializable;

/**
 * Company is client of the Center. Represents the real firm which cooperate with center.
 * @author Micha³ Fi³oñczuk 
 * @version 0.1
 */

public abstract class Company implements Serializable {
	private static final long serialVersionUID = -6331595473685706122L;
	protected String name;

	/**
	 * Standard way to create User object
	 * @param id - long ID number of the Company
	 */
	public Company( String name) {
		this.name = name;
	}
	/**
	 * Getter of the name of the company
	 * @return name of the company
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter of the name of the company
	 * @param name new name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
}
