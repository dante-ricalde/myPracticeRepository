package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessageEndpoint
public class ChatMessagesProcessor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessagesProcessor.class);
	
	@ServiceActivator(inputChannel = "chatMessagesProcessorChannel")
	public List<ChatMessage> process(String queueName) {
		LOGGER.debug("Getting messages from: '{queueName}'", queueName);
		return null;
	}
}
