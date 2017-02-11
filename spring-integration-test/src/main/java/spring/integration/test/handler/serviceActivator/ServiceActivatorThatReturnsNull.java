package spring.integration.test.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("serviceActivatorThatReturnsNull")
public class ServiceActivatorThatReturnsNull {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceActivatorThatReturnsNull.class);

	@ServiceActivator
	public Message<?> handleMessage(Message<?> message) {
		LOGGER.info("Executing retrieveOpenOrders for the message: " + message);
		return null;
	}

}
