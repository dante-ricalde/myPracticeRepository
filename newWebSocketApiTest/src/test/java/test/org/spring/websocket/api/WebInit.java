package test.org.spring.websocket.api;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

@Component
public class WebInit implements WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebInit.class);

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// What to do here, to move from XML to java config
		LOGGER.debug("Web application Initializer ...");
	}
}