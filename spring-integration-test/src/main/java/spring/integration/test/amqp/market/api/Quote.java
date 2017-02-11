package spring.integration.test.amqp.market.api;

import java.text.DateFormat;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * Domain object representing a stock quote.
 * 
 * @author dr.ricalde
 * 
 */
@JsonAutoDetect
public class Quote {

	private Stock stock;

	private String price;

	private long timestamp;

	private DateFormat format = DateFormat.getTimeInstance();

	public Quote() {
		this(null, null);
	}

	public Quote(Stock stock, String price) {
		this(stock, price, new Date().getTime());
	}

	public Quote(Stock stock, String price, long timestamp) {
		this.stock = stock;
		this.price = price;
		this.timestamp = timestamp;
	}

	public String getTimeString() {
		return format.format(new Date(timestamp));
	}

	@Override
	public String toString() {
		return "Quote [time=" + getTimeString() + ", stock=" + stock + ", price=" + price + "]";
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Stock stock) {
		this.stock = stock;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
