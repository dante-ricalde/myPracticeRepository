package spring.integration.test.tcp.support;

import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpSocketSupport;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component
public class MyCustomTcpSocketSupport implements TcpSocketSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCustomTcpSocketSupport.class);

	@Autowired
	private HandshakeCompletedListener handshakeCompletedListener;

	@Override
	public void postProcessServerSocket(ServerSocket serverSocket) {
		// TODO Auto-generated method stub
		LOGGER.debug("Server Socket: " + serverSocket);
	}

	@Override
	public void postProcessSocket(Socket socket) {
		// TODO Auto-generated method stub
		SSLSocket sslSocket = (SSLSocket) socket;
		sslSocket.addHandshakeCompletedListener(handshakeCompletedListener);
	}
}
