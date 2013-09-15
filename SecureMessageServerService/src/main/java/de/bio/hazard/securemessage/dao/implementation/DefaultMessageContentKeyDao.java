package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.model.MessageContentKey;

@Component
public class DefaultMessageContentKeyDao extends
		AbstractGenericJpaDAO<MessageContentKey, Long> {

	public Long getPrimaryKey(MessageContentKey persistentObject) {
		return persistentObject.getId();
	}

	/**
	 * Search all Users
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<MessageContentKey> findAll() {
		return getEntityManager().createNamedQuery(MessageContentKey.FIND_ALL,
				MessageContentKey.class).getResultList();
	}

	public List<MessageContentKey> findByMessage(Long pMessageId) {
		return getEntityManager()
				.createNamedQuery(MessageContentKey.FIND_BY_MESSAGE,
						MessageContentKey.class).setParameter(1, pMessageId)
				.getResultList();
	}
	
	public List<MessageContentKey> findByMessageContent(Long pMessageContentId) {
		return getEntityManager()
				.createNamedQuery(MessageContentKey.FIND_BY_MESSAGECONTENT,
						MessageContentKey.class).setParameter(1, pMessageContentId)
				.getResultList();
	}
	
	public List<MessageContentKey> findByMessageAndMessageContent(Long pMessageId, Long pMessageContentId) {
		return getEntityManager()
				.createNamedQuery(MessageContentKey.FIND_BY_MESSAGE_AND_MESSAGECONTENT,
						MessageContentKey.class).setParameter(1, pMessageId).setParameter(2, pMessageContentId)
				.getResultList();
	}

}
