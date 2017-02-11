package spring.integration.test.junit.httpGateway;

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
@Ignore
public class HttpSimpleOutboundGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpSimpleOutboundGatewayTest.class);

	@Autowired
	private Cafe httpOutboundGatewayGateway;

	@Ignore
	@Test
	public void testAGatewayWhereTheInvocationResultsInErrors() {
		try {
			List<Order> openOrders = httpOutboundGatewayGateway.retrieveOpenOrders();
			LOGGER.info("retrieveOpenOrders method executed, and the returned value is: " + openOrders);
		} catch (Exception e) {
			LOGGER.info("Exception that has been thrown back to the gateway for a null reply");
		}
	}
}
