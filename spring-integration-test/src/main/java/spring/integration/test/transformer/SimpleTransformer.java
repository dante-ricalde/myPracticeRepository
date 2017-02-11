package spring.integration.test.transformer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

import spring.integration.test.var.PaymentType;

@MessageEndpoint("simpleTransformer")
public class SimpleTransformer {

	@Transformer
	public Message<PaymentType> transform(Message<PaymentType> message,
			@Header(value = "anotherPaymentType", required = false) String anotherPaymentType) {
		PaymentType payload = message.getPayload();
		if (StringUtils.isNotBlank(anotherPaymentType)) {
			payload.setPaymentType(anotherPaymentType);
		}
		return message;
	}
}
