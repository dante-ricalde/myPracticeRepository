package spring.integration.test.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dr.ricalde
 * 
 */
@Component("strToInt")
public class StringToIntegerConverter implements Converter<String, Integer> {

	public Integer convert(String source) {
		return Integer.parseInt(source);
	}

}
