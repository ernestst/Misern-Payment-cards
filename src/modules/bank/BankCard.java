package modules.bank;
import java.io.IOException;

import system.exceptions.DataCardException;

/**
 * Class BankCard represents card that can be used in ATM 
 * @author Ernest Stachelski
 * @version 0.5
 */
public class BankCard extends Card {

	private static final long serialVersionUID = -2433695691883632353L;

	/**
	 * Standard constructor
	 * @param n represents Card number
	 * @param e represents Card expiration date
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataCardException 
	 */
	public BankCard(int n, String e) throws DataCardException {
		super(n, e);
	}

	/**
	 * Getter Limit in BankCard
	 * Limit in Bank Card is 0
	 */
	@Override
	public int getLimit() {
		return 0;
	}
}
