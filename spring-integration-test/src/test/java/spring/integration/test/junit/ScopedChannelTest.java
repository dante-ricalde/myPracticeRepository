package spring.integration.test.junit;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import spring.integration.test.receiver.ChannelReceiver;
import spring.integration.test.sender.ToChannelSender;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class ScopedChannelTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScopedChannelTest.class);

	@Autowired
	private MessageChannel threadLocalChannel;

	@Autowired
	private MessageChannel threadLocalChannelWithAQueue;

	@Autowired
	private PollableChannel threadLocalChannelWithAQueueAndReplyChannel;

	@Autowired
	private PollableChannel terminalChannel;

	/**
	 * It tests to send a message to a scoped direct channel. In this case we are using a service activator for handling
	 * the message, otherwise it would throw a exception of "dispatcher has no subscribers for this channel". The direct
	 * channel besides to be the simplest point-to-point channel option, enables a single thread to perform the
	 * operations on both sides of the channel, in this case when it calls the method send, it executes the service
	 * activator "handler message" method immediately after, before the method "send" returns.
	 */
	@Test
	@Ignore
	public void testThreadLocalScopedChannel() {
		boolean sent = threadLocalChannel.send(new GenericMessage<String>("Sending a message to a scoped channel..."));
		LOGGER.info("Message sent to a scoped channel: " + sent);
	}

	/**
	 * We send a message to a channel with a queue scoped a thread local, later we attempt receiving the message in the
	 * same thread that sent the message to the channel, and We receive it successfully. The result obtained by this
	 * test is correct as this channel has been scoped a thread local and when a channel is scoped a thread, the thread
	 * that sends to the channel will later be able to receive those same messages, but no other thread would be able to
	 * access them.
	 */
	@Test
	@Ignore
	public void testThreadLocalScopedChannelWithAQueue() {
		boolean sent = threadLocalChannelWithAQueue.send(new GenericMessage<String>(
				"Sending a message to a thread local scoped channel with a queue"));
		LOGGER.info("Message sento to a thread local scoped channel with a queue: " + sent);
		Message<?> messagesReceived = ((PollableChannel) threadLocalChannelWithAQueue).receive();
		LOGGER.info("Message received:" + messagesReceived);
	}

	/**
	 * We send a message to a channel with a queue scoped a thread local. We attempt receiving the message in another
	 * thread but We can't receive the message because can only be received by the thread that sent the message. When we
	 * attempt receiving the message, we get the following message
	 * "SimpleThreadScope does not support destruction callbacks. Consider using a RequestScope in a Web environment" y
	 * we get a null value as message received. The result obtained by the test is correct as this channel has been
	 * scoped a thread local, and when a channel is scoped a thread, the thread that sends to the channel will later be
	 * able to receive those same messages, but no other thread would be able to access them.
	 */
	@Test
	@Ignore
	public void testThreadLocalScopedChannelWithAQueueReceivingTheMessageInAnotherThread() {
		boolean sent = threadLocalChannelWithAQueue.send(new GenericMessage<String>(
				"Sending a message to a thread local scoped channel with a queue"));
		LOGGER.info("Message sent to a thread local scoped channel with a queue: " + sent);
		ChannelReceiver channelReceiver = SpringApplicationContext.getBean("channelReceiver");
		channelReceiver.setMessageChannel((PollableChannel) threadLocalChannelWithAQueue);
		channelReceiver.setTimeout(0L);
		channelReceiver.start();
	}

	/**
	 * We send a message to a channel with a queue scoped a thread local. When We receive the message in the same thread
	 * We verify if it contains a reply channel, after that, We send a message to that reply channel, then, in the same
	 * thread We receive the reply message. This test is correct because they both the channel sender and reply channel
	 * are scoped a thread and the send and received invocation methods are invoked in the same thread.
	 */
	@Ignore
	@Test
	public void testThreadLocalScopedChannelWithAQueueAndReplyChannel() {
		MessageBuilder<String> messageBuilder = MessageBuilder
				.withPayload("Sending a message to a thread local scoped channel with a queue and reply channel ...");
		messageBuilder.setReplyChannel(terminalChannel);
		boolean sent = threadLocalChannelWithAQueueAndReplyChannel.send(messageBuilder.build());
		LOGGER.info("Message sent to a thread local scoped channel with a queue and reply channel: " + sent);
		Message<?> messageReceived = threadLocalChannelWithAQueueAndReplyChannel.receive();
		MessageChannel replyChannel = (MessageChannel) messageReceived.getHeaders().getReplyChannel();
		boolean sentToSender = replyChannel.send(new GenericMessage<String>(
				"Response to a thread local scoped channel with a queue and reply channel"));
		LOGGER.info("Response sent to a thread locadl channel with a queue and reply channel: " + sentToSender);
		Message<?> replyMessageReceived = terminalChannel.receive();
		LOGGER.info("Reply message received: " + replyMessageReceived);
	}

	/**
	 * We send a message to a channel with a queue scoped a thread local. When We received the message in the same
	 * thread We verify if it contains a reply channel, after that, We send a message to that reply channel in another
	 * thread, then, in the same of the sender thread We attempt receiving the reply message but We can't get the
	 * message (We receive a null value as reply message and for that this occurs, We specify a timeout in the
	 * invocation of the receive method of the reply channel, otherwise the thread would block) because They both the
	 * channel sender and the reply channel are scoped a thread and the invocation of the send method of the reply
	 * channel occurs in another thread and all the invocations (send and receive from the sender channel and the reply
	 * channel) should be invoked in the same thread.
	 */
	@Ignore
	@Test
	public void testThreadLocalScopedChannelWithAQueueAndReplyChannelSendingTheReplyMessageInAnotherThread() {
		MessageBuilder<String> messageBuilder = MessageBuilder
				.withPayload("Sending a message to a thread local scoped channel with a queue and reply channel ...");
		messageBuilder.setReplyChannel(terminalChannel);
		boolean sent = threadLocalChannelWithAQueueAndReplyChannel.send(messageBuilder.build());
		LOGGER.info("Message sent to a thread local scoped channel with a queue and reply channel: " + sent);
		Message<?> messageReceived = threadLocalChannelWithAQueueAndReplyChannel.receive();
		MessageChannel replyChannel = (MessageChannel) messageReceived.getHeaders().getReplyChannel();
		ToChannelSender toChannelSender = SpringApplicationContext.getBean("toChannelSender");
		toChannelSender.setPublishSusbcriberChannelWithSubscriptor(replyChannel);
		toChannelSender.start();
		// boolean sentToSender = replyChannel.send(new GenericMessage<String>(
		// "Response to a thread local scoped channel with a queue and reply channel"));
		LOGGER.info("Response sent to a thread local channel with a queue and reply channel ...");
		Message<?> replyMessageReceived = terminalChannel.receive(2000);
		LOGGER.info("Reply message received: " + replyMessageReceived);
	}
}
