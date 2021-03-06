package spring.integration.test.router;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component(value = "recipientListRouter")
public class CustomRecipientListRouter extends AbstractMessageRouter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomRecipientListRouter.class);

	@Override
	protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
		// TODO Auto-generated method stub
		List<MessageChannel> channelsToWhichTheGivenMessageShouldBeRouted = null;
		try {
			channelsToWhichTheGivenMessageShouldBeRouted = SpringApplicationContext.getBean("destinationChannels" + "_"
					+ toString());
		} catch (NoSuchBeanDefinitionException e) {
			List<String> channelNamesToWhichTheGivenMessageShouldBeRouted = SpringApplicationContext
					.getBean("destinationChannelNames" + "_" + toString());
			if (CollectionUtils.isNotEmpty(channelNamesToWhichTheGivenMessageShouldBeRouted)) {
				channelsToWhichTheGivenMessageShouldBeRouted = new ArrayList<MessageChannel>();
				for (String channelName : channelNamesToWhichTheGivenMessageShouldBeRouted) {
					try {
						channelsToWhichTheGivenMessageShouldBeRouted.add((MessageChannel) SpringApplicationContext
								.getBean(channelName));
					} catch (NoSuchBeanDefinitionException e2) {
						LOGGER.info("channel not found: " + channelName);
					}
				}
			}
		}
		return channelsToWhichTheGivenMessageShouldBeRouted;
	}
}
