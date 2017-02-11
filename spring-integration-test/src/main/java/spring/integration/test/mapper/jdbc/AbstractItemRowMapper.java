package spring.integration.test.mapper.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.messaging.Message;

import spring.integration.test.var.Item;

/**
 * 
 * @author dr.ricalde
 * 
 */
public abstract class AbstractItemRowMapper extends BeanPropertyRowMapper<Item> {
	
	protected RowMapper<Message<?>> messageRowMapper;

	public AbstractItemRowMapper() {
		super();
		setMappedClass(Item.class);
	}
	
	
	
	@Override
	public Item mapRow(ResultSet rs, int rowNumber) throws SQLException {
		// TODO Auto-generated method stub
		Item result = super.mapRow(rs, rowNumber);
		result.setMessage(messageRowMapper.mapRow(rs, rowNumber));
		return result;
	}

}
