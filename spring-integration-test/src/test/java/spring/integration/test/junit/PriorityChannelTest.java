package spring.integration.test.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class PriorityChannelTest extends AbstractTest {

	@Autowired
	private MessageChannel priorityChannel;

	@Autowired
	private MessageChannel priorityChannelWithComparator;

	/**
	 * Send messages to a priority channel with 20 of capacity. We send 30 messages to the channel, but only 20 were
	 * sent because the channel size is 20. When We send the messages We specify the timeout to 0 to avoid sender
	 * blocking, the same We do when We receive the messages. By default, the messages are received in descending order
	 * by consulting of the priority in the message header. If the messages don't have a priority in their header the
	 * Messages are received in the same order that they were sent.
	 */
	@Test
	public void testPriorityChannel() {
		// Build the messages
		List<Message<String>> messages = getMessages();
		// Send the messages
		sendMessages(messages, priorityChannel);
		// Receive the messages
		receiveMessages(messages, priorityChannel);
	}

	/**
	 * Send messages to a priority channel with 20 of capacity. We send 30 messages to the channel, but only 20 were
	 * sent because the channel size is 20. When We send the messages We specify the timeout to 0 to avoid sender
	 * blocking, the same We do when We receive the messages. In this case we use a comparator for this channel to
	 * indicate that the order must be ascending when We receive the messages.
	 */
	@Test
	public void testPriorityChannelWithComparator() {
		// Build the messages
		List<Message<String>> messages = getMessages();
		// Send the messages
		sendMessages(messages, priorityChannelWithComparator);
		// Receive the messages
		receiveMessages(messages, priorityChannelWithComparator);
	}

	private void receiveMessages(List<Message<String>> messages, MessageChannel priorityChannel) {
		Message<?> message = null;
		for (int i = 0; i < messages.size(); i++) {
			message = ((PollableChannel) priorityChannel).receive(0);
			System.out.println("Message received: " + message);
		}

	}

	private void sendMessages(List<Message<String>> messages, MessageChannel priorityChannel) {
		boolean sended = false;
		for (Message<String> message : messages) {
			sended = priorityChannel.send(message, 0);
			IntegrationMessageHeaderAccessor messageHeaderAccesor = new IntegrationMessageHeaderAccessor(message);
			System.out.println("*** PriorityChannel: Mensaje con prioridad: " + messageHeaderAccesor.getPriority()
					+ ". Enviado: " + sended);
		}
	}

	private List<Message<String>> getMessages() {
		List<Message<String>> messages = new ArrayList<Message<String>>();
		Integer priority = 0;
		MessageBuilder<String> messageBuilder = null;
		for (int i = 0; i < 30; i++) {
			priority = (int) Math.round(Math.random() * 10);
			messageBuilder = MessageBuilder.withPayload("Mensaje enviado a un priority channel ...Con priority + "
					+ priority);
			messageBuilder.setPriority(priority);
			messages.add(messageBuilder.build());
		}
		return messages;
	}
}
