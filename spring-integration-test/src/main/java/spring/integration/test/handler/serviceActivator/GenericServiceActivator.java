package spring.integration.test.handler.serviceActivator;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("genericServiceActivator")
public class GenericServiceActivator {

	/** The set cookie. */
	private Boolean setCookie;

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericServiceActivator.class);

	@ServiceActivator(outputChannel = "toTestASimpleChainOutputQueueChannel")
	public Message<?> handleMessage(Message<?> message) {
		LOGGER.info("*************** Received Message for service activator. Mensaje: " + message);
		MessageBuilder<String> messageBuilder = MessageBuilder.withPayload("Respuesta del Servidor...");
		if (BooleanUtils.isTrue(setCookie)) {
			messageBuilder.setHeader("Set-Cookie", "MyCookie*************************");
			messageBuilder.setHeader("myCookie", "Cookie to be filtered *************************");
		}

		return messageBuilder.build();
	}

	/**
	 * @return the setCookie
	 */
	public Boolean getSetCookie() {
		return setCookie;
	}

	/**
	 * @param setCookie
	 *            the setCookie to set
	 */
	public void setSetCookie(Boolean setCookie) {
		this.setCookie = setCookie;
	}
}
