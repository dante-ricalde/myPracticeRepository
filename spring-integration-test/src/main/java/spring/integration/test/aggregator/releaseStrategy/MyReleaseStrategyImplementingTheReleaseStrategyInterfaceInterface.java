package spring.integration.test.aggregator.releaseStrategy;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("myReleaseStrategyImplementingTheReleaseStrategyInterfaceInterface")
public class MyReleaseStrategyImplementingTheReleaseStrategyInterfaceInterface implements ReleaseStrategy {

	@Override
	public boolean canRelease(MessageGroup group) {
		Collection<Message<?>> messages = group.getMessages();
		if (CollectionUtils.isNotEmpty(messages)) {
			int i = 1;
			for (Message<?> message : messages) {
				message.getHeaders().put("MessageGroupId", group.getGroupId());
				if (i > 10) {
					return true;
				}
			}
		}
		return false;
	}

}
