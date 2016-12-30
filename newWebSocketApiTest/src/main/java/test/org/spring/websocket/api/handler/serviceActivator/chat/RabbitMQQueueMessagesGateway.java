package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessageEndpoint
public class RabbitMQQueueMessagesGateway {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessagesProcessor.class);

	@ServiceActivator(inputChannel = "queueMessagesGatewayRequestChannel")
	public List<ChatMessage> getMessages(String queueName) {
		LOGGER.debug("Getting messages from queue: '{}'", queueName);
		
		ChatMessage msg1 = new ChatMessage("hi", "dante", "keluchis");
		ChatMessage msg2 = new ChatMessage("how are you?", "dante", "keluchis");
		return Arrays.asList(msg1, msg2);
	}
}
