package spring.integration.test.aggregator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("aggregatorImplementingTheAggregationAlgorithm")
public class AggregatorImplementingTheAggregationAlgorithm<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AggregatorImplementingTheAggregationAlgorithm.class);

	public Message<?> aggregatePayloads(List<Message<?>> messageAccumulated) {
		Message<?> result = null;
		String payload = StringUtils.EMPTY;
		if (CollectionUtils.isNotEmpty(messageAccumulated)) {
			int i = 0;
			for (Message<?> t : messageAccumulated) {
				if (i++ % 2 == 0) {
					LOGGER.info("Message to Aggregate: " + t);
					payload += t.getPayload();
				}
			}
			result = MessageBuilder.withPayload(payload).build();
			LOGGER.info("Message aggregated from all received messages: " + result);
		}
		return result;
	}
}
