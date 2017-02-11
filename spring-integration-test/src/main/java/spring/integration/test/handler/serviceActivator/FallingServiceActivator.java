package spring.integration.test.handler.serviceActivator;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("fallingServiceActivator")
public class FallingServiceActivator {

	@ServiceActivator
	public void service(Message<?> message) {
		throw new RuntimeException("foo");
	}

}
