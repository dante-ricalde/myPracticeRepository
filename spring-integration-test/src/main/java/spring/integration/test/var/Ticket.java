package spring.integration.test.var;

import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * The Class Ticket.
 * 
 * @author dr.ricalde
 */
public class Ticket {

	/**
	 * The Enum Priority.
	 */
	public enum Priority {

		/** The low. */
		low,
		/** The medium. */
		medium,
		/** The high. */
		high,
		/** The emergency. */
		emergency
	}

	/** The ticket id. */
	private long ticketId;

	/** The issue date time. */
	private Calendar issueDateTime;

	/** The description. */
	private String description;

	/** The priority. */
	private Priority priority;

	/**
	 * Instantiates a new ticket.
	 */
	public Ticket() {

	}

	/**
	 * Gets the ticket id.
	 * 
	 * @return the ticketId
	 */
	public long getTicketId() {
		return ticketId;
	}

	/**
	 * Sets the ticket id.
	 * 
	 * @param ticketId
	 *            the ticketId to set
	 */
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * Gets the issue date time.
	 * 
	 * @return the issueDateTime
	 */
	public Calendar getIssueDateTime() {
		return issueDateTime;
	}

	/**
	 * Sets the issue date time.
	 * 
	 * @param issueDateTime
	 *            the issueDateTime to set
	 */
	public void setIssueDateTime(Calendar issueDateTime) {
		this.issueDateTime = issueDateTime;
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

	/**
	 * Gets the priority.
	 * 
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 * 
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return String.format("Ticket# %d: [%s] %s", ticketId, priority, description);
	}

}
