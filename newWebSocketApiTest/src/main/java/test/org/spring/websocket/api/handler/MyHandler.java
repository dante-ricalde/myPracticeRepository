package test.org.spring.websocket.api.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * 
 * @author dr.ricalde
 *
 */
@Component
public class MyHandler extends TextWebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyHandler.class);

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("Handling text message: " + message);
		super.handleTextMessage(session, message);
		session.sendMessage(new TextMessage("MyHandler processing the message: " + message));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("Connection closed: " + status);
		super.afterConnectionClosed(session, status);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("Handling Transport Error: " + exception);
		super.handleTransportError(session, exception);
	}

	@Override
	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.debug("handlePongMessage: " + message);
		super.handlePongMessage(session, message);
	}
}
