package de.bio.hazard.securemessage.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultMessageReceiverDao;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageReceiver;
import de.bio.hazard.securemessage.service.MessageReceiverService;

@Service(value = "messageReceiverService")
@Transactional(readOnly = true)
public class DefaultMessageReceiverService implements MessageReceiverService {

	@Autowired
	private DefaultMessageReceiverDao DefaultMessageReceiverDao;

	@Transactional(readOnly = false)
	@Override
	public void addMessageReceiver(MessageReceiver pMessage) {
		getDefaultMessageReceiverDao().create(pMessage);
	}

	@Transactional(readOnly = false)
	@Override
	public void updateMessageReceiver(MessageReceiver pMessage) {
		getDefaultMessageReceiverDao().save(pMessage);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteMessageReceiver(MessageReceiver pMessage) {
		getDefaultMessageReceiverDao().remove(pMessage);
	}

	@Override
	public MessageReceiver getMessageReceiverById(long pID) {
		return getDefaultMessageReceiverDao().findByPrimaryKey(pID);
	}

	@Override
	public List<MessageReceiver> getMessageReceivers() {
		return getDefaultMessageReceiverDao().findAll();
	}

	@Override
	public List<MessageReceiver> getMessageReceiversByMessage(Message pMessage) {
		return getDefaultMessageReceiverDao().findByMessage(pMessage);
	}

	public DefaultMessageReceiverDao getDefaultMessageReceiverDao() {
		return DefaultMessageReceiverDao;
	}

	public void setDefaultMessageReceiverDao(
			DefaultMessageReceiverDao defaultMessageReceiverDao) {
		DefaultMessageReceiverDao = defaultMessageReceiverDao;
	}

}
