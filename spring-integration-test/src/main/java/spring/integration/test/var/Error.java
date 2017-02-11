package spring.integration.test.var;

// TODO: Auto-generated Javadoc
/**
 * The Class Error.
 * 
 * @author dr.ricalde
 */
public class Error {

	/** The code. */
	private String code;

	/** The descrption. */
	private String descrption;

	/**
	 * Instantiates a new error.
	 */
	public Error() {
		super();
	}

	/**
	 * Instantiates a new error.
	 * 
	 * @param code
	 *            the code
	 * @param descrption
	 *            the descrption
	 */
	public Error(String code, String descrption) {
		super();
		this.code = code;
		this.descrption = descrption;
	}

	/**
	 * Gets the code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 * 
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the descrption.
	 * 
	 * @return the descrption
	 */
	public String getDescrption() {
		return descrption;
	}

	/**
	 * Sets the descrption.
	 * 
	 * @param descrption
	 *            the descrption to set
	 */
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	@Override
	public String toString() {
		return "Error [code=" + code + ", descrption=" + descrption + "]";
	}

}
