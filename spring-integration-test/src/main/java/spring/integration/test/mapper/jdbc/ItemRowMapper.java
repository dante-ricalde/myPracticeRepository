package spring.integration.test.mapper.jdbc;

import mju.ntj.ejis.commons.context.SpringApplicationContext;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class ItemRowMapper extends AbstractItemRowMapper {

	public ItemRowMapper() {
		super();
		this.messageRowMapper = SpringApplicationContext.getBean("messageRowMapper");
	}

}
