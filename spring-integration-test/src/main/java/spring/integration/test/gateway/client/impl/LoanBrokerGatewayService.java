package spring.integration.test.gateway.client.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;

import spring.integration.test.gateway.client.LoanBrokerGateway;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint
public class LoanBrokerGatewayService implements LoanBrokerGateway {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoanBrokerGatewayService.class);

	@Override
	public Double getLoanQuote(Double amount) {
		// TODO Auto-generated method stub
		LOGGER.info("Executing getLoanQuote for amount: " + amount);
		return 100D;
	}

	@Override
	public List<Double> getAllLoanQuotes(Double amount) {
		// TODO Auto-generated method stub
		LOGGER.info("Executing getAllLoanQuotes for amount: " + amount);
		return Arrays.asList(100D, 200D, 300D, 400D, 500D);
	}

}
