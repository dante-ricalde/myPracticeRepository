package spring.integration.test.junit.gateway;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.LoanBrokerGateway;
import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class LoanBrokerGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanBrokerGatewayTest.class);

	@Autowired
	private LoanBrokerGateway loanBrokerGateway;

	@Ignore
	@Test
	public void testLoadnBrokerGateway() throws Exception {
		for (int i = 0; i < 100; i++) {
			if (i % 2 == 0) {
				Double loanQuote = loanBrokerGateway.getLoanQuote(100D);
				LOGGER.info("getLoanQuote method executed, and returned value is: " + loanQuote);
			} else {
				List<Double> allLoanQuotes = loanBrokerGateway.getAllLoanQuotes(250D);
				LOGGER.info("getAllLoanQuotes method executed, and the returned values are: " + allLoanQuotes);
			}
		}
	}
}
