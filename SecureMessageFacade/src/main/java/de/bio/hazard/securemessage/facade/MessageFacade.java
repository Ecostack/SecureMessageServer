package de.bio.hazard.securemessage.facade;

import de.bio.hazard.securemessage.dto.MessageWebserviceDTO;
import de.bio.hazard.securemessage.model.Message;

public class MessageFacade {

	
	public static Message transferMessageWebserviceDTOtoMessage(MessageWebserviceDTO pDTO) {
		if (pDTO == null) {
			throw new IllegalArgumentException("MessageWebserviceDTO is not allowed to be null");
		}
		
		Message lcResult = new Message();
		lcResult.setMessageText(pDTO.getMessageText());
		lcResult.setSubject(pDTO.getSubject());
		return lcResult;
	}
}
