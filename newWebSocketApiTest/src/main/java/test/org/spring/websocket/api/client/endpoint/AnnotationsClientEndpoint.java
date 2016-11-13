package test.org.spring.websocket.api.client.endpoint;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

@ClientEndpoint
public class AnnotationsClientEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationsClientEndpoint.class);

	private static final String SENT_MESSAGE = "Hello World";

	private static final String ERROR_TEST_MESSAGE = "ERROR_TEST";

	/**
	 * @PathParam doesn't work in ClientEnpoint
	 * @param session
	 * @param guestID
	 * @throws Exception
	 */
	@OnOpen
	public void onStart(Session session, @PathParam(value = "guest-id") String guestID) throws Exception {
		URI uri = session.getRequestURI();
		LOGGER.debug("Session Id: '{}' processed for the instance: '{}'", session.getId(), this.toString());
		LOGGER.debug("Sending a message using URI template: '{}' and guest id: '{}' ==> " + SENT_MESSAGE, uri, guestID);
		if (uri != null && StringUtils.endsWithIgnoreCase(uri.getRawPath(), "toTestOnError")) {
			LOGGER.debug("Setting the user property: MyUserProperty");
			// this user property is kept only in this session, i.e. in this web socket client endpoint (in all the
			// events of the lifecycle of this endpoint) , not in the
			// session of the web socket server endpoint.
			session.getUserProperties().put("MyUserProperty", 20L);
			session.getBasicRemote().sendText(ERROR_TEST_MESSAGE);
		} else {
			session.getBasicRemote().sendText(SENT_MESSAGE + " with URI " + uri);
		}
	}

	@OnMessage
	public void onMessage(String message) {
		LOGGER.debug("Message received by web sockect server endpoint: " + message);
	}

	/**
	 * This onClose method is invoked when a session.close is invoked in this endpoint, when receiving a websocket
	 * protocol close frame when a close was initiated by a local container (timeout), but not when an @onClose method
	 * of the server endpooint has been executed after the @onError method executed in the same server endpoint.
	 * 
	 * @PathParam doesn't work in ClientEnpoint
	 * @param session
	 * @param closeReason
	 * @param guestID
	 */
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		// TODO Auto-generated method stub
		LOGGER.debug("Closing session for URI template " + session.getRequestURI() + " with sessionId:"
				+ session.getId());
		LOGGER.debug("MyUserProperty: " + session.getUserProperties().get("MyUserProperty"));
		LOGGER.debug("Close Code: " + closeReason.getCloseCode());
		LOGGER.debug("Close Reason: " + closeReason.getReasonPhrase());
	}

	@OnError
	public void onError(Session session, Throwable error) {
		LOGGER.debug("OnError() method invoked by the container when an error has been generated on any of the connections to this endpoint.");
		LOGGER.debug("Error: ", error);
		LOGGER.debug("MyUserProperty: " + session.getUserProperties().get("MyUserProperty"));
	}
}
