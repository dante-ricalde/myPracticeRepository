package test.org.spring.websocket.api.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import reactor.core.support.StringUtils;
import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@Component
@Profile(value = { "chat" })
public class ChatEventListener implements ApplicationListener<SessionSubscribeEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatEventListener.class);

	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;

	@Qualifier("sessionRegistry")
	@Autowired(required = false)
	private SessionRegistry sessionRegistry;

//	@Autowired(required = false)
//	public ChatEventListener(@Qualifier(value = "org.springframework.messaging.simp.SimpMessagingTemplate#0") SimpMessagingTemplate template) {
//		this.template = template;
//	}

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		// TODO Auto-generated method stub
		LOGGER.debug("on Application event invoked: " + event);
		String destination = StompHeaderAccessor.getDestination(event.getMessage().getHeaders());
		if (StringUtils.endsWithIgnoreCase(destination, "notification")) {
			String userName = ((SecurityContext) SimpMessageHeaderAccessor.getSessionAttributes(event.getMessage().getHeaders()).get("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
			List<Object> principals = sessionRegistry.getAllPrincipals();
			LOGGER.debug("Getting all the principals");
			for (Object principal : principals) {
				User user = ((User) principal);
				if (!user.getUsername().equalsIgnoreCase(userName)) {
					LOGGER.debug("Sending a notification to the user: '{}'", user.getUsername());
					ChatMessage chatMessage = new ChatMessage("newUser", userName);
					template.convertAndSend("/exchange/amq.direct/messages-" + user.getUsername() + "-" + "notification", chatMessage);
				}
			}
		}
	}
}
