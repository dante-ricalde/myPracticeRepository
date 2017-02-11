package spring.integration.test.junit.gateway;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.Cafe;
import spring.integration.test.junit.AbstractTest;
import spring.integration.test.service.api.Order;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class GatewayWhereTheInvocationResultsInErrorsTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayWhereTheInvocationResultsInErrorsTest.class);

	@Autowired
	private Cafe whereTheInvocationResultsInErrorsGateway;

	@Ignore
	@Test
	public void testAGatewayWhereTheInvocationResultsInErrors() {
		for (int i = 0; i < 100; i++) {
			try {
				List<Order> openOrders = whereTheInvocationResultsInErrorsGateway.retrieveOpenOrders();
				LOGGER.info("retrieveOpenOrders method executed, and the returned value is: " + openOrders);
			} catch (Exception e) {
				LOGGER.info("Exception that has been thrown back to the gateway for a null reply");
			}
		}
	}
}
