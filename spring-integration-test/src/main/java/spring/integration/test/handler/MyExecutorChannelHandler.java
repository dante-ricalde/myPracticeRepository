package spring.integration.test.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myExecutorChannelHandler")
public class MyExecutorChannelHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyExecutorChannelHandler.class);

	@ServiceActivator
	public void handlerMessage(Message<?> message) {
		MessageChannel replyChannel = (MessageChannel) message.getHeaders().getReplyChannel();
		if (replyChannel != null) {
			boolean sent = replyChannel.send(new GenericMessage<String>("Sending a message to a reply channel: "));
			LOGGER.info("Message sent to the reply channel: " + sent);
		}
		LOGGER.debug("*************** Received Message for service activator. Mensaje: " + message);
	}
}
