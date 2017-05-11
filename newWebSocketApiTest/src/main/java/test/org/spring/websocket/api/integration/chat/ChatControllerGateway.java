package test.org.spring.websocket.api.integration.chat;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.Map;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import test.org.spring.websocket.api.model.ChatMessage;

/**
 * 
 * @author dr.ricalde
 *
 */
@MessagingGateway
@RequestMapping(value = "/chat")
@MessageMapping(value = "/chat")
@Controller
public interface ChatControllerGateway {

	/**
	 * Implement this as a interface method. this is going to be processed in a service activator. This method is not going to send the response to a
	 * destination, it will just return it
	 */

	@RequestMapping(value = "/welcome", method = POST)
	@ResponseBody
	@Gateway(requestChannel = "welcomeChannel")
	public List<String> greetMVC(ModelMap modelMap);

	@MessageMapping("/sendMessage/{sender}/{recipient}")
	@SendTo(value = "/exchange/chatDirectExchange/messages-{recipient}-{sender}")
	@Gateway(requestChannel = "chatMessageProcessorChannel")
	public ChatMessage receiveMessage(@Payload ChatMessage chatMessage, MessageHeaders headers);

	@SubscribeMapping("/getMessages/{queueName}")
	@Gateway(requestChannel = "chatMessagesProcessorChannel")
	public List<ChatMessage> getMessages(@DestinationVariable(value = "queueName") String queueName, MessageHeaders headers);
	
	@RequestMapping(value = "/admin", method = GET)
	@Gateway(requestChannel = "adminChannel")
	public String admin(ModelMap modelMap);

}
