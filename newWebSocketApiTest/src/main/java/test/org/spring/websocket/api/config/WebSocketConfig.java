package test.org.spring.websocket.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@IntegrationComponentScan(basePackages = "test.org.spring.websocket.api")
@ComponentScan(basePackages = "test.org.spring.websocket.api")
@EnableIntegration
public class WebSocketConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);

}