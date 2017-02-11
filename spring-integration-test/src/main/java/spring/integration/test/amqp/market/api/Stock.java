package spring.integration.test.amqp.market.api;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class Stock {

	private String ticker;

	private StockExchange stockExchange;

	public Stock() {
	}

	public Stock(StockExchange stockExchange, String ticker) {
		this.stockExchange = stockExchange;
		this.ticker = ticker;
	}

	/**
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * @return the stockExchange
	 */
	public StockExchange getStockExchange() {
		return stockExchange;
	}

	/**
	 * @param stockExchange
	 *            the stockExchange to set
	 */
	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + ", stockExchange=" + stockExchange + "]";
	}

}
