package spring.integration.test.junit;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class ExecutorChannelTest extends AbstractTest {

	@Resource
	private MessageChannel executorChannel;

	/**
	 * Prueba enviar un mensaje a un channel, el mensaje será recibido por un service activator.
	 */
	@Test
	public void testExectorChannel() {
		boolean sended = executorChannel.send(new GenericMessage<String>("Probando un executor channel"));
		System.out.println(" ************** Mensaje enviado: " + sended);
	}
}
