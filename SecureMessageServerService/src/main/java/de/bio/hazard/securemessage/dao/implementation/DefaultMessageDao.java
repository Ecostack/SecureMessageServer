package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Message;

@Component
public class DefaultMessageDao extends AbstractGenericJpaDAO<Message, Long> {

	@Override
	public Long getPrimaryKey(Message persistentObject) {
		return persistentObject.getId();
	}

	@Override
	public List<Message> findAll() {
		return getEntityManager().createNamedQuery(Message.FIND_ALL, Message.class)
				.getResultList();
	}

}
