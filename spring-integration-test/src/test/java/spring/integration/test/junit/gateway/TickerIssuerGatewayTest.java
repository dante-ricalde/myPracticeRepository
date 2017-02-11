package spring.integration.test.junit.gateway;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.TicketIssuer;
import spring.integration.test.junit.AbstractTest;
import spring.integration.test.var.Ticket;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class TickerIssuerGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TickerIssuerGatewayTest.class);

	@Autowired
	private TicketIssuer ticketIssueGateway;

	@Ignore
	@Test
	public void testTicketIssuerGateway() throws Exception {

		for (int i = 0; i < 100; i++) {
			Ticket ticket = ticketIssueGateway.issueTicket((long) (Math.random() * 100));
			LOGGER.info("Ticket: " + ticket + " was issued on: " + ticket.getIssueDateTime() + " with ticket id: "
					+ ticket.getTicketId());
		}
	}
}
