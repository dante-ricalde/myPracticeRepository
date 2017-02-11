package test.org.spring.websocket.api.handler.serviceActivator.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessageEndpoint
public class RabbitMQQueueMessagesGateway {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQQueueMessagesGateway.class);
	
	@Autowired
	MappingJackson2MessageConverter converter;

	@ServiceActivator(inputChannel = "queueMessagesGatewayRequestChannel", outputChannel = "queueMessagesGatewayHttpJsonRequestChannel")
	public ModelMap getMessages(String queueName) {
		LOGGER.debug("Getting messages from queue: '{}'", queueName);
		
		//ChatMessage msg1 = new ChatMessage("hi", "dante", "keluchis");
		//ChatMessage msg2 = new ChatMessage("how are you?", "dante", "keluchis");
		ModelMap body = new ModelMap();   
        body.put("count", "1000");
        body.put("requeue", "true");
        body.put("encoding", "auto");
        body.put("truncate", "50000");
        body.put("queueName", queueName + "-queue");
		return body;
	}
	/*
	@ServiceActivator(inputChannel = "queueMessagesGatewayJsonReplyChannel", outputChannel="queueMessagesGatewayChatMessageReplyChannel")
	public List<ChatMessage> transformToChatMessage(List<String> payload) throws IOException {
		LOGGER.debug("Transforming message: '{}'", payload);//converter.getObjectMapper().readValue(payload.toString(), TypeFactory.defaultInstance().constructCollectionType(List.class, ChatMessage.class))
		return payload.stream().map(e -> (ChatMessage) converter.fromMessage(MessageBuilder.withPayload(e).build(), ChatMessage.class)).collect(Collectors.toList());
		//return null;
		//return message.getPayload().stream().map(e -> converter.fromMessage(e, ChatMessage.class)).collect(Collectors.toList());
		//message.getPayload().stream().forEach(e -> converter.fromMessage(message, ChatMessage.class));
	}*/
}
