package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import modules.bank.Bank;
import modules.bank.Card;
import modules.bank.CreditCard;
import modules.bank.DebitCard;
import modules.center.Transaction;
import modules.company.Company;
import modules.company.ShopCompany;
import system.exceptions.DataCardException;

class Transaction_Test {

	@Test
	void constructor_Test() throws DataCardException, ClassNotFoundException, IOException {
		Company comp = new ShopCompany("Biedronka");
		Card card = new DebitCard(1434,"2001-01");
		Bank bank = new Bank("PKOO");
		Transaction trans = new Transaction(comp,bank,card,12,"2001-02-01");
		assertNotNull(trans);
		assertEquals(trans.getComp(),comp);
		assertEquals(trans.getComp().getName(),"Biedronka");

		assertEquals(trans.getBank(),bank);
		assertEquals(trans.getBank().getName(),"PKOO");
		assertEquals(trans.getCard().getNumber(),1434);
		assertEquals(trans.getCard().getExp_date(),"2001-01");
		assertEquals(trans.getCard().getLimit(),0);
		assertEquals(trans.getAmount(),12);
		assertEquals(trans.getDate(),"2001-02-01");

		

		
		
	}

}
