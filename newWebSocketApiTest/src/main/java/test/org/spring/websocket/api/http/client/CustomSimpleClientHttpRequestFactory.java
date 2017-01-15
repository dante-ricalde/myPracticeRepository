package test.org.spring.websocket.api.http.client;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Dante Ricalde
 *
 */
@Component
public class CustomSimpleClientHttpRequestFactory extends SimpleClientHttpRequestFactory {

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);
		// Basic Authentication for Police API
		String authorisation = "guest" + ":" + "guest";
		// byte[] encodedAuthorisation = ;
		connection.setRequestProperty("Authorization", "Basic Z3Vlc3Q6Z3Vlc3Q=");
		this.setBufferRequestBody(false);
		this.setConnectTimeout(15 * 1000);
		this.setReadTimeout(15 * 1000);
	}
}
