package spring.integration.test.filter.withAnnotations;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;

import spring.integration.test.var.PaymentType;

/**
 * 
 * @author dr.ricalde
 * 
 */
@MessageEndpoint(value = "myFilterWithAnnotations")
public class MyFilterWithAnnotations {

	@Filter
	public boolean cashOnly(PaymentType paymentType) {
		if (spring.integration.test.var.enums.PaymentType.valueOf(paymentType.getPaymentType()).equals(
				spring.integration.test.var.enums.PaymentType.CASH)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
