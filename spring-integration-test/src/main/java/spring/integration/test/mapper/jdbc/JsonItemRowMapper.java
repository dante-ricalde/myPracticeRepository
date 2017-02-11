package spring.integration.test.mapper.jdbc;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class JsonItemRowMapper extends AbstractItemRowMapper {
	
	public JsonItemRowMapper() {
		super();
		this.messageRowMapper = SpringApplicationContext.getBean("messageJsonRowMapper");
	}

}
