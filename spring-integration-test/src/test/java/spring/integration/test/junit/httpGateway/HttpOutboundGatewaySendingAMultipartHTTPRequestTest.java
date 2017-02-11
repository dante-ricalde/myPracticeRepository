package spring.integration.test.junit.httpGateway;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import spring.integration.test.junit.AbstractTest;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Ignore
public class HttpOutboundGatewaySendingAMultipartHTTPRequestTest extends AbstractTest {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(HttpOutboundGatewaySendingAMultipartHTTPRequestTest.class);

	/**
	 * ************** Multipart HTTP request - RestTemplate (client) and Http Inbound Gateway (server).*****************
	 * This example demonstrates how simple it is to send a Multipart HTTP request via Spring's RestTemplate and receive
	 * it with a Spring Integration HTTP Inbound Adapter (it doesn't return a response message). All we are doing is
	 * creating a MultiValueMap and populating it with multi-part data. The RestTemplate will take care of the rest (no
	 * punintended) by converting it to a MultipartHttpServletRequest . This particular client will send a multipart
	 * HTTP Request which contains the name of the company as well as an image file with the company logo.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testSendingAMultipartHTTPRequest() {
		RestTemplate template = new RestTemplate();
		String uri = "http://localhost:8080/spring-integration-application-sample-webapp/springIntegrationSample/inboundAdapter.htm";
		Resource s2logo = new ClassPathResource("images/spring09_logo.png");
		MultiValueMap map = new LinkedMultiValueMap<>();
		map.add("company", "SpringSource");
		map.add("company-logo", s2logo);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("multipart", "form-data"));
		HttpEntity request = new HttpEntity<>(map, headers);
		Object[] uriVariables = new Object[0];
		Class responseType = null;
		ResponseEntity<?> httpResponse = template.exchange(uri, HttpMethod.POST, request, responseType, uriVariables);
		LOGGER.info("Http Response: " + httpResponse);
	}

}
