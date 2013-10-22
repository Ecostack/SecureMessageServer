package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.User;

public interface MessageService {

	void addMessage(Message pMessage);

	void updateMessage(Message pMessage);

	void deleteMessage(Message pMessage);

	Message getMessageById(long pID);

	List<Message> getMessages();

	List<Message> getMessagesByReceiver(User pReceiver);

}
