package test.org.spring.websocket.api.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 * @author dr.ricalde
 *
 */
@Controller
@EnableWebMvc
public class GreetingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

	@Lazy
	@Autowired
	@Qualifier("org.springframework.messaging.simp.SimpMessagingTemplate#0")
	private SimpMessagingTemplate template;

//	@Autowired(required = false)
//	public GreetingController(@Qualifier(value = "org.springframework.messaging.simp.SimpMessagingTemplate#0") SimpMessagingTemplate template) {
//		this.template = template;
//	}

	@MessageMapping("/greeting")
	public String handle(String greeting) {
		LOGGER.debug("Handling message: '{}'", greeting);
		return "[" + Calendar.getInstance().getTime() + ":" + greeting;
	}

	@SubscribeMapping("/subscribe")
	public List<String> subscribe() {
		LOGGER.debug("Handling subscribe request ...");
		return new ArrayList<String>(
				Arrays.asList("Data when the application UI is being initialized", "Dantito", "Danielito", "Keluchis"));
	}

	@SubscribeMapping("/subscribeAndSendTo")
	@SendTo(value = "/topic/subscribeAndSendToResponse")
	public List<String> subscribeAndSendTo() {
		LOGGER.debug("Handling subscribe request at /subscribeAndSendTo ...");
		return new ArrayList<String>(
				Arrays.asList("Data when the application UI is being initialized", "Dantito", "Danielito", "Keluchis"));
	}

	@MessageMapping(value = "/greetings")
	public void greet(String greeting) {
		LOGGER.debug("Handling message request at /app/greetings ...");
		String text = "[" + Calendar.getInstance().getTime() + "]:" + greeting;
		this.template.convertAndSend("/topic/greetings", text);
	}
	
	@MessageMapping(value = "/chat")
	public void chat(String msg) {
		LOGGER.debug("Handling message request at /appBrokerRelay/greetings ...");
		this.template.convertAndSend("/topic/greetings", msg);
	}

	@RequestMapping(value = "/greetingsMVC", method = POST)
	public ModelAndView greetMVC(@RequestParam(value = "greeting") String greeting) {
		LOGGER.debug("Handling message request at /anyUrlPattern/greetingsMVC ...:" + greeting);
		String text = "[" + Calendar.getInstance().getTime() + "]:" + greeting;
		this.template.convertAndSend("/topic/greetings", text);
		return null;
	}

	// @RequestMapping(value = "/sendingMessgagesStompSample")
	// public String greetMVC() {
	// return "sendingMessgagesStompSample";
	// }
}
