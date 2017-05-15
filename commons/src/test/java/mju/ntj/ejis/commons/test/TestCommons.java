package mju.ntj.ejis.commons.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-commons-test.xml" })
public class TestCommons extends TestCase {

	@Autowired
	private PrintTexto printTexto;

	@Test(expected = Exception.class)
	public void testAspectsRetry() throws Exception {
		printTexto.printTexto();
	}

	@Test
	public void printTextoWithNoRetryCode() throws Exception {
		for (int i = 0; i < 5; i++) {
			try {
				printTexto.printTextoWithNoRetryCode();
			} catch (Exception e) {
			}
		}
	}

	@Test
	public void testRemplaceString() throws Exception {
		String prueba = "${dentro}";
		int inicio = prueba.indexOf("${");
		int fin = prueba.indexOf("}");
		System.out.println(inicio);
		System.out.println(fin);
		System.out.println(prueba.substring(inicio + 2, fin));

		prueba = "izquierda${dentro}derecha";
		inicio = prueba.indexOf("${");
		fin = prueba.indexOf("}");
		System.out.println(inicio);
		System.out.println(fin);
		System.out.println(prueba.substring(0, inicio).concat(
				prueba.substring(inicio + 2, fin).concat(prueba.substring(fin + 1, prueba.length()))));
	}
}
