package spring.integration.test.gateway.client;

import java.util.List;

/**
 * 
 * @author dr.ricalde
 * 
 */
public interface LoanBrokerGateway {

	/**
	 * Gets the loan quote.
	 * 
	 * @param amount
	 *            the amount
	 * @return the loan quote
	 */
	public Double getLoanQuote(Double amount);

	/**
	 * Gets the all loan quotes.
	 * 
	 * @param amount
	 *            the amount
	 * @return the all loan quotes
	 */
	public List<Double> getAllLoanQuotes(Double amount);
}
