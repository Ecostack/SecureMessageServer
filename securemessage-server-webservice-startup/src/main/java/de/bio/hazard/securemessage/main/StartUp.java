package de.bio.hazard.securemessage.main;

import javax.xml.ws.Endpoint;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.webservice.AuthenticationWebservice;
import de.bio.hazard.securemessage.webservice.BasisInfoWebservice;
import de.bio.hazard.securemessage.webservice.MessageWebservice;
import de.bio.hazard.securemessage.webservice.UserWebservice;

@Component()
@Scope(value = "singleton")
public class StartUp {

	private static String IP = "0.0.0.0";

	// private static String IP = "192.168.0.102";

	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] pStrings) {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		MessageWebservice lcMessageWebservice = applicationContext
				.getBean(MessageWebservice.class);

		AuthenticationWebservice lcAuthenticationWebservice = applicationContext
				.getBean(AuthenticationWebservice.class);

		BasisInfoWebservice lcBasisInfoWS = applicationContext
				.getBean(BasisInfoWebservice.class);

		UserWebservice lcUserWS = applicationContext
				.getBean(UserWebservice.class);

		// MessageEndpoint lcMessageWebservice = new MessageEndpoint();
		Endpoint.publish("http://" + IP + ":8080/messageWebservice",
				lcMessageWebservice);
		Endpoint.publish("http://" + IP + ":8080/authenticationWebservice",
				lcAuthenticationWebservice);
		Endpoint.publish("http://" + IP + ":8080/basisInfoWebservice",
				lcBasisInfoWS);
		Endpoint.publish("http://" + IP + ":8080/userWebservice", lcUserWS);

	}
}
