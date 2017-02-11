package spring.integration.test.amqp.market.api;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 
 * @author dr.ricalde
 * 
 */
public final class MockStock extends Stock {

	private static final Random random = new Random();

	private int basePrice;

	private final DecimalFormat twoPlacesFormat = new DecimalFormat("0.00");

	public MockStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MockStock(String ticker, StockExchange stockExchange, int basePrice) {
		super(stockExchange, ticker);
		this.basePrice = basePrice;
	}

	public String randomPrice() {
		return this.twoPlacesFormat.format(this.basePrice + Math.abs(random.nextGaussian()));
	}

	/**
	 * @return the basePrice
	 */
	public int getBasePrice() {
		return basePrice;
	}

	/**
	 * @param basePrice
	 *            the basePrice to set
	 */
	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	@Override
	public String toString() {
		return "Stock [ticker=" + getTicker() + ", stockExchange=" + getStockExchange() + ", price" + basePrice + "]";
	}
}
