package modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;

import modules.bank.Card;

/**
 * Class represents specified bank customer
 * @author Ernest Stachelski
 * @version 0.5
 */
public class Client implements Serializable {

	private static final long serialVersionUID = 1805754444123030417L;
	private String pesel;
	private String name, surname;
	private ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * Standard constructor 
	 * @param n represents client name
	 * @param s represents client surname
	 * @param p represents client PESEL
	 */
	public Client(String n, String s, String p) {
		this.name = n;
		this.surname = s;
		this.pesel = p;
	}
	
	/**
	 * Adds card for client 
	 * @param c - card object that will be bind with Client
	 */
	public void addCard(Card c) {
		cards.add(c);
	}

	/**
	 * Removes client card
	 * @param c - card object that will be unbind from Client
	 */
	public void deleteCard(Card c) {
		cards.remove(c);
	}
	
	/**
	 * Removes client card
	 * @param number - index from the list of Client's cards to delete
	 */
	public void deleteCard(int number) throws IOException, ClassNotFoundException {
		Iterator<Card> iter = cards.iterator();
		while (iter.hasNext()) {
		    Card card = iter.next();

		    if (card.getNumber() == number) {
		    	 iter.remove();
		    }
		}
	}

	/**
	 * Getter PESEL
	 * @return string PESEL
	 */
	public String getPesel() {
		return pesel;
	}
	
	/**
	 * Getter Name
	 * @return string Client Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter Surname
	 * @return string Client Surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Overridden method that converts object to string
	 */
	public String toString() {
		return name + " " + surname + " " + pesel;
	}
	
	/**
	 * Checks if client own specified card
	 * @param card - card object to find
	 * @return owner flag
	 */
	public boolean hasCard(Card card) {
		for(Card card_i : cards) {
			if(card_i == card)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Gets all cards list from client
	 * @return
	 */
	public ArrayList<Card> getCards() {
		return cards; 
	}
	
	/**
	 * Checks if client own specified card
	 * @param number - card's number to find
	 * @return owner flag
	 */
	public boolean hasCard(int number) {
		for(Card card_i : cards) {
			if(card_i.getNumber() == number)
				return true;
		}
		
		return false;
	}

	/**
	 * Sets new pesel value 
	 * @param pesel - string with pesel
	 */
	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	/**
	 * Sets new name 
	 * @param name - string with new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets new surname 
	 * @param surname - string with new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
