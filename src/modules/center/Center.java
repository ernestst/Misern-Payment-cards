package modules.center;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import modules.Client;
import modules.bank.Bank;
import modules.company.ATMCompany;
import modules.company.Company;
import modules.company.ServiceCompany;
import modules.company.ShopCompany;
import modules.company.TransportCompany;
import system.Database;
import system.exceptions.DataCardException; 

/**
 * Class contains the list of all transactions ,center users and banks.
 * @author Micha³ Fi³oñczuk
 * @version 0.5
 */
public class Center {
	private ArrayList<Bank> banks;
	private ArrayList<Transaction> archive_transactions;
	private ArrayList<User> users;
	private ArrayList<Company> companies;
	private Company loggedCompany;
	private Bank loggedBank; 
	
	private transient Database database = new Database(); 

	/**
	 * Standard way to create Center object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Center() throws ClassNotFoundException, IOException {
		this.users = database.RUser(); 
		this.companies = database.RCompany(); 
		
		try {
			this.banks = database.RBank();
			this.archive_transactions = database.RTransaction(); 
		} catch (DataCardException e) {
			e.printStackTrace();
		} 
		
		this.loggedBank = null;
		this.loggedCompany = null;
	}
	
	/**
	 * Gets banks list
	 * @return ArrayList complete banks list
	 */
	public ArrayList<Bank> getBanks() {
		return banks;
	}
	
	/**
	 * Gets companies list 
	 * @return ArrayList complete companies list
	 */
	public ArrayList<Company> getCompanies() {
		return companies;
	}

	/**
	 * Gets archived transactions list
	 * @return ArrayList complete transaction list
	 */
	public ArrayList<Transaction> getArchiveTransactions() {
		return archive_transactions;
	}
	
	/**
	 * Gets users list 
	 * @return ArrayList complete users list
	 */
	public ArrayList<User> getUsers() {
		return users;
	}

	/**
	 * Adds new user to the list
	 * 
	 * @param login - String login of the user
	 * @param password - String unencrypted password of the user
	 * @param rights - String one option of: bank/company/admin
	 * @param orgName - String Bank or Company name 
	 */
	public void addUser(String login, String password, String rights, String orgName) {
		User user = new User(login, password, rights, orgName);
		users.add(user);
	}
	
	/**
	 * Adds new bank to the list
	 * @param name - String name of new bank
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataCardException 
	 */
	public void addBank(String name) {
		Bank bank = null;
		bank = new Bank(name); 
		
		banks.add(bank);
	}
	
	/**
	 * Adds new company to the list 
	 * @param name - String name of the company
	 * @param type - String type of the company matches type in GUI
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void addCompany(String name, String type) {
		Company company = null; 
		if(type.equals("Us³ugi"))
			company = new ServiceCompany( name);
		else if(type.equals("Sklep"))
			company = new ShopCompany( name);
		else 
			company = new TransportCompany( name);
		
		companies.add(company); 
	}

	/**
	 * Deletes user from the list
	 * @param login - String login of the user to delete
	 */
	public void deleteUser(String login) {
		Iterator<User> iter = users.iterator();
		
		while (iter.hasNext()) {
		    User user = iter.next();

		    if (user.getLogin().equals(login))
		        iter.remove();
		}
	}
	
	/**
	 * Deletes bank from the list 
	 * @param name - String name of the bank to delete
	 */
	public void deleteBank(String name) {
		Iterator<Bank> iter = banks.iterator();
		
		while (iter.hasNext()) {
		    Bank bank = iter.next();

		    if (bank.getName().equals(name))
		        iter.remove();
		}
	}
	
	/**
	 * Deletes company from the list
	 * @param name - String name of the company to delete
	 */
	public void deleteCompany(String name) {
		Iterator<Company> iter = companies.iterator();
		
		while (iter.hasNext()) {
		    Company comp = iter.next();

		    if (comp.getName().equals(name))
		        iter.remove();
		}
	}
	
	/** 
	 * Updates one record from companies list
	 * @param name - String new name of the company
	 * @param type - String new type of the company (that matches one of the types from GUI)
	 * @param order - Integer index of the record in list
	 */
	public void editCompany(String name, String type, int order) {
		
		int i = 0; 
		ListIterator<Company> listIterator = companies.listIterator();
		
		while (listIterator.hasNext()) {
			listIterator.next();

		    if (i == order) {	    	
		    	if(type.equals("Sklep"))
		    		listIterator.set(new ShopCompany( name)); 
				else if(type.equals("Us³ugi"))
					listIterator.set(new ServiceCompany( name));
				else if(type.equals("Transport"))
					listIterator.set(new TransportCompany( name));
				else
					listIterator.set(new ATMCompany( name));
		    }
		    
		    i++;
		}
	}
	
	/**
	 * Updates one record from users list
	 * @param login - String new login of the user
	 * @param password - String new unencrypted pass of the user
	 * @param role - String new role of the user (that matches one of the role from GUI)
	 * @param order - Integer index of the user in list 
	 */
	public void editUser(String login, String password, String role, String orgName, int order) {
		int i = 0; 
		ListIterator<User> listIterator = users.listIterator();
		
		while (listIterator.hasNext()) {
			listIterator.next();

		    if (i == order) {	    	
		    	listIterator.set(new User(login, password, role, orgName)); 
		    }
		    
		    i++;
		}
	}
	
