package spring.integration.test.gateway.client.impl;

import java.util.Random;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint
public class MathServiceGatewayImpl {
	
	private final Random random = new Random();

	@ServiceActivator
	public Integer multiplyByTwo(int i) throws Exception {
		long sleep = random.nextInt(10) * 500;
		Thread.sleep(sleep);
		return i * 2;
	}

}
