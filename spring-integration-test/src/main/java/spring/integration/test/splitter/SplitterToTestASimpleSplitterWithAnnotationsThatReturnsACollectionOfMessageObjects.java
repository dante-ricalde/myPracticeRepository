package spring.integration.test.splitter;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("splitterToTestASimpleSplitterWithAnnotationsThatReturnsACollectionOfMessageObjects")
public class SplitterToTestASimpleSplitterWithAnnotationsThatReturnsACollectionOfMessageObjects {

	public Collection<Message<?>> splitMessage(String message) {
		Collection<Message<?>> result = new ArrayList<Message<?>>();
		String[] splitMessage = StringUtils.split(message, " ");
		for (String string : splitMessage) {
			result.add(MessageBuilder.withPayload(string).build());
		}
		return result;
	}
}
