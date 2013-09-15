package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.Config;

@Component
public class DefaultConfigDao extends AbstractGenericJpaDAO<Config, Long> {

	public Long getPrimaryKey(Config persistentObject) {
		return persistentObject.getId();
	}

	@Override
	public List<Config> findAll() {
		return getEntityManager().createNamedQuery(Config.FIND_ALL,
				Config.class).getResultList();
	}
	
}
