package spring.integration.test.var;

// TODO: Auto-generated Javadoc
/**
 * The Class Parent.
 * 
 * @author dr.ricalde
 */
public class Father {

	/** The child. */
	private Kid child;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new parent.
	 */
	public Father() {
		super();
	}

	/**
	 * Instantiates a new parent.
	 * 
	 * @param child
	 *            the child
	 * @param name
	 *            the name
	 */
	public Father(Kid child, String name) {
		super();
		this.child = child;
		this.name = name;
	}

	/**
	 * Gets the child.
	 * 
	 * @return the child
	 */
	public Kid getChild() {
		return child;
	}

	/**
	 * Sets the child.
	 * 
	 * @param child
	 *            the child to set
	 */
	public void setChild(Kid child) {
		this.child = child;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Father [child=" + child + ", name=" + name + "]";
	}
}
