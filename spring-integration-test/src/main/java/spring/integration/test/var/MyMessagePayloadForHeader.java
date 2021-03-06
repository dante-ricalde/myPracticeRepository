package spring.integration.test.var;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MyMessagePayloadForHeader {

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/**
	 * Instantiates a new my message content.
	 * 
	 * @param code
	 *            the code
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 */
	public MyMessagePayloadForHeader(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MyMessagePayload [name=" + name + ", description=" + description + "]";
	}

}
