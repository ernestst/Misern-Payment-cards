package modules.company;
/**
 * Another type of center client
 * @author Micha³ Fi³oñczuk
 */
public class ATMCompany extends Company {
	private static final long serialVersionUID = -2560703902509172127L;
	/**
	 * Standard way to create ATM Company
	 * @param id
	 * @param name
	 */
	public ATMCompany(String name) {
		super(name);
	}
}
