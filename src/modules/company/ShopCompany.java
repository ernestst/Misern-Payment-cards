package modules.company;

/**
 * One type of the centrum client
 * @author Micha³ Fi³oñczuk
 *
 */
public class ShopCompany extends Company {
	private static final long serialVersionUID = 4490159945656242158L;
	/**
	 * Standard way to create shop company
	 * @param id long- id of the company
	 * @param name String -name of the company
	 */
	public ShopCompany(String name) {
		super(name);
	}

}
