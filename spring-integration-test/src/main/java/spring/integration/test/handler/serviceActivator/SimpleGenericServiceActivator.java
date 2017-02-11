package spring.integration.test.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("simpleGenericServiceActivator")
public class SimpleGenericServiceActivator {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleGenericServiceActivator.class);

	@ServiceActivator
	public void handleMessage(Message<?> message) {
		LOGGER.debug("*************** Received Message for service activator. Mensaje: " + message);
	}

}
