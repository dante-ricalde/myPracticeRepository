package spring.integration.test.gemfire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author dr.ricalde
 * 
 */
public class StartServer {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"config/gemfire/application-Context-gemfire-server.xml");
		System.out.println("Starting Server");
	}
}
