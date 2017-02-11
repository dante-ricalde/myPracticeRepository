package spring.integration.test.generator;

import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 * @param <T>
 */
public interface MessageGenerator<T> {

	public Message<T> generateMessage();
}
