package mju.ntj.ejis.commons.aspects.impl;

import java.util.Arrays;
import java.util.List;

import mju.ntj.ejis.commons.aspects.ServiceAspect;
import mju.ntj.ejis.commons.constants.CommonsConstants;
import mju.ntj.ejis.commons.manager.ConfigurationManager;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Aspect
public class ServiceAspectImpl implements ServiceAspect, Ordered {

	@Autowired
	private ConfigurationManager configurationManager;

	/** The Logger. */
	private static final Logger log = LoggerFactory.getLogger(ServiceAspectImpl.class);

	/**
	 * Re attempt operation.
	 * 
	 * @param joinPoint
	 *            the join point
	 * @return the object
	 * @throws Throwable
	 *             the throwable
	 */
	public Object reAttemptOperation(ProceedingJoinPoint joinPoint) throws Throwable {
		Exception lockFailureException;
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getTarget().getClass().getName();
		String signatureName = className + "." + methodName + ".";

		String numAttempts = configurationManager.getDataValueWithoutErrorLogIfNotExists(signatureName
				+ CommonsConstants.NUMERO_INTENTOS);
		String timeBetweenAttemp = configurationManager.getDataValueWithoutErrorLogIfNotExists(signatureName
				+ CommonsConstants.TIEMPO_ENTRE_INTENTOS_EN_SEGUNDOS);
		int numAttemptsInt = 0;
		long timeBetweenAttempLong = 0;
		if (StringUtils.isNotBlank(numAttempts) && StringUtils.isNotBlank(timeBetweenAttemp)) {
			numAttemptsInt = Integer.valueOf(numAttempts);
			timeBetweenAttempLong = Long.valueOf(timeBetweenAttemp);
		}
		do {
			try {
				return joinPoint.proceed();
			} catch (Exception ex) {
				lockFailureException = ex;
				// Comprobamos si hay codigo para reintentar
				numAttemptsInt = processCodes(ex, signatureName, numAttemptsInt, timeBetweenAttempLong,
						CommonsConstants.CODES_FOR_RETRY);
				// Comprobamos si hay codigo para no reintentar
				numAttemptsInt = processCodes(ex, signatureName, numAttemptsInt, timeBetweenAttempLong,
						CommonsConstants.CODES_FOR_NOT_RETRY);
				log.info("Quedan {} reintentos en {}", numAttemptsInt, signatureName);
				Thread.sleep(timeBetweenAttempLong * 1000);
			}
		} while (numAttemptsInt-- > 1);
		throw lockFailureException;
	}

	/**
	 * Process codes.
	 * 
	 * @param ex
	 *            the ex
	 * @param signatureName
	 *            the signature name
	 * @param numAttemptsInt
	 *            the num attempts int
	 * @param timeBetweenAttempLong
	 *            the time between attemp long
	 * @param codeNames
	 *            the code names
	 * @return the int
	 * @throws Throwable
	 *             the throwable
	 */
	private int processCodes(Exception ex, String signatureName, int numAttemptsInt, long timeBetweenAttempLong,
			String codeNames) throws Throwable {
		String codeValues = configurationManager.getDataValueWithoutErrorLogIfNotExists(signatureName + codeNames);
		if (StringUtils.isNotBlank(codeValues)) {
			String[] codeValuesArray = StringUtils.split(codeValues, ",");
			List<String> codeValuesList = Arrays.asList(StringUtils.stripAll(codeValuesArray));
			if ((!codeValuesList.contains(ex.getMessage()) && CommonsConstants.CODES_FOR_NOT_RETRY
					.equalsIgnoreCase(codeNames))
					|| (codeValuesList.contains(ex.getMessage()) && CommonsConstants.CODES_FOR_RETRY
							.equalsIgnoreCase(codeNames)))
				return numAttemptsInt;
			else
				return 0;
		}
		return numAttemptsInt;
	}

	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}
