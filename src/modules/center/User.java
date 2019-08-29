package modules.center;

import java.io.Serializable;

/**
 * Class which represents the users of the center
 * @author Fi³oñczuk Micha³
 * @version 0.5
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2574227166443415485L;
	private String login;
	private String password;
	private String rights;
	private String orgName; 

	/*
	 * Non-parameter constructor. Use it only for system tests!
	
	public User() {
		this.login = "guest";
		this.password = "123";
		this.rights = "guest";
		this.orgName = "default"; 
	} */
	
	/**
	 * Standard way to create User object 
	 * @param login - String contains user's login
	 * @param password - String contains unencrypted password
	 * @param rights - String contains one of the option from ComboBox in GUI's language
	 * @param orgName - String name of Bank or Company. If doesn't exists put empty string
	 */
	public User(String login, String password, String rights, String orgName) {
		this.login = login;
		this.password = password;
		this.rights = rights;
		this.orgName = orgName; 
	}

	/**
	 * Getter of the login
	 * @return String, login of the user
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter of the login
	 * @param login String, new login of the user
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter of the password of the user
	 * @return String,password of the user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter of the password of the user
	 * @param password String, new password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter of the rights of the user
	 * @return String, rights of the user
	 */
	public String getRights() {
		return rights;
	}

	/**
	 * Setter of the rights of the user
	 * @param rights String, new rights of user
	 */
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	/**
	 * Setter of the bank or company name 
	 * @return String organisation name
	 */
	public String getOrgName() {
		return this.orgName; 
	}

}
