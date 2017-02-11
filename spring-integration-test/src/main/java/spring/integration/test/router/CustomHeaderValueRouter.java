package spring.integration.test.router;

import java.util.List;
import java.util.Map;

import org.springframework.integration.router.HeaderValueRouter;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("customHeaderValueRouter")
public class CustomHeaderValueRouter extends HeaderValueRouter {

	
	public CustomHeaderValueRouter() {
		super("headerValueToDetermineADestinationChannel");
	}
	
	@Override
	public List<Object> getChannelKeys(Message<?> message) {
		// TODO Auto-generated method stub
		return super.getChannelKeys(message);
	}
	
	@Override
	public Map<String, String> getChannelMappings() {
		// TODO Auto-generated method stub
		return super.getChannelMappings();
	}
	
	
	@Override
	@ManagedOperation
	public void setChannelMapping(String key, String channelName) {
		// TODO Auto-generated method stub
		super.setChannelMapping(key, channelName);
	}
	
	@Override
	@ManagedOperation
	public void removeChannelMapping(String key) {
		// TODO Auto-generated method stub
		super.removeChannelMapping(key);
	}
	
	

}
