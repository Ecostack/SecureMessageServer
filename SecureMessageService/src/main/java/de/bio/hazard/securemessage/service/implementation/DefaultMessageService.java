package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultMessageDao;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.service.MessageService;

@Service(value = "messageService")
@Transactional(readOnly = true)
public class DefaultMessageService implements MessageService {

	@Autowired
	private DefaultMessageDao messageDao;

	@Override
	public void addMessage(Message pMessage) {
		getMessageDao().create(pMessage);

	}

	@Override
	public void updateMessage(Message pMessage) {
		getMessageDao().save(pMessage);

	}

	@Override
	public void deleteMessage(Message pMessage) {
		getMessageDao().remove(pMessage);

	}

	@Override
	public Message getMessageById(long pID) {
		return getMessageDao().findByPrimaryKey(pID);
	}

	@Override
	public List<Message> getMessages() {
		return getMessageDao().findAll();
	}

	public DefaultMessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(DefaultMessageDao messageDao) {
		this.messageDao = messageDao;
	}

}
