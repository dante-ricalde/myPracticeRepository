package spring.integration.test.junit.interceptor;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class WireTapTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WireTapTest.class);

	@Autowired
	private MessageChannel channelWithWireTap;

	/**
	 * It sends a message to a direct channel with a service activator subscribed to the channel. This channel has two
	 * wire tap interceptors, They both are used in conjunction with two logging channel adapters. The first logging
	 * channel adapter is a full logger and the second is a logger with a SpEL expression to be applied against
	 * "payload" and/or "headers" of the message. These wire Tap interceptors (each one configured with its logging
	 * channel adapter respectively) intercept the channel for logging the message using the configuration specified in
	 * their respective logging channel adapter (full message or expression against message content for this example).
	 */
	@Ignore
	@Test
	public void testWireTap() {
		boolean sent = channelWithWireTap.send(new GenericMessage<String>(
				"Sending a message to a channel with a wire tap interceptor..."));
		LOGGER.info("Message sent to the channel with a wire tap: " + sent);
		boolean sentAnotherMessage = channelWithWireTap.send(new GenericMessage<String>(
				"Sending another message to a channel with a wire tap interceptor..."));
		LOGGER.info("Another message sent to the channel with a wire tap: " + sentAnotherMessage);
	}
}
