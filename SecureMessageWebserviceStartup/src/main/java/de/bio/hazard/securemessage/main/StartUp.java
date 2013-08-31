package de.bio.hazard.securemessage.main;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component()
@Scope(value = "singleton")
public class StartUp {

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] pStrings) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
	}
}
