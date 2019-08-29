package Testing;
import java.io.IOException;
import java.util.ArrayList;

import modules.Client;
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
import system.Database;
import system.exceptions.DataCardException;
public class Main {

	public static void main(String[] args) throws ClassNotFoundException, IOException, DataCardException {
	
			Center centrum = new Center();
			ArrayList<Bank> ba = new ArrayList<Bank>();
			ArrayList<Card> ca = new ArrayList<Card>();
			ArrayList<Client> cl = new ArrayList<Client>();
			
			ArrayList<Card> cards = new ArrayList<Card>();
			ArrayList<Bank> banks = new ArrayList<Bank>();
			
			Bank b1 = new Bank("AA"); banks.add(b1);
			ba.add(b1);
			Bank b2 = new Bank ("BB"); banks.add(b2);
			ba.add(b2);
			
			Company c3 = new TransportCompany("C");
			centrum.getCompanies().add(c3);
			Company c1 = new ShopCompany("A");
			centrum.getCompanies().add(c1);
			Company c2 = new ATMCompany("B");
			centrum.getCompanies().add(c2);
			
			Card cr1 = new CreditCard(123,"01/2008",10); ca.add(cr1); cards.add(cr1); cr1.setBalance(15);
			Card cr2 = new BankCard(333,"06/2019");ca.add(cr2); cards.add(cr1);	cr2.setBalance(20);
			Card cr3 = new DebitCard(444,"05/2012");ca.add(cr3); cards.add(cr3); cr3.setBalance(20);
			
			
			//poprawne
			//1- sprawdza czy karta kredytowa moze spaœæ ponizej  0, result: true
			Transaction t1 = new Transaction(c1,b1,cr1,17,"13-01-2001"); centrum.getArchive_transactions().add(t1);
			
			//2- sprawdza czy karta kredytowa moze przekroczyæ  limit, result: false
			Transaction t2 = new Transaction(c3,b2,cr1,26,"13-01-2001"); centrum.getArchive_transactions().add(t2);
			
			//3- sprawdza czy karta kredytowa odejmuje balance, result: wypisanie balance = 6
			Transaction t3 = new Transaction(c1,b1,cr1,9,"13-01-2001"); centrum.getArchive_transactions().add(t3);
			
			//4- sprawdza czy poprawny format daty, result: true
			Transaction t4 = new Transaction(c1,b2,cr1,9,"13-15-2001"); centrum.getArchive_transactions().add(t4);
			
			//5- sprawdza czy karta debetowa poprawnie odejmuje balance , result: true
			Transaction t5 = new Transaction(c3,b1,cr3,15,"02-01-2008"); centrum.getArchive_transactions().add(t5);

			//6- sprawdza czy karta debetowa poprawnie odejmuje balance , result: false
			Transaction t6 = new Transaction(c1,b1,cr3,31,"02-01-2008"); centrum.getArchive_transactions().add(t6);
			
			//7- sprawdza czy  bankomat poprawnie odejmuje balance  0, result: true
			Transaction t7 = new Transaction(c2,b1,cr2,16,"02-01-2008"); centrum.getArchive_transactions().add(t7);
			
			//8- sprawdza czy  bankomat moze przekroczyæ  0, result: false
			Transaction t8 = new Transaction(c2,b1,cr2,59,"02-01-2008"); centrum.getArchive_transactions().add(t8);
			
			//9-sprawdza  konflikt karty i firmy result: true
			Transaction t9 = new Transaction(c2,b1,cr2,0,"02-05-2001"); centrum.getArchive_transactions().add(t9);
			
			//10-sprawdza  konflikt karty i firmy result:  false
			Transaction t10 = new Transaction(c1,b1,cr2,0,"02-05-2001"); centrum.getArchive_transactions().add(t10);
			
			//11-sprawdza  czy poprawna data(karta nie wygas³a)  result: true
			Transaction t11 = new Transaction(c1,b1,cr1,0,"02-05-2001"); centrum.getArchive_transactions().add(t11);
			
			//12-sprawdza czy poprawna data (karta wygas³a) result:  false
			Transaction t12 = new Transaction(c2,b2,cr2,0,"02-07-2019"); centrum.getArchive_transactions().add(t12);
			
			//13-sprawdza czy poprawna data (z³y format) result: false
			Transaction t13 = new Transaction(c1,b1,cr1,0,"02-02-2008"); centrum.getArchive_transactions().add(t13);
			
			//14-sprawdza czy poprawna data (z³y, format) result:  false
			Transaction t14 = new Transaction(c1,b1,cr3,0,"02-05-2019"); centrum.getArchive_transactions().add(t14);
			
			Database database = new Database(); 
		//zapisywanie//	
			database.SUser(centrum.getUsers());
			database.SCompany(centrum.getCompanies());
			database.SBank(ba);
			database.SCard(ca);
			database.SClient(cl);
			database.STransaction(centrum.getArchiveTransactions());
			
			System.out.print("test 1: "); System.out.println(centrum.handleTransaction(t10));

	}
}


