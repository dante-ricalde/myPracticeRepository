package spring.integration.test.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MyGlobalChannelInterceptor extends ChannelInterceptorAdapter {

	/** The number. */
	private String number;

	private static final Logger LOGGER = LoggerFactory.getLogger(MyGlobalChannelInterceptor.class);

	/** The reply channel. */
	private MessageChannel replyChannel;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		if (replyChannel != null) {
			MessageBuilder<?> messageBuilder = MessageBuilder.fromMessage(message);
			messageBuilder.setReplyChannel(replyChannel);
			message = messageBuilder.build();
		}
		LOGGER.info("Global interceptor number" + number + " executed in preSend for the channel: "
				+ channel.toString());
		return super.preSend(message, channel);
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the reply channel.
	 * 
	 * @return the reply channel
	 */
	public MessageChannel getReplyChannel() {
		return replyChannel;
	}

	/**
	 * Sets the reply channel.
	 * 
	 * @param replyChannel
	 *            the new reply channel
	 */
	public void setReplyChannel(MessageChannel replyChannel) {
		this.replyChannel = replyChannel;
	}
}
