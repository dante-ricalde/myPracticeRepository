package spring.integration.test.interceptor;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.locking.NioFileLocker;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component
public class MyHandlingMessageFileChannelInterceptor extends ChannelInterceptorAdapter {

	@Autowired(required = false)
	private NioFileLocker nioFileLocker;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		// TODO Auto-generated method stub
		File file = (File) message.getPayload();
		nioFileLocker.unlock(file);
		return super.preSend(message, channel);
	}

}
