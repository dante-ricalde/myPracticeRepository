package spring.integration.test.junit;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.RendezvousChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.support.GenericMessage;

import spring.integration.test.receiver.ChannelReceiver;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class RendezvousChannelTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RendezvousChannelTest.class);

	@Autowired
	private MessageChannel rendezvousChannel;

	@Autowired
	private MessageChannel rendezvousChannelWithSeveralReceivers;

	@Autowired
	private MessageChannel rendezvousChannelWithReplyChannel;

	/**
	 * It Sends a message to a rendezvous channel. First, a receiver tries to receive a message from the channel but it
	 * stays blocked until a message is sent to the channel. The sender doesn't block because the receiver receives the
	 * message.
	 */
	public void testRendezvousChannel() {
		ChannelReceiver channelReceiver = SpringApplicationContext.getBean("channelReceiver");
		channelReceiver.setMessageChannel((PollableChannel) rendezvousChannel);
		channelReceiver.start();
		boolean sent = rendezvousChannel.send(new GenericMessage<String>("Sending a message to a rendezvous channel"));
		LOGGER.info("Was the message sent to the channel?: " + sent);
	}

	/**
	 * It Sends a message to a rendezvous channel. First, a receiver tries to receive a message from the channel but it
	 * stays blocked until a message is sent to the channel. The sender doesn't block because the receiver receives the
	 * message. Then a second receiver tries to receive a message from the channel but it doesn't get it because there
	 * are no messages in the channel queue. It doesn't stay blocked because It calls the receive method with a timeout
	 * of 0.
	 */
	public void testRendezvousChannelWithSeveralReceivers() {
		ChannelReceiver channelReceiver = SpringApplicationContext.getBean("channelReceiver");
		channelReceiver.setMessageChannel((PollableChannel) rendezvousChannelWithSeveralReceivers);
		channelReceiver.start();
		boolean sent = rendezvousChannelWithSeveralReceivers.send(new GenericMessage<String>(
				"Sending a message to a rendezvous channel"));
		LOGGER.info("Was the message sent to the channel?: " + sent);
		channelReceiver = SpringApplicationContext.getBean("channelReceiver");
		channelReceiver.setMessageChannel((PollableChannel) rendezvousChannelWithSeveralReceivers);
		channelReceiver.setTimeout(0L);
		channelReceiver.start();
	}

	/**
	 * In this test We send a message to a rendezvous channel, in the message header We specify a reply channel. The
	 * receiver when receives the message verifies if there is a reply channel, if there is the receiver sends a message
	 * to that reply channel. In this case We receives that reply message immediately after to send the message to the
	 * receiver.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRendezvousChannelWithReplyChannel() throws Exception {
		// start the channel receiver
		ChannelReceiver channelReceiver = SpringApplicationContext.getBean("channelReceiver");
		channelReceiver.setMessageChannel((PollableChannel) rendezvousChannelWithReplyChannel);
		channelReceiver.start();

		MessageBuilder<String> messageBuilder = MessageBuilder
				.withPayload("Sending a message to a rendezvous channel with a reply channel...");
		PollableChannel replyChannel = new RendezvousChannel();
		messageBuilder.setReplyChannel(replyChannel);
		// send the message
		boolean sent = rendezvousChannelWithReplyChannel.send(messageBuilder.build());
		LOGGER.info("Was the message sent to the channel?: " + sent);
		// receive the reply
		Message<?> reply = replyChannel.receive();
		LOGGER.info("Reply from the receiver: " + reply);
	}
}