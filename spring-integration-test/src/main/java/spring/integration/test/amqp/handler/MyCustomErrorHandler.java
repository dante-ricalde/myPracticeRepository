package spring.integration.test.amqp.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myCustomErrorHandler")
public class MyCustomErrorHandler implements ErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCustomErrorHandler.class);

	@Override
	public void handleError(Throwable t) {
		LOGGER.debug("***************************** It has happened an error: " + t
				+ "************************************************");

	}

}
