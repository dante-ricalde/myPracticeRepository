package spring.integration.test.tcp.ssl;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component
public class MyCustomHandshakeCompletedListener implements HandshakeCompletedListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCustomHandshakeCompletedListener.class);

	@Override
	public void handshakeCompleted(HandshakeCompletedEvent event) {
		// TODO Auto-generated method stub
		LOGGER.debug("******************* THE HANDSHAKE IS COMPLETED *************************");
	}

}
