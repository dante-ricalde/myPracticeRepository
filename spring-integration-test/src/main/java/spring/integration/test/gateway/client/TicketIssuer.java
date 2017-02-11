package spring.integration.test.gateway.client;

import org.springframework.integration.annotation.Gateway;

import spring.integration.test.var.Ticket;

/**
 * 
 * @author dr.ricalde
 * 
 */
public interface TicketIssuer {

	/**
	 * Issue ticket.
	 * 
	 * @param ticketId
	 *            the ticket id
	 * @return the ticket
	 */
	@Gateway(replyChannel = "ticketReplies", requestChannel = "ticketRequests")
	public Ticket issueTicket(long ticketId);
}
