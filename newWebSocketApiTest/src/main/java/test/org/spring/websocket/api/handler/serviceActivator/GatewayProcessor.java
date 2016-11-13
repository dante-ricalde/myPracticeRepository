package test.org.spring.websocket.api.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("gatewayProcessor")
public class GatewayProcessor {

	// @Autowired
	// MappingJackson2MessageConverter converter;

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayProcessor.class);

	@ServiceActivator
	// public ChatMessage handleMessage(Message<?> message) {
	public ChatMessage handleMessage(@Payload ChatMessage payload, MessageHeaders headers) {
		// if (message.getPayload() instanceof byte[]) {
		// return (ChatMessage) converter.fromMessage(message, ChatMessage.class);
		// }
		Message<ChatMessage> result = MessageBuilder.withPayload(new ChatMessage("Gateway " + payload.getMessage(), payload.getSender(), payload.getRecipient())).build();
		return result.getPayload();
	}
}
