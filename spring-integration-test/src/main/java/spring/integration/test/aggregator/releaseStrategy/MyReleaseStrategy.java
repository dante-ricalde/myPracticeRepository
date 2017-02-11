package spring.integration.test.aggregator.releaseStrategy;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("myReleaseStrategy")
public class MyReleaseStrategy {

	@ReleaseStrategy
	public boolean canMessagesBeReleased(List<String> messagesUnmarked) {
		for (String payload : messagesUnmarked) {
			if (StringUtils.endsWith(payload, "4")) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}
