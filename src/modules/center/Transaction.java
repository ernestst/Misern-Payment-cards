package modules.center;

import java.io.Serializable;
import modules.bank.Bank;
import modules.bank.Card;
import modules.company.Company;

/**
 * The properties of transaction that were created in the company,contains the
 * object of company, object of the card, amount and date.
 * 
 * @author Micha³ Fi³oñczuk
 * @version 0.5
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 6376390646984773329L;
	private Bank bank;
	private Company comp;
	private Card card;
	private double amount;
	private String date;

	/**
	 * Standard way to create transaction object with filled fields
	 * @param comp object of the company where transaction was created
	 * @param card object of the card that was used in transation
	 * @param amount value of the transaction
	 * @param date date of the transaction
	 */
	public Transaction(Company comp, Bank bank, Card card, double amount, String date) {
		this.comp = comp;
		this.card = card;
		this.amount = amount;
		this.date = date;
		this.bank = bank;
	}
	
	public Transaction() {}

	/**
	 * Gets company connected with this transaction
	 * @return Company object
	 */
	public Company getComp() {
		return comp;
	}
	
	/**
	 * Sets company connected with this transaction
	 * @param comp - company object
	 */
	public void setComp(Company comp) {
		this.comp = comp;
	}

	/**
	 * Gets card connected with this transaction
	 * @return CreditCard, BankCard or DebitCard object
	 */
	public Card getCard() {
		return card;
	}
	
	/**
	 * Sets card connected with this transaction
	 * @param card - specified type of card to bind
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Gets amount of money used in this transaction
	 * @return double value
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * Sets amount of money used in this transaction
	 * @param amount - double value
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * Gets date of the transaction
	 * @return String date in format "YYYY-MM-DD"
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * Sets date of the transaction
	 * @param date - string in format "YYYY-MM-DD"
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets bank connected with this transaction
	 * @return Bank object
	 */
	public Bank getBank() {
		return bank;
	}
	
	/**
	 * Sets bank connected with this transaction
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	/**
	 * Converts object to string value
	 */
	public String toString() {
		if(card != null)
			return date + " (" + card.getNumber() + ") ";
		else 
			return "Brak transakcji do wyœwietlenia"; 
	}

}
