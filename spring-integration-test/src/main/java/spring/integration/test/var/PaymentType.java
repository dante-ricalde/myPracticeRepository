package spring.integration.test.var;

import java.io.Serializable;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class PaymentType implements Serializable {

	private static final long serialVersionUID = -8575152536538424851L;

	/** The paymen type. */
	private String paymentType;

	/**
	 * Instantiates a new payment type.
	 * 
	 * @param paymentType
	 *            the payment type
	 */
	public PaymentType(String paymentType) {
		super();
		this.paymentType = paymentType;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType
	 *            the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "PaymentType [paymentType=" + paymentType + "]";
	}

}
