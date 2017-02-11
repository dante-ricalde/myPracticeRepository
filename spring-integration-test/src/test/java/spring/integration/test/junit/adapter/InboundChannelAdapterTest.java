package spring.integration.test.junit.adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;

import spring.integration.test.junit.AbstractTest;

/**
 * @author dr.ricalde
 * 
 */
public class InboundChannelAdapterTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundChannelAdapterTest.class);

	@Autowired
	private SourcePollingChannelAdapter simpleInboundChannelAdapter;

	@Ignore
	@Test
	public void testInboundChannelAdapter() {
		LOGGER.info("simple Inbound channel adapter is of type: " + simpleInboundChannelAdapter.getClass().getName());
	}
	
//	@Test
//	public void testStdinStreamChannelAdapter() throws Exception {
//		char c;
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		System.out.println("Enter characters, 'q' to quit.");
//		// read characters
//		do {
//			c = (char) br.read();
//			System.out.println(c);
//		} while (c != 'q');
//	}

}
