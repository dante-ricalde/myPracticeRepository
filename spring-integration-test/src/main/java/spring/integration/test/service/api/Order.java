package spring.integration.test.service.api;

// TODO: Auto-generated Javadoc
/**
 * The Class Order.
 * 
 * @author dr.ricalde
 */
public class Order {

	/** The id. */
	private String id;

	/** The description. */
	private String description;

	/**
	 * Instantiates a new order.
	 * 
	 * @param id
	 *            the id
	 * @param description
	 *            the description
	 */
	public Order(String id, String description) {
		super();
		this.id = id;
		this.description = description;
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
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", description=" + description + "]";
	}

}
