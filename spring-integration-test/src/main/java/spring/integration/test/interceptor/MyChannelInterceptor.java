package spring.integration.test.interceptor;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myChannelInterceptor")
public class MyChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyChannelInterceptor.class);

	/** The channel to monitoring. */
	private String channelToMonitoring;

	private Boolean throwExceptionBeforeSending;

	private Boolean throwExceptionAfterSending;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		if (BooleanUtils.isTrue(throwExceptionBeforeSending)) {
			if (Math.random() > 0.5) {
				LOGGER.info("exception provoked for the channel : " + channel.toString() + " with the message:"
						+ message);
				String a = "stringToProvokeTheException";
				a.charAt(1000);
			}
		}
		double time = Math.random() * 100;
		try {
			LOGGER.info("preSend invoked for the channel : " + channel.toString() + " with the message:" + message);
			Thread.sleep((long) time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return super.preSend(message, channel);

		// LOGGER.info("Local interceptor executed in preSend for the channel: " + channel.toString()
		// + " with the message:" + message);
		// return super.preSend(message, channel);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		// TODO Auto-generated method stub
		if (BooleanUtils.isTrue(throwExceptionAfterSending)) {
			if (Math.random() > 0.5) {
				LOGGER.info("exception provoked for the channel : " + channel.toString() + " with the message:"
						+ message);
				String a = "stringToProvokeTheException";
				a.charAt(1000);
			}
		}
		super.postSend(message, channel, sent);
		LOGGER.info("post send invoked for the channel: " + channel.toString() + " with the message:" + message);
	}

	@Override
	public boolean preReceive(MessageChannel channel) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		LOGGER.info("pre receive invoked for the channel: " + channel.toString() + "...");
		boolean preReceive = super.preReceive(channel);
		processChannelToMonitoring(channel);
		return preReceive;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		LOGGER.info("post receive invoked for the channel: " + channel.toString() + " with the message: " + message);
		processChannelToMonitoring(channel);
		Message<?> postReceive = super.postReceive(message, channel);
		return postReceive;
	}

	/**
	 * Process channel to monitoring.
	 * 
	 * @param channel
	 *            the channel
	 */
	private void processChannelToMonitoring(MessageChannel channel) {
		if (StringUtils.isNotEmpty(channelToMonitoring)) {
			if (channelToMonitoring.equalsIgnoreCase(channel.toString())) {
				QueueChannel queueChannel = (QueueChannel) channel;
				LOGGER.info("Queue size of the channel: " + queueChannel.getQueueSize());
			}
		}
	}

	/**
	 * Gets the channel to monitoring.
	 * 
	 * @return the channelToMonitoring
	 */
	public String getChannelToMonitoring() {
		return channelToMonitoring;
	}

	/**
	 * Sets the channel to monitoring.
	 * 
	 * @param channelToMonitoring
	 *            the channelToMonitoring to set
	 */
	public void setChannelToMonitoring(String channelToMonitoring) {
		this.channelToMonitoring = channelToMonitoring;
	}

	/**
	 * @return the throwExceptionBeforeSending
	 */
	public Boolean getThrowExceptionBeforeSending() {
		return throwExceptionBeforeSending;
	}

	/**
	 * @param throwExceptionBeforeSending
	 *            the throwExceptionBeforeSending to set
	 */
	public void setThrowExceptionBeforeSending(Boolean throwExceptionBeforeSending) {
		this.throwExceptionBeforeSending = throwExceptionBeforeSending;
	}

	/**
	 * @return the throwExceptionAfterSending
	 */
	public Boolean getThrowExceptionAfterSending() {
		return throwExceptionAfterSending;
	}

	/**
	 * @param throwExceptionAfterSending
	 *            the throwExceptionAfterSending to set
	 */
	public void setThrowExceptionAfterSending(Boolean throwExceptionAfterSending) {
		this.throwExceptionAfterSending = throwExceptionAfterSending;
	}
}
