package spring.integration.test.aggregator.correlationStrategy;

import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myCorrelationStrategy")
public class MyCorrelationStrategy {

	/**
	 * The correlation key is the payload, namely, the message are going to be grouped by payload
	 * 
	 * @param message
	 * @return
	 */
	@CorrelationStrategy
	public Object getCorrelationKey(Message<?> message) {
		return message.getPayload();
	}
}
