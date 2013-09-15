package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultMessageContentKeyDao;
import de.bio.hazard.securemessage.model.MessageContentKey;
import de.bio.hazard.securemessage.service.MessageContentKeyService;

@Service(value = "messageContentKeyService")
@Transactional(readOnly = true)
public class DefaultMessageContentKeyService implements
		MessageContentKeyService {

	@Autowired
	private DefaultMessageContentKeyDao messageContentKeyDao;

	@Transactional(readOnly = false)
	@Override
	public void addMessageContentKey(MessageContentKey pMessageContentKey) {
		getMessageContentKeyDao().create(pMessageContentKey);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateMessageContentKey(MessageContentKey pMessageContentKey) {
		getMessageContentKeyDao().save(pMessageContentKey);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteMessageContentKey(MessageContentKey pMessageContentKey) {
		getMessageContentKeyDao().remove(pMessageContentKey);
	}

	@Override
	public MessageContentKey getMessageContentKeyById(long pID) {
		return getMessageContentKeyDao().findByPrimaryKey(pID);
	}

	@Override
	public List<MessageContentKey> getMessagesContentKeys() {
		return getMessageContentKeyDao().findAll();
	}

	@Override
	public List<MessageContentKey> getMessagesContentKeysByMessage(
			long pMessageId) {
		return getMessageContentKeyDao().findByMessage(pMessageId);
	}

	@Override
	public List<MessageContentKey> getMessagesContentKeysByMessageContent(
			long pMessageContentId) {
		return getMessageContentKeyDao()
				.findByMessageContent(pMessageContentId);
	}

	@Override
	public List<MessageContentKey> getMessagesContentKeysByMessageAndMessageContent(
			long pMessageId, long pMessageContentId) {
		return getMessageContentKeyDao().findByMessageAndMessageContent(
				pMessageId, pMessageContentId);
	}

	public DefaultMessageContentKeyDao getMessageContentKeyDao() {
		return messageContentKeyDao;
	}

	public void setMessageContentKeyDao(
			DefaultMessageContentKeyDao messageContentKeyDao) {
		this.messageContentKeyDao = messageContentKeyDao;
	}
}
