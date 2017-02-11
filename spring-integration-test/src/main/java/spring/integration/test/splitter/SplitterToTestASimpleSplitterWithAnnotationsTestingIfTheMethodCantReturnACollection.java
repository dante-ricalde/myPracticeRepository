package spring.integration.test.splitter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("plitterToTestASimpleSplitterWithAnnotationsTestingIfTheMethodCantReturnACollection")
public class SplitterToTestASimpleSplitterWithAnnotationsTestingIfTheMethodCantReturnACollection {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SplitterToTestASimpleSplitterWithAnnotationsTestingIfTheMethodCantReturnACollection.class);

	@Splitter
	public String splitMessage(Message<String> message) {
		LOGGER.error("message arrived :" + message);
		if (message != null) {
			return message.getPayload();
		} else {
			LOGGER.error("message arrived was null.");
		}
		return StringUtils.EMPTY;
	}
}
