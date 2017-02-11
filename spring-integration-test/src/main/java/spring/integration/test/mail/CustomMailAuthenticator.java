package spring.integration.test.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 * @author dr.ricalde
 *
 */
public class CustomMailAuthenticator extends Authenticator {
	
	private final String user;
	
	private final String password;

	public CustomMailAuthenticator(final String user, final String password) {
		super();
		this.user = user;
		this.password = password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(user, password);
	}
}
