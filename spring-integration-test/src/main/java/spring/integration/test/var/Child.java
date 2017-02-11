package spring.integration.test.var;

import java.io.Serializable;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Child.
 * 
 * @author dr.ricalde
 */
public class Child implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7403171945531266492L;

	/** The name. */
	private String name;

	/** The nick names. */
	private List<String> nickNames;

	/**
	 * Instantiates a new child.
	 */
	public Child() {
		super();
	}

	/**
	 * Instantiates a new child.
	 * 
	 * @param name
	 *            the name
	 * @param nickNames
	 *            the nick names
	 */
	public Child(String name, List<String> nickNames) {
		super();
		this.name = name;
		this.nickNames = nickNames;
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
	 * Gets the nick names.
	 * 
	 * @return the nickNames
	 */
	public List<String> getNickNames() {
		return nickNames;
	}

	/**
	 * Sets the nick names.
	 * 
	 * @param nickNames
	 *            the nickNames to set
	 */
	public void setNickNames(List<String> nickNames) {
		this.nickNames = nickNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Child [name=" + name + ", nickNames=" + nickNames + "]";
	}
}
