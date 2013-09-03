package de.bio.hazard.securemessage.webserviceclient.test;

import java.net.URL;

import junit.framework.TestCase;
import de.bio.hazard.securemessage.webservice.MessageWebservice;
import de.bio.hazard.securemessage.webservice.MessageWebserviceDTO;
import de.bio.hazard.securemessage.webservice.MessageWebserviceService;

public class TestWerbservice extends TestCase {

	public void testMessage() {
		try {
			MessageWebserviceService lcEndpointService = new MessageWebserviceService(
					new URL("http://localhost:8080/messageWebservice"));
			
			MessageWebservice lcEndpoint =lcEndpointService.getMessageWebservicePort();
			
			
			MessageWebserviceDTO lcMessageWebserviceDTO = new MessageWebserviceDTO();
			lcMessageWebserviceDTO.setMessageText("Mein Text vom WS");
			lcMessageWebserviceDTO.setSubject("Mein Subject vom WS");
			
			lcEndpoint.addMessage(lcMessageWebserviceDTO);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
