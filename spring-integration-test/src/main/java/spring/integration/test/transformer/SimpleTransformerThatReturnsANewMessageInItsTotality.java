package spring.integration.test.transformer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import spring.integration.test.var.PaymentType;

@MessageEndpoint("simpleTransformerThatReturnsANewMessageInItsTotality")
public class SimpleTransformerThatReturnsANewMessageInItsTotality {

	@Transformer
	public Message<PaymentType> transform(Message<PaymentType> message,
			@Header(value = "anotherPaymentType", required = false) String anotherPaymentType) {
		Message<PaymentType> result = MessageBuilder.withPayload(message.getPayload()).build();
		if (StringUtils.isNotBlank(anotherPaymentType)) {
			PaymentType payload = result.getPayload();
			payload.setPaymentType(anotherPaymentType);
		}
		return result;
	}
}
