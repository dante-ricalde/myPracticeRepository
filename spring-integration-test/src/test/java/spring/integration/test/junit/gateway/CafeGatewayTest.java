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
public class CafeGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CafeGatewayTest.class);

	@Autowired
	private Cafe cafeGateway;

	@Ignore
	@Test
	public void testCafeGatewayTest() throws Exception {
		for (int i = 0; i < 100; i++) {
			List<Order> openOrders = cafeGateway.retrieveOpenOrders();
			LOGGER.info("retrieveOpenOrders method executed, and the returned value is: " + openOrders);
		}
	}

}
