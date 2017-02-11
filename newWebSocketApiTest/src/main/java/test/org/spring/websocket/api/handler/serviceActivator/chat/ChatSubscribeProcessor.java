package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpAttributesContextHolder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import reactor.core.support.StringUtils;
import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@MessageEndpoint
public class ChatSubscribeProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatSubscribeProcessor.class);

	@Qualifier("sessionRegistry")
	@Autowired(required = false)
	private SessionRegistry sessionRegistry;

	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;

	@SuppressWarnings("unchecked")
	@ServiceActivator(inputChannel = "chatSubscribeChannel")
	public void process(@Payload(value = "message") Message<ChatMessage> message) {
		LOGGER.debug("on Application event invoked: " + message);
		String destination = StompHeaderAccessor.getDestination(message.getHeaders());
		if (StringUtils.endsWithIgnoreCase(destination, "notification")) {
			String userName = ((SecurityContext) SimpAttributesContextHolder.getAttributes().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
			List<User> principals = (List<User>) (Object) sessionRegistry.getAllPrincipals();
			LOGGER.debug("Getting all the principals");
			for (User user : principals) {
				if (!user.getUsername().equalsIgnoreCase(userName)) {
					LOGGER.debug("Sending a notification to the user: '{}'", user.getUsername());
					ChatMessage chatMessage = new ChatMessage("newUser", userName);
					template.convertAndSend("/exchange/chatDirectExchange/messages-" + user.getUsername() + "-" + "notification", chatMessage);
				}
			}
		}
	}

}
