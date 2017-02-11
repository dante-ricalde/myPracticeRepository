package test.org.spring.websocket.api.handler.serviceActivator;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * 
 * @author dr.ricalde
 *
 */
public interface ServiceActivator<T> {
	
	@org.springframework.integration.annotation.ServiceActivator
	public T handleMessage(Message<?> message) throws MessagingException;

}
