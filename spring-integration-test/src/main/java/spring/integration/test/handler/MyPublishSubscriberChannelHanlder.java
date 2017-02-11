package spring.integration.test.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MyPublishSubscriberChannelHanlder implements MessageHandler {

	String id;

	public MyPublishSubscriberChannelHanlder(String id) {
		super();
		this.id = id;
	}

	public void handleMessage(Message<?> message) throws MessagingException {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("handler " + id + " invoked ..., message: " + message);
	}

}
