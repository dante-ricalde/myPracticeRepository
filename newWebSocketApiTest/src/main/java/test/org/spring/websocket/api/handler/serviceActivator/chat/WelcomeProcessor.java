package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;

import test.org.spring.websocket.api.controller.ChatController;

/**
 * 
 * @author dr.ricalde
 *
 */
@MessageEndpoint
public class WelcomeProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

	@Qualifier("sessionRegistry")
	@Autowired(required = false)
	private SessionRegistry sessionRegistry;

	@SuppressWarnings("unchecked")
	@ServiceActivator(inputChannel = "welcomeChannel")
	public List<String> welcome() {
		SecurityContext context = SecurityContextHolder.getContext();
		List<User> principals = (List<User>) (Object) sessionRegistry.getAllPrincipals();
		LOGGER.debug("Getting all the principals");
		List<String> userNames = new ArrayList<>();
		for (User user : principals) {
			LOGGER.debug("Getting user: '{}'", user.getUsername());
			if (!user.getUsername().equalsIgnoreCase(context.getAuthentication().getName())) {
				userNames.add(user.getUsername());
			}
		}
		return userNames;
	}
}
