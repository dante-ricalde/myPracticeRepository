package spring.integration.test.mapper.factory;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class ObjectMapperFactory {

	/**
	 * Gets the mapper.
	 * 
	 * @return the mapper
	 */
	public static ObjectMapper getMapper() {
		ObjectMapper result = new ObjectMapper();
		result.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		return result;
	}

}
