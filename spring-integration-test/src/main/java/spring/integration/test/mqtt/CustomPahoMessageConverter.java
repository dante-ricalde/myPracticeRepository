package spring.integration.test.mqtt;

import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component
public class CustomPahoMessageConverter extends DefaultPahoMessageConverter {

	private volatile boolean payloadAsBytes = false;

	public CustomPahoMessageConverter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method accepts payloads of type byte[], String and Serializable.
	 */
	@Override
	protected byte[] messageToMqttBytes(Message<?> message) {
		Object payload = message.getPayload();
		Assert.isTrue(payload instanceof byte[] || payload instanceof String || payload instanceof Serializable);
		byte[] payloadBytes;
		if (payload instanceof String) {
			try {
				payloadBytes = ((String) payload).getBytes("UTF-8");
			} catch (Exception e) {
				throw new MessageConversionException("failed to convert Message to object", e);
			}
		} else if (payload instanceof Serializable) {
			payloadBytes = SerializationUtils.serialize((Serializable) payload);
		} else {
			payloadBytes = (byte[]) payload;
		}
		return payloadBytes;
	}

	@Override
	protected Object mqttBytesToPayload(MqttMessage mqttMessage) throws Exception {
		if (this.payloadAsBytes) {
			return mqttMessage.getPayload();
		} else if (mqttMessage.getPayload() instanceof byte[]) {
			return SerializationUtils.deserialize(mqttMessage.getPayload());
		} else {
			return new String(mqttMessage.getPayload(), "UTF-8");
		}
	}
}
