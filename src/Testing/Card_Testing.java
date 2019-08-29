package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import modules.bank.BankCard;
import modules.bank.CreditCard;
import modules.bank.DebitCard;
import system.exceptions.DataCardException;
import modules.bank.Card;
/**
 * Test class Card
 * @author Ernest Stachelski
 *
 */
class Card_Testing {
/**
 * Test BankCard constructor
 * @throws ClassNotFoundException
 * @throws IOException
 * @throws DataCardException
 */
	@Test
	void constructor_Bank_test() throws ClassNotFoundException, IOException, DataCardException {
		Card bcard = new BankCard(111,"2011-11");
		
		assertNotNull(bcard);
		assertEquals(bcard.getNumber(),111);
		assertEquals(bcard.getExp_date(),"2011-11");
	}
/**
 * Test CreditCard constructor
 * @throws ClassNotFoundException
 * @throws IOException
 * @throws DataCardException
 */
	@Test
	void constructor_Credit_test() throws ClassNotFoundException, IOException, DataCardException {
		Card ccard = new CreditCard(111,"2011-11",10);
		
		assertNotNull(ccard);
		assertEquals(ccard.getNumber(),111);
		assertEquals(ccard.getExp_date(),"2011-11");
		
	}
/**
 * Test DebitCard constructor	
 * @throws ClassNotFoundException
 * @throws IOException
 * @throws DataCardException
 */
	@Test
	void constructor_Debit_test() throws ClassNotFoundException, IOException, DataCardException {
		Card dcard = new DebitCard(111,"2011-11");
		
		assertNotNull(dcard);
		assertEquals(dcard.getNumber(),111);
		assertEquals(dcard.getExp_date(),"2011-11");
	}
/**
 * Test block card
 * @throws ClassNotFoundException
 * @throws IOException
 * @throws DataCardException
 */
	@Test
	void block_test() throws ClassNotFoundException, IOException, DataCardException {
		Card dcard = new DebitCard(111,"2011-11");
		dcard.blockCard();
		assertEquals(dcard.isBlocked(),true);
		
	}

}
