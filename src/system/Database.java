package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modules.center.User;
import modules.Client;
import modules.bank.Bank;
import modules.bank.Card;
import modules.bank.DebitCard;
import modules.center.Transaction;
import modules.company.Company;
import modules.company.ShopCompany;
import system.exceptions.DataCardException;

/**
 * Database works on project files
 * @author Ernest Stachelski
 * @version 0.04
 *
 */
public class Database {
	/**
	 * Reads Users file
	 * @return ArrayList Users
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<User> RUser() throws IOException, ClassNotFoundException {
		ArrayList<User> us = new ArrayList<User>();
		File f = new File("Users.bin");
		if (f.length() != 0) {
			FileInputStream fius = new FileInputStream(f); 
			ObjectInputStream ius = new ObjectInputStream(fius);
			us = (ArrayList<User>) ius.readObject();
			ius.close();
			fius.close();
		} else {
			User u = new User("admin", "admin", "Centrum", "None");
			us.add(u);
		}
		
		return us;
	}
	/**
	 * Reads Transactions file
	 * @return ArrayList Transactions
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Transaction> RTransaction() throws IOException, ClassNotFoundException{
		ArrayList <Transaction> tr = new ArrayList<Transaction>();
		File f= new File("Transaction.bin");
		if(f.length()!=0) {
			FileInputStream fitr = new FileInputStream(f);	//opening file with clients
			ObjectInputStream itr = new ObjectInputStream(fitr);  
			tr=(ArrayList<Transaction>)itr.readObject();	//copying clientgs to new array list
			itr.close();
			fitr.close();
		}
		else {
			Transaction c = new Transaction();
			tr.add(c);
		}
		return tr;
	}
	/**
	 * Reads Companies file
	 * @return ArrayList Companies
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Company> RCompany() throws IOException, ClassNotFoundException{
		ArrayList <Company> co = new ArrayList<Company>();
		File f= new File("Companies.bin");
		if(f.length()!=0) {
			FileInputStream fico = new FileInputStream(f);	//opening file with clients
			ObjectInputStream ico = new ObjectInputStream(fico);  
			co=(ArrayList<Company>)ico.readObject();	//copying clientgs to new array list
			ico.close();
			fico.close();
		}
		else {
			Company c = new ShopCompany( "Default");
			co.add(c);
		}
		return co;
	}
	/**
	 * Reads Banks file
	 * @return ArrayList Banks
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DataCardException
	 */
	public ArrayList<Bank> RBank() throws IOException, ClassNotFoundException, DataCardException{
		ArrayList <Bank> ba = new ArrayList<Bank>();
		File f= new File("Banks.bin");
		if(f.length()!=0) {
			FileInputStream fiba = new FileInputStream(f);	//opening file with clients
			ObjectInputStream iba = new ObjectInputStream(fiba);  
			ba=(ArrayList<Bank>)iba.readObject();	//copying clientgs to new array list
			iba.close();
			fiba.close();
		}
		else {
			Bank b = new Bank("Default");
			ba.add(b);
		}
		return ba;
	}
	/**
	 * Reads Clients file
	 * @return ArrayList Clients
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Client> RClient() throws IOException, ClassNotFoundException{
		ArrayList <Client> cl = new ArrayList<Client>();
		File f= new File("Clients.bin");
		if(f.length()!=0) {
			FileInputStream ficl = new FileInputStream(f);	//opening file with clients
			ObjectInputStream icl = new ObjectInputStream(ficl);  
			cl=(ArrayList<Client>)icl.readObject();	//copying clientgs to new array list
			icl.close();
			ficl.close();
		}
		else {
			Client c = new Client("Dummy", "Dummy", "98060402654");
			cl.add(c);
		}
		return cl;
	}
	/**
	 * Reads Clients file
	 * @return ArrayList Clients
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws DataCardException
	 */
	public ArrayList<Card> RCard() throws IOException, ClassNotFoundException, DataCardException{
		ArrayList <Card> ca = new ArrayList<Card>();
		File f= new File("Card.txt");
		if(f.length()!=0) {
			FileInputStream fica = new FileInputStream(f);	//opening file with cards
			ObjectInputStream ica = new ObjectInputStream(fica);  
			ca=(ArrayList<Card>)ica.readObject();	//copying cards to new array list
			ica.close();
			fica.close();
		}
		else {
			Card c= new DebitCard(0," ");
			ca.add(c);
		}
		return ca;
		
	}
	/**
	 * Saves User List
	 * @param u represents list of all users
	 * @throws IOException
	 */
	public void SUser(ArrayList<User> co) throws IOException {
		FileOutputStream foco = new FileOutputStream(new File("Users.bin"));
		ObjectOutputStream oco = new ObjectOutputStream(foco);
		oco.writeObject(co);	//rewritnig file with new client
		oco.close();
		foco.close();
	}
	/**
	 * Saves Companies List
	 * @param co represents list of all companies
	 * @throws IOException
	 */
	public void SCompany(ArrayList<Company> co) throws IOException {
		FileOutputStream foco = new FileOutputStream(new File("Companies.bin"));
		ObjectOutputStream oco = new ObjectOutputStream(foco);
		oco.writeObject(co);	//rewritnig file with new client
		oco.close();
		foco.close();
	}
	/**
	 * Saves Cleints List
	 * @param cl represents list of all clients
	 * @throws IOException
	 */
	public void SClient(ArrayList<Client> cl) throws IOException {
		FileOutputStream focl = new FileOutputStream(new File("Clients.bin"));
		ObjectOutputStream ocl = new ObjectOutputStream(focl);
		ocl.writeObject(cl);	//rewritnig file with new client
		ocl.close();
		focl.close();
	}
	/**
	 * Saves Banks List
	 * @param ba represents list of all banks
	 * @throws IOException
	 */
	public void SBank(ArrayList<Bank> ba) throws IOException{
		FileOutputStream foba = new FileOutputStream(new File("Banks.bin"));
		ObjectOutputStream oba = new ObjectOutputStream(foba);
		oba.writeObject(ba);	//rewritnig file with new client
		oba.close();
		foba.close();
	}

	/**
	 * Saves Cards List
	 * @param ca represents list of all cards
	 * @throws IOException
	 */
	public void SCard(ArrayList<Card> ca) throws IOException {
		FileOutputStream foca = new FileOutputStream(new File("Card.bin"));
		ObjectOutputStream oca = new ObjectOutputStream(foca);
		oca.writeObject(ca);	//rewritnig file with new card
		oca.close();
		foca.close();
	}
	
	/**
	 * Saves Transactions List
	 * @param tr represents list of all transactions
	 * @throws IOException
	 */
	public void STransaction(ArrayList<Transaction> tr) throws IOException {
		FileOutputStream fotr = new FileOutputStream(new File("Transaction.bin"));
		ObjectOutputStream otr = new ObjectOutputStream(fotr);
		otr.writeObject(tr);	//rewritnig file with new transaction
		otr.close();
		fotr.close();
	}
}
