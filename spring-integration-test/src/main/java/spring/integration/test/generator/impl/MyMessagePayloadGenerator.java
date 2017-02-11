package spring.integration.test.generator.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import spring.integration.test.generator.MessageGenerator;
import spring.integration.test.var.MyMessagePayload;

public class MyMessagePayloadGenerator implements MessageGenerator<MyMessagePayload> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyMessagePayloadGenerator.class);

	private Map<String, ?> headers;

	private boolean chooseOneHeaderRamdomly;

	private boolean setPayloadInTheHeader;

	private boolean setPayloadRandomlyInTheHeader;

	@SuppressWarnings("unchecked")
	@Override
	public Message<MyMessagePayload> generateMessage() {
		Message<MyMessagePayload> result = null;
		MyMessagePayload myMessagePayload = new MyMessagePayload("0001", "Dante Ricalde", "Programmer Analyst");
		result = MessageBuilder.withPayload(myMessagePayload).build();
		if (MapUtils.isNotEmpty(headers)) {
			if (chooseOneHeaderRamdomly) {
				Map.Entry<String, ?> header = (Entry<String, ?>) headers.entrySet().toArray()[(int) Math.round((Math
						.random() * (headers.size() - 1)))];
				result = MessageBuilder.fromMessage(result).setHeaderIfAbsent(header.getKey(), header.getValue())
						.build();
			} else {
				for (Map.Entry<String, ?> entry : headers.entrySet()) {
					result = MessageBuilder.fromMessage(result).setHeaderIfAbsent(entry.getKey(), entry.getValue())
							.build();
				}
			}
		}
		if (setPayloadInTheHeader) {
			result = MessageBuilder.fromMessage(result).setHeaderIfAbsent("myMessagePayload", myMessagePayload).build();
		}
		if (setPayloadRandomlyInTheHeader) {
			if (Math.random() > 0.5) {
				result = MessageBuilder.fromMessage(result).setHeaderIfAbsent("myMessagePayload", myMessagePayload)
						.build();
			}
			// } else {
			// MyMessagePayloadForHeader myMessagePayloadForHeader = new MyMessagePayloadForHeader("Dante Ricalde",
			// "Programmer Analyst");
			// result = MessageBuilder.fromMessage(result)
			// .setHeaderIfAbsent("myMessagePayload", myMessagePayloadForHeader).build();
			// }
		}
		LOGGER.info("Sending the folowing message: " + result);
		return result;
	}

	/**
	 * @return the headers
	 */
	public Map<String, ?> getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, ?> headers) {
		this.headers = headers;
	}

	/**
	 * @return the chooseOneHeaderRamdomly
	 */
	public boolean isChooseOneHeaderRamdomly() {
		return chooseOneHeaderRamdomly;
	}

	/**
	 * @param chooseOneHeaderRamdomly
	 *            the chooseOneHeaderRamdomly to set
	 */
	public void setChooseOneHeaderRamdomly(boolean chooseOneHeaderRamdomly) {
		this.chooseOneHeaderRamdomly = chooseOneHeaderRamdomly;
	}

	/**
	 * @return the setPayloadInTheHeader
	 */
	public boolean isSetPayloadInTheHeader() {
		return setPayloadInTheHeader;
	}

	/**
	 * @param setPayloadInTheHeader
	 *            the setPayloadInTheHeader to set
	 */
	public void setSetPayloadInTheHeader(boolean setPayloadInTheHeader) {
		this.setPayloadInTheHeader = setPayloadInTheHeader;
	}

	/**
	 * @return the setPayloadRandomlyInTheHeader
	 */
	public boolean isSetPayloadRandomlyInTheHeader() {
		return setPayloadRandomlyInTheHeader;
	}

	/**
	 * @param setPayloadRandomlyInTheHeader
	 *            the setPayloadRandomlyInTheHeader to set
	 */
	public void setSetPayloadRandomlyInTheHeader(boolean setPayloadRandomlyInTheHeader) {
		this.setPayloadRandomlyInTheHeader = setPayloadRandomlyInTheHeader;
	}
}
