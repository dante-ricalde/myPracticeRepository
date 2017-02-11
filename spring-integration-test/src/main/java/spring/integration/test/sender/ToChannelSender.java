package spring.integration.test.sender;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("toChannelSender")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ToChannelSender extends Thread {

	@Resource(name = "publish-subscribe-channel-with-suscriptor")
	private MessageChannel publishSusbcriberChannelWithSubscriptor;

	@Override
	public void run() {
		boolean sended = publishSusbcriberChannelWithSubscriptor.send(new GenericMessage<String>("Envio a Dante"));
		System.out.println("Mensaje enviado:" + sended);
	}

	/**
	 * @return the publishSusbcriberChannelWithSubscriptor
	 */
	public MessageChannel getPublishSusbcriberChannelWithSubscriptor() {
		return publishSusbcriberChannelWithSubscriptor;
	}

	/**
	 * @param publishSusbcriberChannelWithSubscriptor
	 *            the publishSusbcriberChannelWithSubscriptor to set
	 */
	public void setPublishSusbcriberChannelWithSubscriptor(MessageChannel publishSusbcriberChannelWithSubscriptor) {
		this.publishSusbcriberChannelWithSubscriptor = publishSusbcriberChannelWithSubscriptor;
	}
}
