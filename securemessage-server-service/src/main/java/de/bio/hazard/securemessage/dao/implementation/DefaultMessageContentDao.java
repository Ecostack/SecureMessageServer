package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;

@Component
public class DefaultMessageContentDao extends
		AbstractGenericJpaDAO<MessageContent, Long> {

	public Long getPrimaryKey(MessageContent persistentObject) {
		return persistentObject.getId();
	}

	/**
	 * Search all Users
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<MessageContent> findAll() {
		return getEntityManager().createNamedQuery(MessageContent.FIND_ALL,
				MessageContent.class).getResultList();
	}

	public List<MessageContent> findByMessage(Message pMessage) {
		return getEntityManager()
				.createNamedQuery(MessageContent.FIND_BY_MESSAGEID,
						MessageContent.class).setParameter(1, pMessage.getId())
				.getResultList();
	}

}
