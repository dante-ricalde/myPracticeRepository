package spring.integration.test.junit.message;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MessageTest extends AbstractTest {

	@Test
	public void testMessageBuilder() {
		Message<Integer> importantMessage = MessageBuilder.withPayload(99).setPriority(5).build();
		IntegrationMessageHeaderAccessor importantMessageHeaderAccessor = new IntegrationMessageHeaderAccessor(
				importantMessage);
		Assert.assertTrue(importantMessageHeaderAccessor.getPriority().equals(5));
		Message<Integer> lessImportantMessage = MessageBuilder.fromMessage(importantMessage)
				.setHeaderIfAbsent(IntegrationMessageHeaderAccessor.PRIORITY, 2).build();
		IntegrationMessageHeaderAccessor lessImportantMessageHeaderAccessor = new IntegrationMessageHeaderAccessor(
				lessImportantMessage);
		Assert.assertTrue(lessImportantMessageHeaderAccessor.getPriority().equals(5));
	}
}
