package spring.integration.test.junit;

import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import junit.framework.Assert;
import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import spring.integration.test.sender.ToChannelSender;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class PublishSubscribeChannelTest extends AbstractTest {

	@Resource(name = "publish-subscribe-channel")
	private MessageChannel publishSubscribeChannel;

	@Resource(name = "publish-subscribe-channel-with-suscriptor")
	private MessageChannel publishSusbcriberChannelWithSubscriptor;

	@Resource(name = "task-executor")
	private TaskExecutor taskExecutor;

	@Resource(name = "myPublishSubscriberChannelHandlerA")
	private MessageHandler myPublishSubscriberChannelHandlerA;

	@Resource(name = "myPublishSubscriberChannelHandlerB")
	private MessageHandler myPublishSubscriberChannelHandlerB;

	@Resource(name = "publish-subscribe-channel-with-suscriptor-secuence")
	private MessageChannel publishSusbcriberChannelWithSubscriptorSecuence;

	@Resource(name = "myPublishSubscriberChannelHandlerC")
	private MessageHandler myPublishSubscriberChannelHandlerC;

	@Resource(name = "myPublishSubscriberChannelHandlerD")
	private MessageHandler myPublishSubscriberChannelHandlerD;

	@Resource(name = "myPublishSubscriberChannelHandlerE")
	private MessageHandler myPublishSubscriberChannelHandlerE;

	/**
	 * Prueba enviar un mensaje a través de un PublishSubscribeChannel. Como no se ha especificado un task executor para
	 * que publique mensajes, los mensajes son simplemente publicados en el hilo del enviador. En este cado el envio da
	 * false porque no ha logrado enviar el mensaje (y es correcto) y no ha dado una excepción de
	 * "no hay subscriptores registrados" porque el BroadcastingDispatcher por defecto de este channel tiene la
	 * propiedad requireSubscribers a false.
	 */
	@Ignore
	@Test
	public void testPublishSusbcribeChannel() {
		boolean sended = publishSubscribeChannel.send(new GenericMessage<Number>(2.5D));
		Assert.assertFalse(sended);
	}

	/**
	 * Prueba de enviar 10 mensaje a través de un PublishSubscribeChannel con un taskExecutor a dos handlers suscritos
	 * al channel. El task executor se encarga de abrir un hilo separado cuando envia el mensaje a cada handler, si hay
	 * 2 handlers y se envian 5 mensajes, se crean entones 10 threads (10 tasks) uno para cada mensaje que el executor
	 * envia al suscriptor.
	 * 
	 * @throws InterruptedException
	 */
	@Ignore
	@Test
	public void testPublishSubscribeChannelWithExecutor() throws InterruptedException {
		// Lanzamos los hilos que envian los mensajes
		((PublishSubscribeChannel) publishSusbcriberChannelWithSubscriptor)
				.subscribe(myPublishSubscriberChannelHandlerA);
		((PublishSubscribeChannel) publishSusbcriberChannelWithSubscriptor)
				.subscribe(myPublishSubscriberChannelHandlerB);

		Thread thread = null;
		for (int i = 0; i < 5; i++) {
			thread = SpringApplicationContext.getBean("toChannelSender");
			thread.start();
		}
		// Chequeamos como va el task executor
		ThreadPoolExecutor threadPoolExecutor = ((ThreadPoolTaskExecutor) taskExecutor).getThreadPoolExecutor();
		do {
			System.out.println("Hilos ejecutados: " + threadPoolExecutor.getCompletedTaskCount());
			System.out.println("Hilos activos: " + threadPoolExecutor.getActiveCount());
			Thread.sleep(100);
		} while (threadPoolExecutor.getCompletedTaskCount() < 10);
	}

	/**
	 * Es la misma prueba que la anterior, pero en este caso hay tres suscritos al channel y además el channel tiene la
	 * propiedad de apply-secuence a true lo que significa que cada mensaje viene con un número de secuencia correlativo
	 * en la cabecera, es decir como son 3 suscriptores los número de secuencia de los mensajes cada vez que se envía un
	 * mensaje será 1,2,3 y así para cada mensaje. Si no se especifica esta propiedad el channel envia las mismas
	 * instancias de mensaje; en este caso (como se ha puesto la propiedad apply-secuence a true) el channel crea nuevas
	 * instancias de mensajes con la misma referencia de payload pero diferentes valores de headers.
	 * 
	 * @throws Exception
	 */
	@Ignore
	@Test
	public void testPublishSubscribeChannelWithExecutorAndSecuence() throws Exception {
		publishSusbcriberChannelWithSubscriptorSecuence = (MessageChannel) ((Advised) publishSusbcriberChannelWithSubscriptorSecuence)
				.getTargetSource().getTarget();

		// Lanzamos los hilos que envian los mensajes
		((PublishSubscribeChannel) publishSusbcriberChannelWithSubscriptorSecuence)
				.subscribe(myPublishSubscriberChannelHandlerC);
		((PublishSubscribeChannel) publishSusbcriberChannelWithSubscriptorSecuence)
				.subscribe(myPublishSubscriberChannelHandlerD);
		((PublishSubscribeChannel) publishSusbcriberChannelWithSubscriptorSecuence)
				.subscribe(myPublishSubscriberChannelHandlerE);

		ToChannelSender thread = null;
		for (int i = 0; i < 5; i++) {
			thread = (ToChannelSender) SpringApplicationContext.getBean("toChannelSender");
			thread.setPublishSusbcriberChannelWithSubscriptor(publishSusbcriberChannelWithSubscriptorSecuence);
			thread.start();
		}
		// Chequeamos como va el task executor
		ThreadPoolExecutor threadPoolExecutor = ((ThreadPoolTaskExecutor) taskExecutor).getThreadPoolExecutor();
		do {
			System.out.println("Hilos ejecutados: " + threadPoolExecutor.getCompletedTaskCount());
			System.out.println("Hilos activos: " + threadPoolExecutor.getActiveCount());
			Thread.sleep(1000);
		} while (threadPoolExecutor.getCompletedTaskCount() < 25);
	}
}
