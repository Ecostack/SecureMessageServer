package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.model.MessageReceiver;

@Component
public class DefaultMessageReceiverDao extends
		AbstractGenericJpaDAO<MessageReceiver, Long> {

	@Override
	public Long getPrimaryKey(MessageReceiver persistentObject) {
		return persistentObject.getId();
	}

	@Override
	public List<MessageReceiver> findAll() {
		return getEntityManager().createNamedQuery(MessageReceiver.FIND_ALL,
				MessageReceiver.class).getResultList();
	}
	
	public List<MessageReceiver> findByMessage(Message pMessage) {
		return getEntityManager().createNamedQuery(MessageReceiver.FIND_BY_MESSAGE,
				MessageReceiver.class).setParameter(1, pMessage).getResultList();
	}
}
