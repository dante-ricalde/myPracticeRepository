package spring.integration.test.var;

import java.io.Serializable;
import java.util.Date;

import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class Item implements Serializable {

	private static final long serialVersionUID = -7129618533953057694L;

	private String id;

	private String code;

	private String description;

	private int status;

	private Date created_date;

	private Message<?> message;

	public Item() {
		super();
	}

	public Item(String id, String code, String description, int status) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.status = status;
	}

	public Item(String id, String code, String description, int status, Date created_date) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.status = status;
		this.created_date = created_date;
	}

	public Item(String id, String code, String description, int status, Date created_date, Message<?> message) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
		this.status = status;
		this.created_date = created_date;
		this.message = message;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}

	/**
	 * @param created_date
	 *            the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return the message
	 */
	public Message<?> getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message<?> message) {
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", code=" + code + ", description=" + description + ", status=" + status
				+ ", created_date=" + created_date + ", message=" + message + "]";
	}
	
}
