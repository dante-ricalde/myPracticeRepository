package spring.integration.test.splitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint(value = "simpleSplitterBeanToTestASplitterWhoseMethodReturnsMessageListAndReceivesAPayloadArg")
public class SimpleSplitterBeanToTestASplitterWhoseMethodReturnsMessageListAndReceivesAPayloadArg {

	public Collection<Message<String>> splitPayload(String payload) {
		List<Message<String>> result = null;
		if (StringUtils.isNotBlank(payload)) {
			String[] splitMessage = StringUtils.split(payload, " ");
			if (ArrayUtils.isNotEmpty(splitMessage)) {
				result = new ArrayList<Message<String>>();
				for (String string : splitMessage) {
					result.add(MessageBuilder.withPayload(string).build());
				}
			}
		}
		return result;
	}

}
