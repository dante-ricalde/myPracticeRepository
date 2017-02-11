package spring.integration.test.adapter.sender;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("channelAdapterSenderToTestAnAggregator")
public class ChannelAdapterSenderToTestAnAggregator {

	/** The message. */
	private String message;

	/** The count. */
	private Double count;

	private Integer messageNumber = 0;

	private List<String> headerValuesToDetermineADestinationChannel;

	private Map<String, ?> headers;

	private int numberOfMessagesToApplyHeaders;

	private int numberOfMessagesToApplyHeadersCounter;

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAdapterSenderToTestAnAggregator.class);

	public Message<?> createMessage() {
		if (count != null) {
			if (count == 10)
				return null;
			count++;
		}
		LOGGER.info("M  E  S  S  A  G  E     N  U  M  B  E  R : " + count);
		return new GenericMessage(Arrays.asList("message number 1", "message number 2", "message number 3",
				"message number 4", "message number 5"));
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

}
