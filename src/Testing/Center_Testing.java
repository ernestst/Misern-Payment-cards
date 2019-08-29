package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modules.bank.Bank;
import modules.bank.BankCard;
import modules.bank.Card;
import modules.bank.CreditCard;
import modules.bank.DebitCard;
import modules.center.Center;
import modules.center.Transaction;
import modules.company.ATMCompany;
import modules.company.Company;
import modules.company.ShopCompany;
import modules.company.TransportCompany;
import system.exceptions.DataCardException;

class Center_Testing {

	@Test
	void constructor_test() throws ClassNotFoundException, IOException {
		
		
		Center center = new Center();
		assertNotNull(center);	
	}

	/**
	 * Testing handleTransaction
	 * @author Ernest Stachelski
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataCardException
	 */
	@Test 
	void handleTransaction_test() throws ClassNotFoundException, IOException, DataCardException {
		Center center = new Center();
		Company comp = new ShopCompany("Nazwa");
		Company ATM = new ATMCompany("Nazwa");
		Bank bank = new Bank("Nazwa");
		Bank bank2 = new Bank("Nazwa");
		Card dcard = new DebitCard(1,"2011-11");
		Card bcard = new BankCard(2,"2011-11");
		Card ccard = new CreditCard(3,"2011-11",10);
		Card cccard = new CreditCard(4,"2011-11",12);
		Transaction tran1 = new Transaction(comp, bank, dcard, 0, "2011-01-01");
		Transaction tran2 = new Transaction(ATM, bank, bcard, 0, "2011-01-01");
		Transaction tran3 = new Transaction(comp, bank, ccard, 10, "2011-01-01");
		Transaction tran4 = new Transaction(comp, bank, dcard, 0, "2011-11-01");
		Transaction tran5 = new Transaction(comp, bank, dcard, 1, "2011-01-01");
		Transaction tran6 = new Transaction(comp, bank, bcard, 0, "2011-01-01");
		Transaction tran7 = new Transaction(comp, bank, ccard, 20, "2011-01-01");
		Transaction tran8 = new Transaction(comp, bank2, dcard, 0, "2011-01-01");
		Transaction tran9 = new Transaction(comp, bank2, cccard, 5, "2012-01-01");
		assertEquals(center.handleTransaction(tran1),true);
		assertEquals(center.handleTransaction(tran2),true);
		assertEquals(center.handleTransaction(tran3),true);
		assertEquals(center.handleTransaction(tran4),false);
		assertEquals(center.handleTransaction(tran5),false);
		assertEquals(center.handleTransaction(tran6),false);
		assertEquals(center.handleTransaction(tran7),false);
		assertEquals(center.handleTransaction(tran8),false);
		assertEquals(center.handleTransaction(tran9),false);
	}
	
	@Test
	void getArchivedTransaction_Test() throws ClassNotFoundException, IOException, DataCardException
	{
		Center center = new Center();
		Company comp = new ShopCompany("Nazwa");
		Company ATM = new ATMCompany("Nazwa1");
		Bank bank = new Bank("Nazwa");
		Bank bank2 = new Bank("Nazwa2");
		Card dcard = new DebitCard(1,"2011-11");
		Card bcard = new BankCard(2,"2011-11");
		Card ccard = new CreditCard(3,"2011-11",10);
		Transaction tran1 = new Transaction(comp, bank, dcard, 0, "2011-01-01"); center.getArchive_transactions().add(tran1);
		Transaction tran2 = new Transaction(ATM, bank, bcard, 0, "2011-01-01"); center.getArchive_transactions().add(tran2);
		Transaction tran3 = new Transaction(comp, bank, ccard, 10, "2011-01-01"); center.getArchive_transactions().add(tran3);
		Transaction tran4 = new Transaction(comp, bank, dcard, 0, "2011-11-01"); center.getArchive_transactions().add(tran4);
		Transaction tran5 = new Transaction(comp, bank, dcard, 1, "2011-01-01"); center.getArchive_transactions().add(tran5);
		Transaction tran6 = new Transaction(comp, bank, bcard, 0, "2011-01-01"); center.getArchive_transactions().add(tran6);
		Transaction tran7 = new Transaction(comp, bank, ccard, 20, "2011-01-01"); center.getArchive_transactions().add(tran7);
		Transaction tran8 = new Transaction(comp, bank2, dcard, 0, "2011-01-01"); center.getArchive_transactions().add(tran8);
		center.getArchivedTransaction("-;AND;-;AND;-;AND;-;AND;-");
	}
}
