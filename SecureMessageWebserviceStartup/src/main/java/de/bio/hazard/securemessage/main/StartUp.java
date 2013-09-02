package de.bio.hazard.securemessage.main;

import javax.xml.ws.Endpoint;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.webservice.MessageWebservice;

@Component()
@Scope(value = "singleton")
public class StartUp {
	
	

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] pStrings) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		MessageWebservice lcMessageWebservice = applicationContext.getBean(MessageWebservice.class);
		
//		MessageEndpoint lcMessageWebservice = new MessageEndpoint();
		Endpoint lcEndpointPublished = Endpoint.publish("http://localhost:8080/messageWebservice", lcMessageWebservice);
	}
}
