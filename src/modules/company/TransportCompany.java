package modules.company;

/**
 * One type of the Company
 * @author Micha³ Fi³oñczuk
 *
 */
public class TransportCompany extends Company  {
	private static final long serialVersionUID = -8785668440816308231L;
	/**
	 * Standard way to transport company
	 * @param id long -id of the company
	 * @param name String- name of the company
	 */
	public TransportCompany( String name) {
		super(name);
	}

}
