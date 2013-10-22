package de.bio.hazard.securemessage.dto.message;

import java.util.ArrayList;

import de.bio.hazard.securemessage.dto.AbstractDTO;

public class MessageWebserviceDTO extends AbstractDTO {

	// ACHTUNG: Typ muss Serializable sein / also nicht Interface List<E> ...
	// sondern z.B. ArrayList
	private ArrayList<MessageContentWebserviceDTO> content;
	private ArrayList<MessageReceiverWebserviceDTO> receiver;

	public ArrayList<MessageReceiverWebserviceDTO> getReceiver() {
		if (receiver == null) {
			receiver = new ArrayList<MessageReceiverWebserviceDTO>();
		}
		return receiver;
	}

	public ArrayList<MessageContentWebserviceDTO> getContent() {
		if (content == null) {
			content = new ArrayList<MessageContentWebserviceDTO>();
		}
		return content;
	}

	public void setContent(ArrayList<MessageContentWebserviceDTO> content) {
		this.content = content;
	}

	public void setReceiver(ArrayList<MessageReceiverWebserviceDTO> receiver) {
		this.receiver = receiver;
	}
}
