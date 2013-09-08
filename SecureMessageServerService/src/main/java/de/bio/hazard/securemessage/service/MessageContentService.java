package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;

public interface MessageContentService {

	void addMessageContent(MessageContent pMessageContent);

	void updateMessageContent(MessageContent pMessageContent);

	void deleteMessageContent(MessageContent pMessageContent);

	MessageContent getMessageContentById(long pID);

	List<MessageContent> getMessagesContents();

	List<MessageContent> getMessagesContentsByMessage(Message pMessage);
}
