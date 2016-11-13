package test.org.spring.websocket.api.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint(value = "usingTemplateServiceActivator")
public class UsingTemplateServiceActivator implements ServiceActivator<Void> {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsingTemplateServiceActivator.class);
	
	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;

	@Override
	@org.springframework.integration.annotation.ServiceActivator
	public Void handleMessage(Message<?> message) throws MessagingException {
		LOGGER.info("*************** Received Message for service activator. Mensaje: " + message);
		LOGGER.debug("1. Sending message to destination: ");
		template.convertAndSend("/exchange/amq.direct/messages-dante-kelly", new ChatMessage("Template " + ((ChatMessage) message.getPayload()).getMessage()));
		return null;
	}

}
