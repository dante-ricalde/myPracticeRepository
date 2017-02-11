package spring.integration.test.advice.delayer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import spring.integration.test.interceptor.MyChannelInterceptor;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component
public class CustomDelayerAdviceBean implements MethodInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyChannelInterceptor.class);

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object proceedValue = null;
		LOGGER.info("Advice executed ...");
		try {
			proceedValue = invocation.proceed();
		} catch (Exception e) {
			LOGGER.error("Error captured in the adviced: " + e);
		}

		return proceedValue;

	}
}
