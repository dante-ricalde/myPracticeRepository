package spring.integration.test.router.withAnnotations;

import java.util.ArrayList;
import java.util.List;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.MessageChannel;

import spring.integration.test.var.MyMessagePayload;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint(value = "myRouterWithAnnotations")
public class MyRouterWithAnnotations {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyRouterWithAnnotations.class);

	@Router(inputChannel = "iinputDirectChannelToTestARouterConfiguredWithAnnotations")
	public List<MessageChannel> route(MyMessagePayload myMessagePayload,
			@Header(value = "myMessagePayload", required = false) MyMessagePayload header) {
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

	@Override
	public String toString() {
		return "myRouterWithAnnotations";
	}

}
