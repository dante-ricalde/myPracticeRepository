package test.org.spring.websocket.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.RequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.TomcatRequestUpgradeStrategy;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableStompBrokerRelay("/topic/,/user/queue/messages/,/queue/,/user/,/exchange/");
		config.enableSimpleBroker("/topic/,/user/queue/messages/,/queue/,/user/,/exchange/");
		config.setApplicationDestinationPrefixes("/appChatRelay");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		RequestUpgradeStrategy upgradeStrategy = new TomcatRequestUpgradeStrategy();
		registry.addEndpoint("/webSocketApiTest/anyUrlPattern/chatRelay")
		    .setHandshakeHandler(new DefaultHandshakeHandler(upgradeStrategy)).withSockJS();
		    //.setAllowedOrigins("*");//.withSockJS();		
	}

}