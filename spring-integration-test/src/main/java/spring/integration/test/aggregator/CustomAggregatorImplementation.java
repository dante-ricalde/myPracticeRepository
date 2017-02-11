package spring.integration.test.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@MessageEndpoint("customAggregatorImplementation")
public class CustomAggregatorImplementation<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAggregatorImplementation.class);

	public Message<?> aggregatePayloads(List<Message<T>> messageAccumulated) throws Exception {
		List<T> result = null;
		long randomNumber = Math.round((Math.random() * 3));
		String groupId = null;
		if (CollectionUtils.isNotEmpty(messageAccumulated)) {
			result = new ArrayList<T>();
			for (Message<T> t : messageAccumulated) {
				result.add(t.getPayload());
			}
		}
		if (randomNumber == 2) {
			throw new Exception("Exception throwed to test if the message group: " + groupId + " for the messages:  "
					+ messageAccumulated.get(0).getHeaders().getId() + " "
					// + messageAccumulated.get(1).getHeaders().getId() + " "
					// + messageAccumulated.get(2).getHeaders().getId()
					+ " is not removed.");
		}
		LOGGER.info("Message aggregated from all received messages: " + messageAccumulated.get(0).getHeaders().getId()
				+ " " // messageAccumulated.get(1).getHeaders().getId() + " "
				// + messageAccumulated.get(2).getHeaders().getId()
				+ " for the message group '{}': " + result, groupId);
		return new GenericMessage<List<T>>(result);
	}
}
