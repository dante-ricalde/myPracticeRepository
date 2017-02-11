package spring.integration.test.adapter.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("channelAdapterReceiver")
public class ChannelAdapterReceiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelAdapterReceiver.class);

	public void receiveMessage(Message<?> message) {
		LOGGER.info("Channel Adapter Receiver: Message Received: " + message
				+ "*********END OF THE MESSAGE *************************");
		if (message.getPayload() instanceof byte[]) {
			byte[] bytes = (byte[]) message.getPayload();
			LOGGER.info("Channel Adapter Receiver: Payload array of bytes to String: " + new String(bytes));
		}
	}
}
