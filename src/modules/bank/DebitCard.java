package modules.bank;
import java.io.IOException;

import system.exceptions.DataCardException;

/**
 * Class DebitCard represents card with creditworthiness
 * @author Ernest Stachelski
 * @version 0.5
 */
public class DebitCard extends Card {

	private static final long serialVersionUID = 3662954371351192965L;

	/**
	 * Standard constructor
	 * @param n represents Card number
	 * @param e represents Card expiration date
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataCardException 
	 */
	public DebitCard(int n, String e) throws DataCardException {
		super(n, e);
	}
	
	/**
	 * Getter Limit in BankCard
	 * Limit in credit card is 0
	 */
	@Override
	public int getLimit() {
		return 0;
	}

}
