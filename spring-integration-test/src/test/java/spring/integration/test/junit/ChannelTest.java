package spring.integration.test.junit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.store.MessageStore;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.support.GenericMessage;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class ChannelTest extends AbstractTest {

	@Autowired
	private MessageChannel numberChannel;

	@Autowired
	private MessageChannel numberChannelMultipleTypes;

	@Autowired
	private MessageChannel queueChannel;

	@Autowired
	private MessageChannel dbBackedChannel;

	@Autowired
	private MessageStore messageStore;

	/**
	 * Prueba enviar un mensaje a un direct channel que solo acepta números. Nosotros enviamos un string que será
	 * convertido por el converter "strToInt" registrado en el Integration conversion Service (si no estuviese ese
	 * converter dará un error de tipo "String es un tipo no aceptado en ese channel"). De momento da error de
	 * "Dispatcher has no subscribers" porque no hay suscriptores registrados en ese channel.
	 */
	@Ignore
	@Test(expected = MessageDeliveryException.class)
	public void testChannelExpectingDataException() {
		numberChannel.send(new GenericMessage<String>("5"));
	}

	/**
	 * Prueba enviar un Long a un direct channel que acepta números. Va bien, pero de momento da error de
	 * "Dispatcher has no subscribers".
	 */
	@Ignore
	@Test(expected = MessageDeliveryException.class)
	public void testChannelWithExceptionDispatcherHasNoSubscribers() {
		numberChannel.send(new GenericMessage<Long>(5L));
	}

	/**
	 * Prueba enviar un String a un direct channel que acepta múltiples tipos, en este caso el channel acepta String y
	 * Números. Va bien, pero de momento da error de "Dispatcher has no subscribers".
	 */
	@Ignore
	@Test(expected = MessageDeliveryException.class)
	public void testChannelMultipleTypes() {
		numberChannelMultipleTypes.send(new GenericMessage<String>("5"));
	}

	/**
	 * Prueva de enviar un mensaje a un Queue Channel (Point to Point) que tiene una cola, en este caso todo va bien
	 * porque no es necesario tener un subscriber registrado en el Channel (no nos da un error de
	 * MessageDeliveryException: Dispatcher has no subscribers). El mensaje enviado se queda en la cola.
	 */
	@Ignore
	@Test
	public void testQueueChannel() {
		boolean sended = queueChannel.send(new GenericMessage<String>("5"));
		int queueSize = ((QueueChannel) queueChannel).getQueueSize();
		Assert.assertTrue(sended && queueSize == 1);
	}

	/**
	 * Prueba de enviar un mensaje a un Persistence Queue Channel (Point to Point ) respaldado por una implementacion
	 * persistente de la interfaz MessageGroupStore (JdbcMessageStore para ser especifícos, el cual usa RDBMS para
	 * almacenar mensajes (para base de datos relacionales es el único que ofrece Spring, tú puede hacerte la tuya si
	 * quieres implementando MessageGroupTStore)). En el test se observa que se envia un mensaje al canal, luego después
	 * de enviar la longitud de la cola y el tamanio del JdbcMessageStore es de 1, lo cual significa que está correcto
	 * ya que el mensaje se ha quedado en el jdbcMessageStore hasta que alguien consuma el mensaje (si alguien lo
	 * consume se borra del message store).
	 */
	@Test
	public void testDbBackedChannel() {
		dbBackedChannel.send(new GenericMessage<String>("10"));
		int queueSize = ((QueueChannel) dbBackedChannel).getQueueSize();
		long messageCount = messageStore.getMessageCount();
		Assert.assertTrue(queueSize == 1 && messageCount == 1);
	}
}
