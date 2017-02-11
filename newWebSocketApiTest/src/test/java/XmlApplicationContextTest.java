import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Receiptable;
import org.springframework.messaging.simp.stomp.StompSession.Subscription;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.AnnotatedEndpointConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.JettyXhrTransport;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import test.org.spring.websocket.api.client.endpoint.AnnotationsClientEndpoint;
import test.org.spring.websocket.api.client.handler.MyWebSocketHandler;
import test.org.spring.websocket.api.model.ChatMessage;
import test.org.spring.websocket.api.tomcat.support.TomcatWebSocketTestServer;

@WebAppConfiguration()
public class XmlApplicationContextTest extends AbstractTest {

	@Autowired
	private WebApplicationContext wac;

	private static MockMvc mockMvc;

	private ServerContainer serverContainer;

	private static TomcatWebSocketTestServer server;

	private static HttpClient jettyHttpClient;

	private static final Logger LOGGER = LoggerFactory.getLogger(XmlApplicationContextTest.class);

	@Autowired
	private TaskScheduler myScheduler;

	private static CountDownLatch messageLatch;

	@Before
	public void setup() throws Exception {
		// to only execute the first time
		if (mockMvc == null) {
			jettyHttpClient = new HttpClient();
			jettyHttpClient.setMaxConnectionsPerDestination(1000);
			jettyHttpClient.setExecutor(new QueuedThreadPool());
			jettyHttpClient.start();
			// MockMvcHtmlUnitDriverBuilder.webAppContextSetup(wac, springSecurity()));
			mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
			server = new TomcatWebSocketTestServer(8080);
			server.deployConfig(wac);
			server.start();
			// The servlet and all filters involved in an async request must have async support enabled using the
			// Servlet API or by adding an <async-support>true</async-support> element to servlet and filter
			// declarations in web.xml
			// In this case I have enabled the async-supported in Tomcat directly (enable Servlet async support)
			ServletRegistration.Dynamic servletRegistration = (Dynamic) server.getServletContext().getServletRegistrations().get("dispatcherServlet");
			servletRegistration.setAsyncSupported(true);
		}
	}

	@AfterClass
	public static void stop() throws Exception {
		server.stop();
	}

	@Test
	public void testWebServerSocket() {
		LOGGER.debug("Testing testWebServerSocket ...");
		try {
			StandardWebSocketClient client = new StandardWebSocketClient();
			AnnotatedEndpointConnectionManager manager = new AnnotatedEndpointConnectionManager(AnnotationsClientEndpoint.class, "ws://localhost:8080/anyUrlPattern/myHandler",
					new Object[] {});
			manager.start();
			// WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
			// webSocketContainer.connectToServer(AnnotationsClientEndpoint.class, new URI(
			// "ws://localhost:8080/webSocketApiTest/anyUrlPattern/myHandler"));
			Thread.sleep(200);
		} catch (Exception e) {
			LOGGER.error("Error" + e);
		}
	}

