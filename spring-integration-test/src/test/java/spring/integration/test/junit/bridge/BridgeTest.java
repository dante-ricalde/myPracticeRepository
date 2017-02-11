package spring.integration.test.junit.bridge;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;

import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class BridgeTest extends AbstractTest {

	@Resource(name = "stdin")
	private SourcePollingChannelAdapter stdin;

	@Autowired
	private MessageSource<?> byteStreamReadingMessageSource;

	@Test
	public void testStdinStreamChannelAdapter() throws Exception {
		stdin.setSource(byteStreamReadingMessageSource);
	}
}