	/**
	 * Updates one record from banks list
	 * @param name - String new name of the bank
	 * @param order - Integer index of the bank in list
	 * @throws DataCardException 
	 */
	public void editBank(String name, int order) throws DataCardException {
		int i = 0; 
		ListIterator<Bank> listIterator = banks.listIterator();
		
		while (listIterator.hasNext()) {
			listIterator.next();

		    if (i == order) {	    	
		    	listIterator.set(new Bank(name)); 
		    }
		    
		    i++;
		}
	}

	/**
	 * Passes transaction to the bank;
	 * @author Micha³ Fi³oñczuk
	 * @param trans - transaction object to check
	 * @return boolean true - if transaction is accepted, false - if transaction is rejected
	 */
	public boolean handleTransaction(Transaction trans) {
		Bank bank = trans.getBank();
		return bank.checkTransaction(trans);
	}

	/**
	 * Returns the list of the transactions according to entered query
	 * 
	 * @param query string - properties of transaction you looking for, FORMAT: Company:Bank:Card:amount:owner
	 * where: company is the name of company, bank is the name of the bank, 
	 * card is card number, amount is amount:),date is date of the transaction(FORMAT:DD-MM-RRRR)
	 * @return returns the requested transactions form the archives
	 */
	public ArrayList<Transaction> getArchivedTransaction(String query) {
		
		String[] array = query.split(";");
		String company_name = array[0];
		String bank_name = array[2];
		String amount = array[4];
		String card_number = array[6];
		String owner = array[8];
		
		String con1 = array[1];
		String con2 = array[3]; 
		String con3 = array[5]; 
		String con4 = array[7]; 
		
		if(company_name.equals("-")||company_name.equals("Firma") || company_name.equals("") || company_name.equals(" ")) {
			company_name = "-";
		}
		
		if(bank_name.equals("-")||bank_name.equals("Bank") || bank_name.equals("") || bank_name.equals(" ")) {
			bank_name = "-";
		}
		
		if(card_number.equals("-")||card_number.equals("Numer karty") || card_number.equals("") || card_number.equals(" ")) {
			card_number = "0";
		}
		
		if(amount.equals("-")||amount.equals("Kwota transakcji") || amount.equals("") || amount.equals(" ")) {
			amount = "0";
		}
		
		if(owner.equals("-")||owner.equals("Imiê i nazwisko w³aœciciela") || owner.equals("") || owner.equals(" ")) {
			owner = "-";
		}
		
		ArrayList<Transaction> transactionsToReturn = new ArrayList<Transaction>();
		for (Transaction trans : archive_transactions) {
			boolean cond1 = false;
			boolean cond2 = false;
			boolean cond3 = false;
			boolean cond4 = false;
			boolean cond5 = false;
			
			boolean flag = false; 
			
			if(trans.getComp().getName().equals(company_name) || company_name.equals("-"))
				cond1 = true; 
			
			if(trans.getBank().getName().equals(bank_name) || bank_name.equals("-"))
				cond2 = true; 
			
			if(trans.getCard().getNumber() == Integer.parseInt(card_number) || card_number.equals("0"))
				cond3 = true; 
			
			if(trans.getAmount() == Integer.parseInt(amount) || amount.equals("0"))
				cond4 = true; 
			
			if(owner.equals("-"))
				cond5 = true; 
			else 
				for(Bank bank : banks) 
					for(Client client : bank.getClients())
						if((client.getName() + " " + client.getSurname()).equals(owner) && client.hasCard(Integer.parseInt(card_number))) {
							cond5 = true;
							break; 
						}
			
			if(con1.equals("LUB"))
				flag = cond1 || cond2; 
			else 
				flag = cond1 && cond2; 
			
			if(con2.equals("LUB"))
				flag = flag || cond3; 
			else 
				flag = flag && cond3; 
			
			if(con3.equals("LUB"))
				flag = flag || cond4; 
			else 
				flag = flag && cond4; 
			
			if(con4.equals("LUB"))
				flag = flag || cond5; 
			else 
				flag = flag && cond5; 
			
			if(flag) 
				transactionsToReturn.add(trans); 
		}
		
		return transactionsToReturn;
	}

	/**
	 * Gets actual logged company
	 * @return Company 
	 */
	public Company getLoggedCompany() {
		return loggedCompany;
	}

	/**
	 * Sets actual logged company
	 * @param loggedCompany Company
	 */
	public void setLoggedCompany(Company loggedCompany) {
		this.loggedCompany = loggedCompany;
	}

	/**
	 * Gets actual logged bank
	 * @return Bank
	 */
	public Bank getLoggedBank() {
		return loggedBank;
	}

	/**
	 * Sets actual logged bank
	 * @param loggedBank Bank
	 */
	public void setLoggedBank(Bank loggedBank) {
		this.loggedBank = loggedBank;
	}

	/**
	 * Sets list of banks for center
	 * @param banks ArrayList of all banks
	 */
	public void setBanks(ArrayList<Bank> banks) {
		this.banks = banks;
	}
	
	/**
	 * Gets full list of archived transactions 
	 * @return ArrayList of transactions
	 */
	public ArrayList<Transaction> getArchive_transactions() {
		return archive_transactions;
	}
	
	/**
	 * Sets list of archived transactions
	 * @param archive_transactions ArrayList of transactions
	 */
	public void setArchive_transactions(ArrayList<Transaction> archive_transactions) {
		this.archive_transactions = archive_transactions;
	}
}
