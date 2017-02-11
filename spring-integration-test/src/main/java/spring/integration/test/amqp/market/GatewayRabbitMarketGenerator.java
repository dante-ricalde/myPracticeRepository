package spring.integration.test.amqp.market;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.amqp.AmqpHeaders;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import spring.integration.test.amqp.market.api.MockStock;
import spring.integration.test.amqp.market.api.Quote;
import spring.integration.test.amqp.market.api.Stock;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint("gatewayRabbitMarketGenerator")
public class GatewayRabbitMarketGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayRabbitMarketGenerator.class);

	private static final Random random = new Random();

	// @Resource(name = "stocks")
	// @Inject or
	@Autowired(required = false)
	// @Value(value = "#{mockStocks}")
	@Value(value = "#{getBeanFactory().containsBean('mockStocks') ? getBeanFactory().getBean('mockStocks') : null}")
	private List<MockStock> mockStocks;

	public Message<Stock> generateMarketData() {
		Quote quote = generateFakeQuote();
		Stock stock = quote.getStock();
		LOGGER.info("Sending market data for:" + stock.getTicker());
		String messageId = "app.stock.quotes." + stock.getStockExchange() + "." + stock.getTicker() + ".UUID."
				+ UUID.randomUUID().toString();

		return MessageBuilder.withPayload(stock).setHeader("amqp_messageId", messageId)
				.setHeader(AmqpHeaders.SPRING_REPLY_CORRELATION, messageId)
				.setHeader(AmqpHeaders.SPRING_REPLY_TO_STACK, "anonymousOutboundGatewayReplyQueue").build();
		// .setHeader("amqp_replyTo", "anonymousOutboundGatewayReplyQueue").build();
		// .setHeader(
		// MessageHeaders.ID,
		// "app.stock.quotes." + stock.getStockExchange() + "." + stock.getTicker() + ".UUID."
		// + UUID.randomUUID().toString()).build();
	}

	private Quote generateFakeQuote() {
		MockStock stock = (MockStock) this.mockStocks.get(random.nextInt(this.mockStocks.size()));
		String price = stock.randomPrice();
		return new Quote(stock, price);
	}
}
