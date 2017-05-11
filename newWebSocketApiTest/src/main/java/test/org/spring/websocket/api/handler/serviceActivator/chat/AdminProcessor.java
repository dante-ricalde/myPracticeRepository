package test.org.spring.websocket.api.handler.serviceActivator.chat;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Dante Ricalde
 *
 */
@MessageEndpoint
public class AdminProcessor {

	@ServiceActivator(inputChannel = "adminChannel")
	public String admin() {
		ModelAndView result = new ModelAndView();
		//result.setViewName("chat/admin");
//		return result;
		return "chat/admin";
	}
}
