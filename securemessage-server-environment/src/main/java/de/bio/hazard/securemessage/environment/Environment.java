package de.bio.hazard.securemessage.environment;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
@Scope(value="singleton")
public class Environment {

	private static AbstractApplicationContext applicationContext;

	public static Environment getInstance() {
		if (applicationContext == null) {

			applicationContext = new ClassPathXmlApplicationContext(
					"applicationContext.xml");

			((Environment) applicationContext.getBean(Environment.class)).setUp();

		}
		return (Environment) applicationContext.getBean("environment");
	}
	
	private void setUp() {
	}
}
