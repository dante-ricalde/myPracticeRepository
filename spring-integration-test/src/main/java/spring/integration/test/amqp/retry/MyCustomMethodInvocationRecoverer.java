package spring.integration.test.amqp.retry;

import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.stereotype.Component;

@Component("myCustomMethodInvocationRecoverer")
public class MyCustomMethodInvocationRecoverer implements MethodInvocationRecoverer<Message> {

	@Override
	public Message recover(Object[] args, Throwable cause) {
		Message message = (Message) args[1];

		// This is not actually a normal outcome. It means the recovery was not successful, but we don't want to consume
		// any more messages until the nacks and rollbacks are sent for this (problematic) message...
//		throw new AmqpRejectAndDontRequeueException("Recovered message forces ack (if ack mode requires it): "
//				+ message, cause);
		
		
		// This is actually a normal outcome. It means the recovery was successful, but we don't want to consume
		// any more messages until the acks and commits are sent for this (problematic) message...
		throw new ImmediateAcknowledgeAmqpException("Recovered message forces ack (if ack mode requires it): "
				+ message, cause);
	}

}
