package spring.integration.test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import spring.integration.test.var.User;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("serviceActivatorForUser")
public class ServiceActivatorForUser<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceActivatorForUser.class);

	@ServiceActivator
	public Message<User> handleMessage(Message<T> message) throws MessagingException {
		LOGGER.info("*************** Received Message for service activator. Mensaje: " + message);
		User result = null;
		if (message.getPayload() instanceof User) {
			User user = (User) message.getPayload();
			result = buildUser(user.getUserName());
		} else if (message.getPayload() instanceof String) {
			String userName = (String) message.getPayload();
			result = buildUser(userName);
		}
		return MessageBuilder.withPayload(result).build();
	}

	/**
	 * Builds the user.
	 * 
	 * @param userName
	 *            the user name
	 * @return the user
	 */
	private User buildUser(String userName) {
		User result;
		result = new User(userName);
		if ("dante.ricalde".equalsIgnoreCase(userName)) {
			result.setEmailAndPassword("dante.ricalde@hotmail.com", "pescadito");
		} else {
			result.setEmailAndPassword("kelly.rivera@hotmail.com", "chatiboris");
		}
		return result;
	}
}
