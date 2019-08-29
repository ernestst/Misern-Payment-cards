package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import modules.bank.Bank;
import modules.bank.BankCard;
import modules.bank.Card;
import modules.bank.CreditCard;
import modules.bank.DebitCard;
import modules.center.Transaction;
import modules.company.ATMCompany;
import modules.company.Company;
import modules.company.ShopCompany;
import system.exceptions.DataCardException;
/**
 * Test class Bank
 * @author Ernest Stachelski
 *
 */
class Bank_Testing {
/**
 * Testing Bank constructor
 * @throws DataCardException
 */
	@Test
	void contructor_test() throws DataCardException {
		try {
			Bank bank = new Bank("Nazwa");
			assertNotNull(bank);
			assertEquals(bank.getName(),"Nazwa");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
/**
 * Test checkTransaction
 * @throws DataCardException
 * @throws ClassNotFoundException
 * @throws IOException
 */
	@Test
	void CheckTransaction_test() throws DataCardException, ClassNotFoundException, IOException {
		Company comp = new ShopCompany("Nazwa");
		Company ATM = new ATMCompany("Nazwa");
		Bank bank = new Bank("Nazwa");
		Card dcard = new DebitCard(1,"2001-11");
		Card bcard = new BankCard(1,"2001-11");
		Card ccard = new CreditCard(1,"2001-11",10);
		Transaction tran1 = new Transaction(comp, bank, dcard, 0, "2001-01-01");
		Transaction tran2 = new Transaction(ATM, bank, bcard, 0, "2001-01-01");
		Transaction tran3 = new Transaction(comp, bank, ccard, 10, "2001-01-01");
		Transaction tran4 = new Transaction(comp, bank, dcard, 0, "2001-01-01");
		Transaction tran5 = new Transaction(comp, bank, dcard, 1, "2001-01-01");
		Transaction tran6 = new Transaction(comp, bank, bcard, 0, "2001-01-01");
		Transaction tran7 = new Transaction(comp, bank, ccard, 1, "2001-01-01");
		Transaction tran8 = new Transaction(comp, bank, ccard, 0, "2001-11-01");
		
		
		assertEquals(bank.checkTransaction(tran1),true);
		assertEquals(bank.checkTransaction(tran2),true);
		assertEquals(bank.checkTransaction(tran3),true);
		assertEquals(bank.checkTransaction(tran4),true);
		assertEquals(bank.checkTransaction(tran5),true);
		assertEquals(bank.checkTransaction(tran6),false);
		assertEquals(bank.checkTransaction(tran7),false);
		assertEquals(bank.checkTransaction(tran8),false);
	}

}
