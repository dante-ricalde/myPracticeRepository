package test.org.spring.websocket.api.handler.serviceActivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import test.org.spring.websocket.api.integration.WebSocketGateway;
import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("gatewayInvoker")
public class GatewayInvoker {

	@Autowired(required =  false)
	WebSocketGateway webSocketGateway;

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayInvoker.class);

	@ServiceActivator
//	public void handleMessage(Message<?> message) {
	public void handleMessage(Message<ChatMessage> message) {
		ChatMessage gatewayResponse = webSocketGateway.greeting(message.getPayload(), message.getHeaders());
		LOGGER.debug("Gateway response:" + gatewayResponse);
	}
}
