package spring.integration.test.gateway.client;

import java.util.List;

import org.springframework.integration.annotation.Payload;

import spring.integration.test.service.api.Order;

/**
 * 
 * @author dr.ricalde
 * 
 */
public interface Cafe {

	/**
	 * Retrieve open orders.
	 * 
	 * @return the list
	 */
	@Payload("new java.util.Date()")
	public List<Order> retrieveOpenOrders();
}
