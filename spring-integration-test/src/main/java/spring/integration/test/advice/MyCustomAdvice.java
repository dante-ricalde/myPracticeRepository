package spring.integration.test.advice;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.messaging.Message;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class MyCustomAdvice extends AbstractRequestHandlerAdvice {

	private Boolean throwExceptionAfterSending;

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCustomAdvice.class);

	@Override
	protected Object doInvoke(ExecutionCallback callback, Object target, Message<?> message) throws Exception {
		// add code before the invocation
		LOGGER.info("***************************** BEFORE INVOKING THE HANDLER **************************");
		Object result = callback.execute();
		LOGGER.info("***************************** AFTER INVOKING THE HANDLER ***************************");
		// add code after the invocation
		if (BooleanUtils.isTrue(throwExceptionAfterSending)) {
			if (Math.random() > 0.5) {
				LOGGER.info("******************** Exception provoked after sending the message:" + message);
				String a = "stringToProvokeTheException";
				a.charAt(1000);
			}
		}
		return result;
	}

	/**
	 * @return the throwExceptionAfterSending
	 */
	public Boolean getThrowExceptionAfterSending() {
		return throwExceptionAfterSending;
	}

	/**
	 * @param throwExceptionAfterSending
	 *            the throwExceptionAfterSending to set
	 */
	public void setThrowExceptionAfterSending(Boolean throwExceptionAfterSending) {
		this.throwExceptionAfterSending = throwExceptionAfterSending;
	}

}
