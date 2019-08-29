package Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modules.company.ATMCompany;
import modules.company.Company;
import modules.company.ServiceCompany;
import modules.company.ShopCompany;
import modules.company.TransportCompany;

class Company_Testing {

	@Test
	void ATM_constructor_test() {
		Company ATM = new ATMCompany("Bankomat");
		assertNotNull(ATM);
		assertEquals(ATM.getName(),"Bankomat");
	}
	@Test
	void Service_constructor_test() {
		Company SERV = new ServiceCompany("Bankomat");
		assertNotNull(SERV);
		assertEquals(SERV.getName(),"Bankomat");
	}
	@Test
	void Trans_constructor_test() {
		Company TR = new TransportCompany("Bankomat");
		assertNotNull(TR);
		assertEquals(TR.getName(),"Bankomat");
	}
	@Test
	void Shop_constructor_test() {
		Company SHOP = new ShopCompany("Bankomat");
		assertNotNull(SHOP);
		assertEquals(SHOP.getName(),"Bankomat");
	}

}
