package spring.integration.test.handler;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MyAnotherExecutorChannelHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyAnotherExecutorChannelHandler.class);

	private boolean sometimesThrowsException;

	private String id;

	public MyAnotherExecutorChannelHandler() {
		super();
	}

	public MyAnotherExecutorChannelHandler(String id) {
		this.id = id;
	}

	@ServiceActivator
	public void handlerMessage(Message<?> message) {
		if (BooleanUtils.isTrue(sometimesThrowsException)) {
			double random = Math.random();
			if (random > 0.50) {
				LOGGER.info("*************** Throwing exception in the service activator. Mensaje: " + message);
				throw new RuntimeException("foo");
			}
		}
		LOGGER.debug(" ****** Received Message for service activator" + id + ". Mensaje: " + message);
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

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
