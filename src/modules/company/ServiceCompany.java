package modules.company;

/**
 * One type of the centrum client
 * @author Micha³ Fi³oczuk
 *
 */
public class ServiceCompany extends Company {
	private static final long serialVersionUID = 4554123860689917120L;
	/**
	 * Standard way to create service company
	 * @param id long -number id of the company
	 * @param name String - name of the Company
	 */
	public ServiceCompany(String name) {
		super(name);
	}


}
