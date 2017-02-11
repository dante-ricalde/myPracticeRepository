package spring.integration.test.junit.gateway;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import spring.integration.test.gateway.client.MathServiceGateway;
import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MathServiceGatewayTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GatewayWithAOnewayFlowWhereAnExceptionOccursTest.class);

	private static ExecutorService executor = Executors.newFixedThreadPool(100);
	private static int timeout = 20;

	@Autowired
	private MathServiceGateway mathAsyncGateWay;

	@Ignore
	@Test
	public void testMathServiceGateway() throws Exception {
		Map<Integer, Future<Integer>> results = new HashMap<>();
		Integer number;

		for (int i = 0; i < 300; i++) {
			try {
				LOGGER.info("************************** Test if it is executing... *****************************");
				number = 500 * i;
				Future<Integer> response = mathAsyncGateWay.multiplyByTwo(number);
				results.put(number, response);
			} catch (Exception e) {
				LOGGER.error("An error has occured in the testMathServiceGateway test ...", e);
			}
		}
		for (final Map.Entry<Integer, Future<Integer>> resultEntry : results.entrySet()) {
			executor.execute(new Runnable() {
				public void run() {
					int[] result = processFuture(resultEntry);

					if (result[1] == -1) {
						LOGGER.info("Multiplying " + result[0]
								+ " should be easy. You should be able to multiply any number < 100 by 2 in your head");
					} else if (result[1] == -2) {
						LOGGER.info("Multiplication of " + result[0] + " by 2 is can not be accomplished in " + timeout
								+ " seconds");
					} else {
						LOGGER.info("Result of multiplication of " + result[0] + " by 2 is " + result[1]);
					}
				}
			});
		}
		executor.shutdown();
		executor.awaitTermination(60, TimeUnit.SECONDS);
		LOGGER.info("Finished");
	}

	public static int[] processFuture(Map.Entry<Integer, Future<Integer>> resultEntry) {
		int originalNumber = resultEntry.getKey();
		Future<Integer> result = resultEntry.getValue();
		try {
			int finalResult = result.get(timeout, TimeUnit.SECONDS);
			return new int[] { originalNumber, finalResult };
		} catch (ExecutionException e) {
			return new int[] { originalNumber, -1 };
		} catch (TimeoutException tex) {
			return new int[] { originalNumber, -2 };
		} catch (Exception ex) {
			System.out.println();
			// ignore
		}
		return null;
	}
}
