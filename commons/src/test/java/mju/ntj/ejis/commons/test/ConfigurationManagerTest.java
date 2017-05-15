package mju.ntj.ejis.commons.test;

import junit.framework.TestCase;

import mju.ntj.ejis.commons.manager.ConfigurationManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-commons-test.xml" })
public class ConfigurationManagerTest extends TestCase {

	@Autowired
	private ConfigurationManager configurationManager;

	//	prueba1=prueba1
	//	prueba2=${prueba1}
	//	prueba3=IZQ${prueba1}DER
	//	prueba4=${prueba3}
	//	prueba5=IZQ${prueba2}
	//	prueba6=${prueba2}DER

	@Test
	public void testRemplaceString() throws Exception {

		System.out.println(configurationManager.getDataValue("prueba1"));
		assertEquals("prueba1", configurationManager.getDataValue("prueba1"));

		System.out.println(configurationManager.getDataValue("prueba2"));
		assertEquals("prueba1", configurationManager.getDataValue("prueba2"));

		System.out.println(configurationManager.getDataValue("prueba3"));
		assertEquals("IZQprueba1DER", configurationManager.getDataValue("prueba3"));

		System.out.println(configurationManager.getDataValue("prueba4"));
		assertEquals("IZQprueba1DER", configurationManager.getDataValue("prueba4"));

		System.out.println(configurationManager.getDataValue("prueba5"));
		assertEquals("IZQprueba1", configurationManager.getDataValue("prueba5"));

		System.out.println(configurationManager.getDataValue("prueba6"));
		assertEquals("prueba1DER", configurationManager.getDataValue("prueba6"));

	}
}
