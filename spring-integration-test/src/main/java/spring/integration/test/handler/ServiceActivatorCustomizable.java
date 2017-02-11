package spring.integration.test.handler;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("serviceActivatorCustomizable")
public class ServiceActivatorCustomizable {

	private boolean sometimesThrowsException;

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceActivatorCustomizable.class);

	@ServiceActivator
	public Message<?> handleMessage(Message<?> message) throws MessagingException {
		if (BooleanUtils.isTrue(sometimesThrowsException)) {
			double random = Math.random();
			if (random > 0.5) {
				LOGGER.info("*************** Throwing exception in the service activator. Mensaje: " + message);
				throw new RuntimeException("foo");
			}
		}
		if (message.getPayload() instanceof byte[]) {
			LOGGER.info("*************** Received Message for service activator. Mensaje: "
					+ new String((byte[]) message.getPayload()));
		} else {
			LOGGER.info("*************** Received Message for service activator. Mensaje: " + message);
		}
		return message;
	}

	/**
	 * @return the sometimesThrowsException
	 */
	public boolean isSometimesThrowsException() {
		return sometimesThrowsException;
	}

	/**
	 * @param sometimesThrowsException
	 *            the sometimesThrowsException to set
	 */
	public void setSometimesThrowsException(boolean sometimesThrowsException) {
		this.sometimesThrowsException = sometimesThrowsException;
	}

}
