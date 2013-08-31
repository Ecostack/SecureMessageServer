package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.Message;

public interface MessageService {

	/**
	 * Add Message
	 * 
	 * @param pMessage
	 */
	void addMessage(Message pMessage);

	/**
	 * Update Message
	 * 
	 * @param pMessage
	 */
	void updateMessage(Message pMessage);

	/**
	 * Delete Message
	 * 
	 * @param pMessage
	 */
	void deleteMessage(Message pMessage);

	/**
	 * Get Message
	 * 
	 * @param pID
	 */
	Message getMessageById(long pID);

	/**
	 * Get Message List
	 * 
	 * @return {@link List}
	 */
	List<Message> getMessages();


}
