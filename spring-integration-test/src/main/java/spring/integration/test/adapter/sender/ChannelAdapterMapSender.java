package spring.integration.test.adapter.sender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("channelAdapterMapSender")
public class ChannelAdapterMapSender {

	public Message<Map<String, Object>> createMessage() {
		Message<Map<String, Object>> result = null;
		Map<String, Object> payload = new HashMap<>();
		if (Math.random() > 0.5) {
			payload.put("userName", "dante.ricalde");
			result = MessageBuilder.withPayload(payload).build();
		} else {
			payload.put("userName", "kelly.rivera");
			result = MessageBuilder.withPayload(payload).build();
		}
		return result;
	}

}
