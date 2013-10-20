package de.bio.hazard.securemessage.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.message.MessageWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.RequestMessageWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.RequestMessageWebserviceReturnDTO;
import de.bio.hazard.securemessage.facade.MessageFacade;
import de.bio.hazard.securemessage.tecframework.exception.EncryptionExceptionBiohazard;

@WebService
@Component
public class MessageWebservice {

    @Autowired
    private MessageFacade messageFassade;

    @WebMethod
    public void addMessage(MessageWebserviceDTO pMessageDTO) throws EncryptionExceptionBiohazard {
	System.err.println("Start add Message");
	messageFassade.addMessage(pMessageDTO);
	System.err.println("End add Message");
    }
    
    @WebMethod
    public RequestMessageWebserviceReturnDTO getMessages(RequestMessageWebserviceDTO pRequestMessageDTO) throws EncryptionExceptionBiohazard{
	return messageFassade.getMessages(pRequestMessageDTO);
    }
}
