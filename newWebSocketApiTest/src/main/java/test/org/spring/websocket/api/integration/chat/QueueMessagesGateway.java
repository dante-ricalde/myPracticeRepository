package test.org.spring.websocket.api.integration.chat;

import java.util.List;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessagingGateway
public interface QueueMessagesGateway {

	@Gateway(requestChannel = "queueMessagesGatewayRequestChannel")
	List<ChatMessage> getMessages(String queueName);
}
