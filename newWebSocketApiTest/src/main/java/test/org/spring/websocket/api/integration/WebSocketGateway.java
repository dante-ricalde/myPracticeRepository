package test.org.spring.websocket.api.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@MessagingGateway(name = "myWebSocketGateway")
@Controller
public interface WebSocketGateway {

	@MessageMapping("/integrationGreeting")
	@SendTo(value = "/topic/integrationGreetingResponse")
	@Gateway(requestChannel = "greetingChannel", replyChannel = "replyGreetingChannel")
	ChatMessage greeting(@Payload ChatMessage payload, MessageHeaders headers);
//	ChatMessage greeting(Message<?> message);
	
	@MessageMapping("/integrationGreeting1")
	@SendTo(value = "/topic/integrationGreetingResponse")
	@Gateway(requestChannel = "greetingChannel1", replyChannel = "replyGreetingChannel1")
	ChatMessage greeting1(@Payload ChatMessage payload, MessageHeaders headers);

}
