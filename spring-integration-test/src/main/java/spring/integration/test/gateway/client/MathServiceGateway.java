package spring.integration.test.gateway.client;

import java.util.concurrent.Future;

/**
 * 
 * @author dr.ricalde
 * 
 */
public interface MathServiceGateway {

	/**
	 * Multiply by two.
	 * 
	 * @param i
	 *            the i
	 * @return the future
	 */
	public Future<Integer> multiplyByTwo(int i);
}
