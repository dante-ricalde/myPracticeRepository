package spring.integration.test.var;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 * 
 * @author dr.ricalde
 */
public class Person implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1780683941751584023L;

	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;

	/**
	 * Instantiates a new person.
	 * 
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 */
	public Person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Instantiates a new person.
	 * 
	 * @param name
	 *            the name
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 */
	public Person(String name, String firstName, String lastName) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Instantiates a new person.
	 * 
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 */
	public Person(String id, String name, String firstName, String lastName) {
		super();
		this.id = id;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
