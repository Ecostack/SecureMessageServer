package de.bio.hazard.securemessage.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.MessageWebserviceDTO;
import de.bio.hazard.securemessage.facade.MessageFacade;
import de.bio.hazard.securemessage.service.implementation.DefaultMessageService;

@WebService
@Component
public class MessageWebservice {

	private DefaultMessageService messageService = null;

	@Autowired
	public MessageWebservice(DefaultMessageService pMessageService) {
		messageService = pMessageService;
	}

	@WebMethod
	public void addMessage(MessageWebserviceDTO pMessageDTO) {
		messageService.addMessage(MessageFacade
				.transferMessageWebserviceDTOtoMessage(pMessageDTO));
		
		messageService.listMessagesAndData();
	}

}
