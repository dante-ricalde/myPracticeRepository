package spring.integration.test.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageJsonRowMapper implements RowMapper<Message<?>> {

	@Value(value = "#{getBeanFactory().containsBean('customObjectMapper') ? getBeanFactory().getBean('customObjectMapper') : null}")
	private ObjectMapper customObjectMapper;

	@Value(value = "#{getBeanFactory().containsBean('lobHandler') ? getBeanFactory().getBean('lobHandler') : null}")
	private LobHandler lobHandler;

	@Override
	public Message<?> mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message<?> result = null;
		try {
			customObjectMapper.readValue(lobHandler.getBlobAsBytes(rs, "MESSAGE_BYTES"), GenericMessage.class);
			// JsonNode readTree = customObjectMapper.readTree(lobHandler.getBlobAsBytes(rs, "MESSAGE_BYTES"));
			// result = (Message<?>) customObjectMapper.readValue(readTree, Object.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
