package spring.integration.test.junit.gateway;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.FileWriter;
import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class FileWriterGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterGatewayTest.class);

	/** The file writer gateway. */
	@Autowired
	private FileWriter fileWriterGateway;

	@Ignore
	@Test
	public void testFileWriterGateway() throws Exception {
		for (int i = 0; i < 100; i++) {
			String stringContent = "Dante Raphael Ricalde Delgado";
			String stringContent1 = "Kelly Vanessa Rivera Cordero";
			if (i % 2 == 0) {
				fileWriterGateway.write(stringContent.getBytes(), "names" + i);
			} else {
				fileWriterGateway.write(stringContent1.getBytes(), "names" + i);
			}
			LOGGER.info("method write of the gateway executed...");
		}
	}

}
