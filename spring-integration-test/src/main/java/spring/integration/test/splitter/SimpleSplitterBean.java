package spring.integration.test.splitter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("simpleSplitterBean")
public class SimpleSplitterBean extends AbstractMessageSplitter {

	@Override
	protected List<String> splitMessage(Message<?> message) {
		return Arrays.asList(StringUtils.split(String.valueOf(message.getPayload()), " "));
	}

}
