package spring.integration.test.comparator;

import java.util.Comparator;

import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myComparator")
public class MyComparator implements Comparator<Message<?>> {

	/**
	 * Implementación de compare para ordernar de menor a mayor.
	 */
	public int compare(Message<?> o1, Message<?> o2) {
		IntegrationMessageHeaderAccessor o1integrationMessageHeaderAccessor = new IntegrationMessageHeaderAccessor(o1);
		IntegrationMessageHeaderAccessor o2integrationMessageHeaderAccessor = new IntegrationMessageHeaderAccessor(o2);
		if (o1integrationMessageHeaderAccessor.getPriority() < o2integrationMessageHeaderAccessor.getPriority())
			return -1;
		else if (o1integrationMessageHeaderAccessor.getPriority() > o2integrationMessageHeaderAccessor.getPriority())
			return 1;
		return 0;
	}
}
