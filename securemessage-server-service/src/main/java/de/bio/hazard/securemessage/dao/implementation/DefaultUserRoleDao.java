package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;
import de.bio.hazard.securemessage.model.helper.UserRoleType;

@Component
public class DefaultUserRoleDao extends AbstractGenericJpaDAO<UserRole, Long> {

	@Override
	public Long getPrimaryKey(UserRole persistentObject) {
		return persistentObject.getId();
	}

	/**
	 * Search all user roles
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<UserRole> findAll() {
		return getEntityManager().createNamedQuery(UserRole.FIND_ALL,
				UserRole.class).getResultList();
	}

	public List<UserRole> findAllByUser(User pUser) {
		return getEntityManager()
				.createNamedQuery(UserRole.FIND_ALL_BY_USER, UserRole.class)
				.setParameter(1, pUser).getResultList();
	}

	public List<UserRole> findAllByType(UserRoleType pTargetType) {
		return getEntityManager()
				.createNamedQuery(UserRole.FIND_BY_TYPE, UserRole.class)
				.setParameter(1, pTargetType).getResultList();
	}

	public UserRole findByType(UserRoleType pTargetType) {
		return getEntityManager()
				.createNamedQuery(UserRole.FIND_BY_TYPE, UserRole.class)
				.setParameter(1, pTargetType).getSingleResult();
	}
}