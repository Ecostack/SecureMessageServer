package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultMessageContentDao;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.service.MessageContentService;

@Service(value = "messageContentService")
@Transactional(readOnly = true)
public class DefaultMessageContentService implements MessageContentService {

	@Autowired
	private DefaultMessageContentDao messageContentDao;

	@Transactional(readOnly = false)
	@Override
	public void addMessageContent(MessageContent pMessage) {
		getMessageContentDao().create(pMessage);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateMessageContent(MessageContent pMessage) {
		getMessageContentDao().save(pMessage);

	}

	@Transactional(readOnly = false)
	@Override
	public void deleteMessageContent(MessageContent pMessage) {
		getMessageContentDao().remove(pMessage);

	}

	@Override
	public MessageContent getMessageContentById(long pID) {
		return getMessageContentDao().findByPrimaryKey(pID);
	}

	@Override
	public List<MessageContent> getMessagesContents() {
		return getMessageContentDao().findAll();
	}

	@Override
	public List<MessageContent> getMessagesContentsByMessage(Message pMessage) {
	  //XXX Nico Messagekorrektur return getMessageContentDao().findByMessage(pMessage);
	    return null;
	}

	public DefaultMessageContentDao getMessageContentDao() {
		return messageContentDao;
	}

	public void setMessageContentDao(DefaultMessageContentDao messageContentDao) {
		this.messageContentDao = messageContentDao;
	}
}
