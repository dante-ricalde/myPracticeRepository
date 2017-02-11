import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 
 * @author dr.ricalde
 * 
 */
@WebAppConfiguration
public class TimerTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TimerTest.class);

	@Test
	public void testTimer() throws Exception {
		LOGGER.debug("Stopping ...");
//		Thread.sleep(500000);
	}
}
