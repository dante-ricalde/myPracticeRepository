package test.org.spring.websocket.api.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@RequestMapping("/chat")
@MessageMapping("/chat")
@Controller
@Profile(value = { "chat" })
public class ChatController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;

	@Qualifier("sessionRegistry")
	@Autowired(required = false)
	private SessionRegistry sessionRegistry;

//	@Autowired(required = false)
//	public ChatController(@Qualifier(value = "org.springframework.messaging.simp.SimpMessagingTemplate#0") SimpMessagingTemplate template) {
//		this.template = template;
//	}

	@RequestMapping(value = "/welcome", method = POST)
	@ResponseBody
	public Object greetMVC(ModelMap modelMap) {
		SecurityContext context = SecurityContextHolder.getContext();
		List<Object> principals = sessionRegistry.getAllPrincipals();
		LOGGER.debug("Getting all the principals");
		// String principalUserName = ((SecurityContext)
		// SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders()).get("SPRING_SECURITY_CONTEXT")).getAuthentication().getName();
		List<String> userNames = new ArrayList<>();
		for (Object principal : principals) {
			User user = ((User) principal);
			LOGGER.debug("Getting user: '{}'", user.getUsername());
			if (!user.getUsername().equalsIgnoreCase(context.getAuthentication().getName())) {
				userNames.add(user.getUsername());
			}
		}
		modelMap.addAttribute("users", userNames);
		return userNames;
	}

	@RequestMapping("/sendMessage")
	@MessageMapping("/sendMessage")
	// @SendToUser("/queue/messages")
	// public void receiveMessage(@Payload ChatMessage chatMessage, Message<?> message, @AuthenticationPrincipal
	// UserActive principal) {
	public void receiveMessage(@Payload ChatMessage chatMessage, Message<?> message) {
		// Principal user = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
		LOGGER.debug("received Message from user: '{}' with receipt id: '{}'", chatMessage.getSender(), StompHeaderAccessor.getFirstNativeHeader("receipt", message.getHeaders()));
		// template.convertAndSendToUser("keluchis", "/exchange/amq.direct/messages", message.getPayload());
		template.convertAndSend("/exchange/amq.direct/messages-" + chatMessage.getRecipient() + "-" + chatMessage.getSender(), chatMessage);
		template.convertAndSend("/exchange/amq.direct/messages-" + chatMessage.getSender() + "-" + chatMessage.getRecipient(), chatMessage);
		// return message.getPayload();
	}

	// @SubscribeMapping("/receiveMessage")
	// public String receive(Message<?> message) {
	// LOGGER.debug("received Message from user: '{}'", "kk");//principal.getName());
	// template.convertAndSendToUser("keluchis", "/queue/messages", message.getPayload());
	// return "hola";
	// }

}