	/**
	 * The hearbeats are sent by the server and received by the sockjs client, the logs for the heartbeats are: in the server: TRACE
	 * [org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession] - Scheduled heartbeat in session e036afd14bc848d79dd4fe75d532ff28 and in the client: TRACE
	 * [org.springframework.web.socket.sockjs.client.WebSocketClientSockJsSession] - Received heartbeat in WebSocketClientSockJsSession[id='60ece9f6266c4be9806bdd394a064b1b,
	 * url=ws://localhost:8080/myHandlerWithSockJs]
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSockJsClientThatConnectsToSockJsEndpoint() throws Exception {
		List<Transport> transports = new ArrayList<>(2);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		transports.add(new RestTemplateXhrTransport());

		SockJsClient sockJsClient = new SockJsClient(transports);
		ListenableFuture<WebSocketSession> doHandshake = sockJsClient.doHandshake(new MyWebSocketHandler(), "ws://localhost:8080/anyUrlPattern/myHandlerWithSockJs");
		LOGGER.debug("Websocket session id" + doHandshake.get().getId());		
		// I wanted to use this for reading the heartbeats but it didn't work.
		// HeartbeatListener listener = new HeartbeatListener("127.0.0.1", 8080);
		Thread.sleep(200);
	}

	/**
	 * 
	 * This debug line indicates that there is doing 5 hanshakes: Processing SockJS open frame in XhrClientSockJsSession[id='6e4436f4daa74f51ac44bdbed2cbb6dd Processing SockJS open frame in
	 * XhrClientSockJsSession[id='027295b0fe974fc38e7677a91555ccc3 .. .. ..
	 * 
	 * When the application stops at the end of the executions of all tests, the all 5 http connections opened for this test close:
	 * 
	 * 1. org.springframework.web.socket.sockjs.client.XhrClientSockJsSession] - Transport closed with CloseStatus[code=1006, reason=HttpConnectionOverHTTP@6bc67dc 2.
	 * [org.springframework.web.socket.sockjs.client.XhrClientSockJsSession] - Transport closed with CloseStatus[code=1006, reason=HttpConnectionOverHTTP@5a2efa31 .. .. ..
	 * 
	 * I have also tested the SockJsClient for simulating a large number of concurrent users and I had to configure the underlying HTTP client (for XHR transports) to allow a sufficient number of
	 * connections and threads and that worked.
	 * 
	 * HttpClient jettyHttpClient = new HttpClient(); jettyHttpClient.setMaxConnectionsPerDestination(1000); jettyHttpClient.setExecutor(new QueuedThreadPool(1000));
	 *
	 * @throws Exception
	 */
	@Repeat(value = 5)
	@Test
	public void testSockJsClientUsingXhrTransport() throws Exception {
		LOGGER.debug("****************** testSockJsClientUsingXhrTransport *******************");
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new JettyXhrTransport(jettyHttpClient));
		SockJsClient sockJsClient = new SockJsClient(transports);
		ListenableFuture<WebSocketSession> doHandshake = sockJsClient.doHandshake(new MyWebSocketHandler(), "ws://localhost:8080/anyUrlPattern/myHandlerWithSockJs");
		LOGGER.debug("Websocket session id" + doHandshake.get().getId());
		Thread.sleep(200);
	}

	/**
	 * This tests uses receipts, for that you need to use a stomp broker relay because the simple broker doesn't support receipts and enable the autoReceipt property in the stomp client session. In
	 * this case the stomp java client have registered 4 receipts messages from the server (2 from 2 sent messages directly to the queues with the client (messages sent to the controller doesn't work
	 * for this) and the another 2 from the subscribe operations with the client), we can see in the logs the following message 4 times:
	 * [org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession] - Preparing to write SockJsFrame content='a["RECEIPT\nreceipt-id:0\n\n\u0000"]'). In this test We have
	 * sent 4 messages and we have 6 received messages in the queues (6 subscribed messages from the queues (2 from the two first sends, and 4 from the 2 sends to the controller (each controller sends
	 * 2 messages by handled message), hence 2x2=4)).
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testStompSimpleClient() throws Throwable {
		// We create and configure the client
		// WebSocketClient transport = new StandardWebSocketClient();
		// WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		WebSocketClient transport = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(transport);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.setTaskScheduler(myScheduler); // for heartbeats, receipts
		stompClient.setDefaultHeartbeat(new long[] { 20000, 20000 });
		stompClient.setReceiptTimeLimit(30000);
		// stompClient.
		// connect to the websocket and provide a handler for the STOMP session
		String url = "ws://127.0.0.1:8080/anyUrlPattern/chatRelay";
		messageLatch = new CountDownLatch(6);
		StompSessionHandler handler = new StompSessionHandler() {
			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				LOGGER.debug("handleFrame invoked with headers: '{}' and payload: '{}'", headers, payload);
			}

			@Override
			public Type getPayloadType(StompHeaders headers) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void handleTransportError(StompSession session, Throwable exception) {
				LOGGER.error("handleTransportError invoked with session: '{}'. Exception: '{}'", session, exception);
			}

			@Override
			public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
				LOGGER.error("handleException invoked: '{}' with session: '{}' for command: '{}' with stomp headers: '{}' and payload: '{}'", exception, session, command, headers, payload);
			}

			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				LOGGER.debug("afterConnected invoked with session: '{}' and stomp headers: '{}'", session, connectedHeaders);
				session.setAutoReceipt(true);
				ChatMessage chatMessage = new ChatMessage("Hola keluchis", "dante", "keluchis");
				Function<StompSession, Receiptable> sendFunction = x -> x.send("/exchange/amq.direct/messages-keluchis-dante", new ChatMessage("keluchis. Directly to the queue", "dante", "keluchis"));
				// Function<Receiptable, String> afterSendFunction = x -> x.getReceiptId();
				// final Receiptable[] receiptable = new Receiptable[1];
				StompFrameHandler stompFrameHandler = new StompFrameHandler() {
					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						LOGGER.debug("handleFrame invoked for message:" + payload + " for destination: '{}' with receipId: '{}'", headers.getDestination(), headers.getReceiptId());
						messageLatch.countDown();
					}

					@Override
					public Type getPayloadType(StompHeaders headers) {
						// TODO Auto-generated method stub
						return ChatMessage.class;
					}
				};
				// subscribe to queue messages-dante-keluchis
				Subscription subscribe = session.subscribe("/exchange/amq.direct/messages-" + chatMessage.getSender() + "-" + chatMessage.getRecipient(), stompFrameHandler);
				subscribe.addReceiptLostTask(new ReceiptTaskImpl().handle("A receipt is not received in the configured time."));
				subscribe.addReceiptTask(new ReceiptTaskImpl().handle("A receipt is received in the configured time."));
				// subscribe to queue messages-keluchis-dante
				Subscription subscribe2 = session.subscribe("/exchange/amq.direct/messages-" + chatMessage.getRecipient() + "-" + chatMessage.getSender(), stompFrameHandler);
				subscribe2.addReceiptLostTask(new ReceiptTaskImpl().handle("A receipt is not received in the configured time."));
				subscribe2.addReceiptTask(new ReceiptTaskImpl().handle("A receipt is received in the configured time."));
				// sending a message directly to the queue
				Receiptable apply = sendFunction.apply(session);
				apply.addReceiptLostTask(new ReceiptTaskImpl().handle("A receipt is not received in the configured time."));
				apply.addReceiptTask(new ReceiptTaskImpl().handle("A receipt is received in the configured time."));
				// sending a message directly to the queue
				Receiptable apply4 = sendFunction.apply(session);
				apply4.addReceiptLostTask(new ReceiptTaskImpl().handle("A receipt is not received in the configured time."));
				apply4.addReceiptTask(new ReceiptTaskImpl().handle("A receipt is received in the configured time."));
				// sending a message to a controller method handler
				Receiptable apply1 = session.send("/appChatRelay/chat/sendMessage", new ChatMessage("Hola Dantito 1", "dante", "keluchis"));
				apply1.addReceiptLostTask(new ReceiptTaskImpl().handle("A receipt is not received in the configured time."));
				apply1.addReceiptTask(new ReceiptTaskImpl().handle("A receipt is received in the configured time."));
				// sending a message to a controller method handler
				session.send("/appChatRelay/chat/sendMessage", new ChatMessage("Hola Dantito 2", "dante", "keluchis"));
			}
		};
		ListenableFuture<StompSession> connected = stompClient.connect(url, handler);
		LOGGER.debug("Stomp session id" + connected.get().getSessionId());
		messageLatch.await(30, TimeUnit.SECONDS);
	}

	private interface ReceiptTask {
		public Runnable handle(String message);
	}

	private class ReceiptTaskImpl implements ReceiptTask {
		@Override
		public Runnable handle(String message) {
			return new Runnable() {
				@Override
				public void run() {
					LOGGER.debug(message);
				}
			};
		}
	}
}
