package modules.bank;

import java.io.Serializable;
import java.util.ArrayList;

import modules.Client;
import modules.center.Transaction;
import modules.company.ATMCompany;
import system.exceptions.DataCardException;
import system.exceptions.PersonDoesntExistException;

/**
 * Class represents specified Bank object 
 * @author Micha³ Fi³oñczuk
 * @version 0.5
 */
public class Bank implements Serializable {
	private static final long serialVersionUID = 1933279111153341142L;
	private String name;
	private ArrayList<Client> clients;

	/**
	 * Standard Bank constructor
	 * @param name
	 * @throws DataCardException
	 */
	public Bank(String name) {
		this.name = name;
		
		this.clients = new ArrayList<Client>();
		clients.add(new Client("Jan", "Kowalski", "98053002654"));
	}
	
	/**
	 * Adds Client to local list
	 * @param client represents Object Client
	 */
	public void addClient(Client client) {
		clients.add(client);
	}
	
	/**
	 * Deletes Client with specified PESEL
	 * @param pesel - String represents clients PESEL
	 * @throws PersonDoesntExistException
	 */
	public void delete_Client(String pesel) throws PersonDoesntExistException {
		int index = 0;
		for (Client k : clients) {
			if (k.getPesel() == pesel) {
				index = clients.indexOf(k);
			}
			
			if (clients.indexOf(k) == clients.size() - 1 && clients.get(index).getPesel() != pesel) {
				throw new PersonDoesntExistException();
			}
		}

		clients.remove(index);
	}

	/**
	 * Transaction service
	 * @param trans Transaction - object to execute 
	 * @return boolean true - if transaction is accepted, false - if transaction is rejected 
	 * @author Ernest Stachelski & Micha³ Fi³oñczuk
	 */
	public boolean checkTransaction(Transaction trans) {
		
		if(trans.getCard().isBlocked()) return false; // checking if card is blocked
		
		if(trans.getCard().getBalance() - trans.getAmount() < (-1) * trans.getCard().getLimit() && trans.getCard() instanceof CreditCard) return false;
				
		if(trans.getCard() instanceof BankCard) {
			if(!(trans.getComp() instanceof ATMCompany)) {
				return false;
			}
		}
		
		String now = trans.getDate();
		
		String [] explited = now.split("-"); 
		int year = Integer.parseInt(explited[0]); 
		int month = Integer.parseInt(explited[1]); 
		
		
		explited = trans.getCard().getExp_date().split("-"); 
		int year_exp = Integer.parseInt(explited[0]); 
		int month_exp = Integer.parseInt(explited[1]); 
		
		
		if(year > year_exp) {
			trans.getCard().blockCard();
			return false;
		}
		
		if(year == year_exp) {
			if(month >= month_exp) {
				trans.getCard().blockCard();
				return false;
			} 
		}
		
		trans.getCard().setBalance(trans.getCard().getBalance() - trans.getAmount());
		return true;
	}
	

	/**
	 * Getter Bank Name
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter Bank Name
	 * @param name represents Bank name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter List of Clients
	 * @return ArrayList Clients
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}
	
	/**
	 * Setter List of Clients
	 * @param clients represents Clients List
	 */
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}
}
