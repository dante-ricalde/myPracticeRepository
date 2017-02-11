package spring.integration.test.gateway.client.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import spring.integration.test.service.api.Order;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint
public class CafeService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CafeService.class);

	@ServiceActivator
	public List<Order> retrieveOpenOrders(Message<?> message) {
		// TODO Auto-generated method stub
		LOGGER.info("Executing retrieveOpenOrders for the message: " + message);
		return Arrays.asList(new Order("1", "Order 1"), new Order("2", "Order 2"), new Order("3", "Order 3"));
	}

}
