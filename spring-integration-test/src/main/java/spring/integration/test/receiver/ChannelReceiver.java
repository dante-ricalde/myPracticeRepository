package spring.integration.test.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("channelReceiver")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelReceiver extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelReceiver.class);

	private PollableChannel messageChannel;

	private Long timeout;

	@Override
	public void run() {
		LOGGER.info("Waiting for a message ...");
		Message<?> messageReceived = receiveMessage();
		LOGGER.info("Message received: " + messageReceived);
	}

	private Message<?> receiveMessage() {
		Message<?> messageReceived = null;
		if (timeout == null) {
			messageReceived = messageChannel.receive();
		} else {
			messageReceived = messageChannel.receive(timeout);
		}
		if (messageReceived != null) {
			// see if the message contains a reply channel
			MessageChannel replyChannel = (MessageChannel) messageReceived.getHeaders().getReplyChannel();
			replyChannel.send(new GenericMessage<String>("Reply message: " + messageReceived));
		}
		return messageReceived;
	}

	/**
	 * @return the messageChannel
	 */
	public PollableChannel getMessageChannel() {
		return messageChannel;
	}

	/**
	 * @param messageChannel
	 *            the messageChannel to set
	 */
	public void setMessageChannel(PollableChannel messageChannel) {
		this.messageChannel = messageChannel;
	}

	/**
	 * @return the timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
}
