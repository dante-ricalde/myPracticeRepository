package spring.integration.test.var;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author dr.ricalde
 */
public class User implements Cloneable {

	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

	/** The user name. */
	private String userName;

	/** The password. */
	private String password;

	/** The email. */
	private String email;

	/**
	 * Instantiates a new user.
	 * 
	 * @param userName
	 *            the user name
	 */
	public User(String userName) {
		super();
		this.userName = userName;
	}

	/**
	 * Instantiates a new user.
	 * 
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 */
	public User(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the email.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 * 
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the email and password.
	 * 
	 * @param email
	 *            the email
	 * @param password
	 *            the password
	 */
	public void setEmailAndPassword(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", email=" + email + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		LOGGER.debug("Clonning object: " + this);
		User result = new User();
		result.setUserName(this.userName);
		result.setPassword(this.password);
		result.setEmail(this.email);
		return result;
	}
}
