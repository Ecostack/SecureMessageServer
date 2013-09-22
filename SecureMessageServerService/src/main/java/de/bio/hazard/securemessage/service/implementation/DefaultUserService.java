package de.bio.hazard.securemessage.service.implementation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultUserDao;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.service.UserService;

@Service(value = "userService")
@Transactional(readOnly = true)
@Scope(value = "singleton")
public class DefaultUserService implements UserService {
	

	@Autowired
	private DefaultUserDao userDAO;

	/**
	 * Add User
	 * 
	 * @param pUser
	 */
	@Transactional(readOnly = false)
	@Override
	public void addUser(User pUser) {
		pUser.setCreationDate(Calendar.getInstance());
		getDefaultUserDao().create(pUser);
	}

	/**
	 * Delete User
	 * 
	 * @param pUserr
	 */
	@Transactional(readOnly = false)
	@Override
	public void deleteUser(User pUser) {
		getDefaultUserDao().remove(pUser);
	}

	/**
	 * Update User
	 * 
	 * @param pUser
	 */
	@Transactional(readOnly = false)
	@Override
	public void updateUser(User pUser) {
		getDefaultUserDao().save(pUser);
	}

	/**
	 * Get User
	 * 
	 * @param pID
	 */
	@Override
	public User getUserById(long pID) {
		return getDefaultUserDao().findByPrimaryKey(pID);
	}

	/**
	 * Get User List
	 * 
	 * @return {@link List}
	 */
	@Override
	public List<User> getUsers() {
		return getDefaultUserDao().findAll();
	}

	/**
	 * Get User DAO
	 * 
	 * @return {@link DefaultUserDao}
	 */
	public DefaultUserDao getDefaultUserDao() {
		return userDAO;
	}

	/**
	 * 
	 * @param pDefaultUserDao
	 */
	public void setDefaultUserDao(DefaultUserDao pDefaultUserDao) {
		this.userDAO = pDefaultUserDao;
	}

	/**
	 * @param pUsername
	 * 
	 * @return {@link User}
	 */
	@Override
	public User getUserByUsername(String pUsername) {
		User lcUser = getDefaultUserDao().findByUsername(pUsername);
		return lcUser;
	}

	/**
	 * @param pUsername
	 * 
	 * @return {@link User}
	 */
	@Override
	public User getUserByEMail(String pEMail) {
		User lcUser = getDefaultUserDao().findByEMail(pEMail);
		return lcUser;
	}
	
	/**
	 * @param pUsername
	 * 
	 * @return {@link User}
	 */
	@Override
	public User getUserByPhonenumber(String pPhonenumber) {
		User lcUser = getDefaultUserDao().findByUsername(pPhonenumber);
		return lcUser;
	}
}
