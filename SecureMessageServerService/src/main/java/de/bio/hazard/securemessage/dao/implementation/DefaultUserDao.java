package de.bio.hazard.securemessage.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.db.jpa.dao.AbstractGenericJpaDAO;
import de.bio.hazard.securemessage.model.User;

@Component
public class DefaultUserDao extends AbstractGenericJpaDAO<User, Long> {

	@Override
	public Long getPrimaryKey(User persistentObject) {
		return persistentObject.getId();
	}

	/**
	 * Search all Users
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<User> findAll() {
		return getEntityManager().createNamedQuery(User.FIND_ALL, User.class)
				.getResultList();
	}

	public User findByUsername(String pUsername) {
		List<User> lcUserList = findByUsernameList(pUsername);
		if (lcUserList.size() > 0) {
			return lcUserList.get(0);
		}
		return null;
	}

	public User findByEMail(String pEMail) {
		List<User> lcUserList = findByEMailList(pEMail);
		if (lcUserList.size() > 0) {
			return lcUserList.get(0);
		}
		return null;
	}

	private List<User> findByUsernameList(String pUsername) {
		return getEntityManager()
				.createNamedQuery(User.FIND_BY_USERNAME, User.class)
				.setParameter(1, pUsername).getResultList();
	}

	private List<User> findByEMailList(String pEMail) {
		return getEntityManager()
				.createNamedQuery(User.FIND_BY_EMAIL, User.class)
				.setParameter(1, pEMail).getResultList();
	}

}
