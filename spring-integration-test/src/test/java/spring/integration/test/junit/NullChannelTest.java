package spring.integration.test.junit;

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

public class NullChannelTest extends AbstractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(NullChannelTest.class);

	@Autowired
	private MessageChannel nullChannel;

	@Autowired
	private MessageChannel ChannelToTestNullChannel;

	@Autowired
	private MessageChannel replyChannelToTestNullChannel;

	@Ignore
	@Test
	public void testNullChannel() {
		boolean sent = nullChannel.send(new GenericMessage<String>("Sending a message to the null channel ..."));
		LOGGER.info("Message sent to the null channel: " + sent);
		Message<?> messageReceived = ((PollableChannel) nullChannel).receive();
		LOGGER.info("Message received from the null channel: " + messageReceived);
	}

	@Ignore
	@Test
	public void testNullChannelForAReplyChannelThatDoesntExist() {
		MessageBuilder<String> messageBuilder = MessageBuilder
				.withPayload("Sending a message to a channel to test the null channel");
		messageBuilder.setReplyChannel(replyChannelToTestNullChannel);
		boolean sent = ChannelToTestNullChannel.send(messageBuilder.build());
		LOGGER.info("Message sent to the channel used to test the null channel: " + sent);
	}
}
