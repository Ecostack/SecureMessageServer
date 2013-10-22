package de.bio.hazard.securemessage.dto.message;

import java.util.ArrayList;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class RequestMessageWebserviceReturnDTO extends AbstractDTO {

	private ArrayList<MessageWebserviceDTO> messages;

	public ArrayList<MessageWebserviceDTO> getMessages() {
		if (messages == null) {
			messages = new ArrayList<MessageWebserviceDTO>();
		}
		return messages;
	}

	public void setMessages(ArrayList<MessageWebserviceDTO> messages) {
		this.messages = messages;
	}

}
