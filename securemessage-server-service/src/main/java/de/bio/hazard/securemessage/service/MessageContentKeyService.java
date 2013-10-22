package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.MessageContentKey;

public interface MessageContentKeyService {

	void addMessageContentKey(MessageContentKey pMessageContentKey);

	void updateMessageContentKey(MessageContentKey pMessageContentKey);

	void deleteMessageContentKey(MessageContentKey pMessageContentKey);

	MessageContentKey getMessageContentKeyById(long pID);

	List<MessageContentKey> getMessagesContentKeys();

	List<MessageContentKey> getMessagesContentKeysByMessage(long pMessageId);
	
	List<MessageContentKey> getMessagesContentKeysByMessageContent(long pMessageContentId);
	
	MessageContentKey getMessagesContentKeysByMessageAndMessageContent(long pMessageId,long pMessageContentId);
}
