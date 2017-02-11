package spring.integration.test.junit.gateway;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.GenericFileWriter;
import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class GatewayWithAOnewayFlowWhereAnExceptionOccursTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GatewayWithAOnewayFlowWhereAnExceptionOccursTest.class);

	@Autowired
	private GenericFileWriter withAOnewayFlowWhereAnExceptionOccursGateway;

	@Ignore
	@Test
	public void testAGatewayWithANullChannelTosupressExceptions() {
		LOGGER.info("************************** Test if it is executing... *****************************");

		for (int i = 0; i < 100; i++) {
			try {
				String stringContent = "Dante Raphael Ricalde Delgado";
				String stringContent1 = "Kelly Vanessa Rivera Cordero";
				if (i % 2 == 0) {
					withAOnewayFlowWhereAnExceptionOccursGateway.write(stringContent.getBytes(), "names" + i);
				} else {
					withAOnewayFlowWhereAnExceptionOccursGateway.write(stringContent1.getBytes(), "names" + i);
				}
				LOGGER.info("method write of the gateway executed...");
			} catch (Exception e) {
				LOGGER.info(
						"Exception that has been thrown back to the gateway for an exception thrown in the downstream (service activator)...:",
						e);
			}
		}
	}

}
