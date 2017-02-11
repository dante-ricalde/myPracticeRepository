package spring.integration.test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("serviceActivatorWithAnOutputChannel")
public class ServiceActivatorWithAnOutputChannel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceActivatorWithAnOutputChannel.class);

	@ServiceActivator(outputChannel = "toTestASimpleChainOutputQueueChannel")
	public Message<?> handleMessage(Message<?> message) {
		LOGGER.info("*************** Received Message for service activator. Mensaje: " + message);
		return message;
	}
}
