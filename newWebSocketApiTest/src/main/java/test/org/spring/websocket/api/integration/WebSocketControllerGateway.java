package test.org.spring.websocket.api.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author dr.ricalde
 *
 */
//@MessagingGateway(errorChannel = "greetingErrorChannel")
//@Controller
public class WebSocketControllerGateway {

	@MessageMapping("/integrationGreeting")
//	@SendToUser("queue/answer")
//	@Gateway(requestChannel = "greetingChannel")
	public String greeting(String payload) {
		return "hola";
	}
}
