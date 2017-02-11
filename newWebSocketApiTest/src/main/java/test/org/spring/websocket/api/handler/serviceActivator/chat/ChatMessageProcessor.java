package test.org.spring.websocket.api.handler.serviceActivator.chat;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@MessageEndpoint
public class ChatMessageProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageProcessor.class);

	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;
	
	@Autowired
	private AmqpAdmin amqpAdmin;
	
	@Autowired
	@Qualifier("messagesKeluchisNotificationQueue")
	private Queue messagesKeluchisNotificationQueue;
	
	@Resource(name = "messagesKeluchisDanteQueue")
	private Queue messagesKeluchisDanteQueue;

	@ServiceActivator(inputChannel = "chatMessageProcessorChannel")
	// @SendTo(value = "/exchange/amq.direct/messages}")
	public ChatMessage process(@Payload ChatMessage chatMessage, MessageHeaders headers) {
		chatMessage.setTime(new DateTime().toString());
		LOGGER.debug("received Message from user: '{}' with receipt id: '{}'", chatMessage.getSender(), StompHeaderAccessor.getFirstNativeHeader("receipt", headers));
		ChatMessage receiptChatMessage = new ChatMessage(chatMessage.getId(), chatMessage.getMessage(), chatMessage.getRecipient(), chatMessage.getSender(), chatMessage.getDestination(), chatMessage.getTime(),
				Boolean.TRUE);
		template.convertAndSend("/exchange/chatDirectExchange/messages-" + chatMessage.getSender() + "-" + chatMessage.getRecipient(), receiptChatMessage);
		return chatMessage;
	}
	
	@PostConstruct
	public void init() {
//		ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//
//		AmqpAdmin admin = new RabbitAdmin(connectionFactory);
//		admin.declareQueue(new Queue("anonymousNyseMarketDateQueue"));
		amqpAdmin.declareQueue(messagesKeluchisNotificationQueue);
		amqpAdmin.declareQueue(messagesKeluchisDanteQueue);
	}

}
