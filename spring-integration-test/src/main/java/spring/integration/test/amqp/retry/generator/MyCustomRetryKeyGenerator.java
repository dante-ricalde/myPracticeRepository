package spring.integration.test.amqp.retry.generator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerExecutionException;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;
import org.springframework.stereotype.Component;

@Component("myCustomRetryKeyGenerator")
public class MyCustomRetryKeyGenerator implements MethodArgumentsKeyGenerator {

	@Override
	public Object getKey(Object[] args) {

		Message message = (Message) args[1];

		String messageId = message.getMessageProperties().getMessageId();
		if (StringUtils.isBlank(messageId)) {
			messageId = (String) message.getMessageProperties().getHeaders().get(AmqpHeaders.MESSAGE_ID);
		}

		if (messageId == null) {
			throw new FatalListenerExecutionException(
					"Illegal null id in message. Failed to manage retry for message: " + message);
		}
		return messageId;
	}

	// public Object getKey(Object[] args) {
	// Message message = (Message) args[1];
	//
	// byte[] correlationId = message.getMessageProperties().getCorrelationId();
	// if (correlationId == null) {
	// throw new FatalListenerExecutionException(
	// "Illegal null id in message. Failed to manage retry for message: " + message);
	// }
	// return correlationId;
	// }
}
