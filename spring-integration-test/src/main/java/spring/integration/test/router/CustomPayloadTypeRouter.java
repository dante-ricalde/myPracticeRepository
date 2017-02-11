package spring.integration.test.router;

import java.util.Collection;
import java.util.List;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component(value = "payloadTypeRouter")
public class CustomPayloadTypeRouter extends AbstractMessageRouter {

	@Override
	protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
		// TODO Auto-generated method stub
		List<MessageChannel> channelsToWhichTheGivenMessageShouldBeRouted = SpringApplicationContext
				.getBean("destinationChannels" + "_" + toString());
		return channelsToWhichTheGivenMessageShouldBeRouted;
	}

}
