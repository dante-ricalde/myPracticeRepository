package spring.integration.test.junit.timer;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.endpoint.PollingConsumer;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;

import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class TimerTest extends AbstractTest /* implements ApplicationListener<ContextStartedEvent> */{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimerTest.class);

	@Autowired
	private SourcePollingChannelAdapter simpleInboundChannelAdapter;

	@Autowired
	private SourcePollingChannelAdapter inboundChannelAdapterWithCron;

	@Autowired
	private SourcePollingChannelAdapter inboundChannelAdapterWithMaxMessagesPerPoll;

	@Autowired
	private SourcePollingChannelAdapter inboundChannelAdapterWithMaxMessagesPerPollReturningANullValueTo5thTimeOfExectingCreateMessageMethod;

	@Autowired
	private SourcePollingChannelAdapter inboundChannelAdapterWithMaxMessagesPerPollNegative;

	@Autowired
	private SourcePollingChannelAdapter inboundChannelAdapterWithMaxMessagesPerPollNegativeReturningANullValueTo5thTimeOfExectingCreateMessageMethod;

	@Autowired
	private SourcePollingChannelAdapter outboundInboundChannelAdapter;

	@Autowired
	private SourcePollingChannelAdapter outboundInboundChannelAdapterWithMaxMessagesPerPollNegativeReturningANullValueTo5thTimeOfExectingCreateMessageMethod;

	@Autowired
	private SourcePollingChannelAdapter outboundSimpleInboundChannelAdapter;

	@Autowired
	private SourcePollingChannelAdapter bsimpleInboundChannelAdapter;

	@Autowired
	private PollingConsumer outboundChannelAdapterReceivingFromAPollableChannel;

	@Autowired
	private PollingConsumer serviceActivatorToTestOutboundChannelAdapter;

	@Autowired
	private SourcePollingChannelAdapter b2simpleInboundChannelAdapter;

	@Autowired
	private ConfigurableApplicationContext ctx;

	@Before
	public void makethisBefore() {
		startOutboundChannelAdapter();
		startInboundChannelAdapter();
	}

	private void startOutboundChannelAdapter() {
		// outboundChannelAdapterReceivingFromAPollableChannel.start();
		// serviceActivatorToTestOutboundChannelAdapter.start();
		// channelAdapterToTestAnOutboundChannelAdapterWithoutChannelReference.start();
	}

	private void startInboundChannelAdapter() {
		// inboundChannelAdapterWithMaxMessagesPerPollNegative.start();
		// simpleInboundChannelAdapter.start();
		// inboundChannelAdapterWithMaxMessagesPerPoll.start();
		// inboundChannelAdapterWithMaxMessagesPerPollReturningANullValueTo5thTimeOfExectingCreateMessageMethod.start();
		// inboundChannelAdapterWithMaxMessagesPerPollNegativeReturningANullValueTo5thTimeOfExectingCreateMessageMethod
		// .start();
		// inboundChannelAdapterWithCron.start();
		// outboundInboundChannelAdapter.start();
		// outboundInboundChannelAdapterWithMaxMessagesPerPollNegativeReturningANullValueTo5thTimeOfExectingCreateMessageMethod
		// .start();
		// outboundSimpleInboundChannelAdapter.start();
		// inboundChannelAdapterToTestAnOutboundChannelAdapterWithoutChannelReference.start();
		// bsimpleInboundChannelAdapter.start();
		// b2simpleInboundChannelAdapter.start();
	}

	@Test
	public void testTimer() throws Exception {
		LOGGER.debug("Stopping ...");
		Thread.sleep(600000);// ctx.close()
	}

	// public void onApplicationEvent(ContextStartedEvent event) {
	// inboundChannelAdapterWithMaxMessagesPerPollNegative.stop();
	// simpleInboundChannelAdapter.stop();
	// inboundChannelAdapterWithMaxMessagesPerPoll.stop();
	// inboundChannelAdapterWithMaxMessagesPerPollReturningANullValueTo5thTimeOfExectingCreateMessageMethod.stop();
	// inboundChannelAdapterWithMaxMessagesPerPollNegativeReturningANullValueTo5thTimeOfExectingCreateMessageMethod
	// .stop();
	// inboundChannelAdapterWithCron.stop();
	// }
}
