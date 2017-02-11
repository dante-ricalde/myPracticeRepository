package spring.integration.test.gateway.client.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import spring.integration.test.gateway.client.TicketIssuer;
import spring.integration.test.var.Ticket;

// TODO: Auto-generated Javadoc
/**
 * The Class TicketIssuerService.
 * 
 * @author dr.ricalde
 */
@Component
public class TicketIssuerService implements TicketIssuer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TicketIssuerService.class);

	@Override
	@ServiceActivator
	public Ticket issueTicket(long ticketId) {
		Ticket result = new Ticket();
		result.setIssueDateTime(Calendar.getInstance());
		result.setDescription("New Ticket");
		result.setPriority(Ticket.Priority.medium);
		result.setTicketId(ticketId);
		LOGGER.info("Issuing a Ticket: " + result.getIssueDateTime());
		return result;
	}
}
