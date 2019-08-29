package modules.bank;


import java.io.IOException;
import java.io.Serializable;

import system.exceptions.DataCardException;
/**
 * Abstract Class Card represents Account Card
 * @author Ernest Stachelski
 * @version 0.5
 *
 */
public abstract class Card implements Serializable{

	private static final long serialVersionUID = -5117663770048812196L;
	protected int number, lim;
	protected long id;
	protected double balance;
	protected String exp_date;
	protected boolean blocked = false;
	public abstract int getLimit();
	
	/**
	 * Standard Constructor
	 * @param n represents Card name
	 * @param e represents expiration date
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws DataCardException 
	 */
	public Card(int n, String e) throws DataCardException{
		for(int i = 0; i < e.length(); i++) {
			if(!Character.isDigit(e.charAt(i)) && e.charAt(i) != '-')
				throw new DataCardException(); 
		}
		
		if(e.length() != 7)
			throw new DataCardException();
		
		String [] exploded = e.split("-");
		if(exploded[0].length() != 4 || (exploded[1].length() != 2 || Integer.parseInt(exploded[1]) > 12))
			throw new DataCardException();
		
		this.number=n;
		this.exp_date=e;
	}
	
	/**
	 * Getter Card/Account Balance
	 * @return double Balance
	 */
	public double getBalance() {
		return balance;
	}
	
	/**
	 * Setter Card Balance
	 * @param e represents money send to Card
	 */
	public void setBalance(double e) {
		this.balance = e;
	}
	
	/**
	 * Getter Card Number
	 * @return int number
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Getter Expiration Date
	 * @return String Expiration Date
	 */
	public String getExp_date() {
		return exp_date;
	}
	/**
	 * Blocks Card
	 */
	public void blockCard() {
		blocked = true;
	}
	
	/**
	 * Checks if card is blocked
	 * @return true if card is blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}
	
	/**
	 * Getter Card Id
	 * @return int Id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Setter for card number
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
	/**
	 * Converts object to string value
	 */
	public String toString() {
		return number + ", " + exp_date; 
	}

	/**
	 * Getter for exp date
	 * @param exp_date
	 */
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
}
