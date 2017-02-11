package spring.integration.test.var;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Parent.
 * 
 * @author dr.ricalde
 */
public class Parent implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7220927168578131674L;

	/** The child. */
	private Child child;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new parent.
	 */
	public Parent() {
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
	public Parent(Child child, String name) {
		super();
		this.child = child;
		this.name = name;
	}

	/**
	 * Gets the child.
	 * 
	 * @return the child
	 */
	public Child getChild() {
		return child;
	}

	/**
	 * Sets the child.
	 * 
	 * @param child
	 *            the child to set
	 */
	public void setChild(Child child) {
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
		return "Parent [child=" + child + ", name=" + name + "]";
	}

}
