package spring.integration.test.amqp.retry.generator;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerExecutionException;
import org.springframework.amqp.rabbit.retry.MessageKeyGenerator;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myCustomMessageKeyGenerator")
public class MyCustomMessageKeyGenerator implements MessageKeyGenerator {

	@Override
	public Object getKey(Message message) {
		byte[] correlationId = message.getMessageProperties().getCorrelationId();
		if (correlationId == null) {
			throw new FatalListenerExecutionException(
					"Illegal null id in message. Failed to manage retry for message: " + message);
		}
		return correlationId;
	}

}
