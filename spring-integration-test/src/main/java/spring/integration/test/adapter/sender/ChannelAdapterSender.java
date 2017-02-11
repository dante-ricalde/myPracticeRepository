package spring.integration.test.adapter.sender;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("channelAdapterSender")
public class ChannelAdapterSender {

	/** The message. */
	private String message;

	/** The count. */
	private Double count;

	private Integer messageNumber = 0;

	private List<String> headerValuesToDetermineADestinationChannel;

	private Map<String, ?> headers;

	private int numberOfMessagesToApplyHeaders;

	private int numberOfMessagesToApplyHeadersCounter;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAdapterSender.class);

	public Message<String> createMessage() {
		if (count != null) {
			if (count == 10)
				return null;
			count++;
		}
		String messageText = "Message Number: '" + ++messageNumber
				+ "' Sending a message from a sender to a inbound channel adapter ";
		if (StringUtils.isNotEmpty(message)) {
			messageText += message;
		}
		Message<String> myMessage = null;
		if (CollectionUtils.isNotEmpty(headerValuesToDetermineADestinationChannel)) {
			String headerValueToDetermineADestinationChannel = getHeaderValueToDetermineADestinationChannel(
					headerValuesToDetermineADestinationChannel, messageNumber);
			myMessage = MessageBuilder
					.withPayload(messageText)
					.setHeaderIfAbsent("headerValueToDetermineADestinationChannel",
							headerValueToDetermineADestinationChannel).build();
		} else {
			myMessage = MessageBuilder.withPayload(messageText).build();
		}

		if (MapUtils.isNotEmpty(headers) && numberOfMessagesToApplyHeadersCounter < numberOfMessagesToApplyHeaders) {
			for (Map.Entry<String, ?> entry : headers.entrySet()) {
				myMessage = MessageBuilder.fromMessage(myMessage).setHeaderIfAbsent(entry.getKey(), entry.getValue())
						.build();
			}
			numberOfMessagesToApplyHeadersCounter++;
		}

		LOGGER.info("Sending the folowing message: " + messageText);
		return myMessage;
	}

	private String getHeaderValueToDetermineADestinationChannel(
			List<String> headerValuesToDetermineADestinationChannel2, Integer messageNumber) {
		String result = null;
		try {
			result = headerValuesToDetermineADestinationChannel.get(messageNumber);
		} catch (IndexOutOfBoundsException e) {
			result = headerValuesToDetermineADestinationChannel.get(messageNumber
					% headerValuesToDetermineADestinationChannel.size());
		}
		LOGGER.info(". Header Value to Determine a Destination Channel: " + result);
		return result;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * 
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the count.
	 * 
	 * @return the count
	 */
	public Double getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 * 
	 * @param count
	 *            the new count
	 */
	public void setCount(Double count) {
		this.count = count;
	}

	/**
	 * @return the headerValuesToDetermineADestinationChannel
	 */
	public List<String> getHeaderValuesToDetermineADestinationChannel() {
		return headerValuesToDetermineADestinationChannel;
	}

	/**
	 * @param headerValuesToDetermineADestinationChannel
	 *            the headerValuesToDetermineADestinationChannel to set
	 */
	public void setHeaderValuesToDetermineADestinationChannel(List<String> headerValuesToDetermineADestinationChannel) {
		this.headerValuesToDetermineADestinationChannel = headerValuesToDetermineADestinationChannel;
	}

	/**
	 * @return the headers
	 */
	public Map<String, ?> getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, ?> headers) {
		this.headers = headers;
	}

	/**
	 * @return the numberOfMessagesToApplyHeaders
	 */
	public int getNumberOfMessagesToApplyHeaders() {
		return numberOfMessagesToApplyHeaders;
	}

	/**
	 * @param numberOfMessagesToApplyHeaders
	 *            the numberOfMessagesToApplyHeaders to set
	 */
	public void setNumberOfMessagesToApplyHeaders(int numberOfMessagesToApplyHeaders) {
		this.numberOfMessagesToApplyHeaders = numberOfMessagesToApplyHeaders;
	}

}
