package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageReceiver;

public interface MessageReceiverService {

	void addMessageReceiver(MessageReceiver pMessageReceiver);

	void updateMessageReceiver(MessageReceiver pMessageReceiver);

	void deleteMessageReceiver(MessageReceiver pMessageReceiver);

	MessageReceiver getMessageReceiverById(long pID);

	List<MessageReceiver> getMessageReceivers();

	List<MessageReceiver> getMessageReceiversByMessage(Message pMessage);

}
