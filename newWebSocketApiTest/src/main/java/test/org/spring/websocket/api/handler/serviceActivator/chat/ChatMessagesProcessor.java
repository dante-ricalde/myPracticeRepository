package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;

import test.org.spring.websocket.api.integration.chat.QueueMessagesGateway;
import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessageEndpoint
public class ChatMessagesProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessagesProcessor.class);

	@Autowired
	private QueueMessagesGateway queueMessagesGateway;

	@ServiceActivator(inputChannel = "chatMessagesProcessorChannel")
	public List<ChatMessage> process(String queueName, MessageHeaders headers) {
		LOGGER.debug("Getting messages from: '{}'", queueName);
		List<ChatMessage> messages = queueMessagesGateway.getMessages("messages-" + queueName);
		return messages;
	}
}
