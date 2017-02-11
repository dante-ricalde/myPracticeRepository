package spring.integration.test.event;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class BlackListEvent extends ApplicationEvent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2987306004952260196L;

	private String address;

	private String text;

	public BlackListEvent(String address, String text) {
		super(new String[] { address, text });
		this.address = address;
		this.text = text;
	}

	public BlackListEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "BlackListEvent [address=" + address + ", text=" + text + "]";
	}

}
