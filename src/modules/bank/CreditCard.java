package modules.bank;

import system.exceptions.DataCardException;

/**
 * Class CreditCard represents CreditCards
 * @author Ernest Stachelski
 * @version 0.5
 */
public class CreditCard extends Card {
	
	private static final long serialVersionUID = 7223565725063040658L;
	private int limit;
	
	/** 
	 * @see Card
	 */
	public CreditCard(int n, String e, int li) throws DataCardException {
		super(n, e);
		this.limit = li;
	}
	
	/**
	 * Getter int Limit 
	 */
	public int getLimit() {
		return limit;
	}
}
