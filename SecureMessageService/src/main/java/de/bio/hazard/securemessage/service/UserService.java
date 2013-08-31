package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.User;

public interface UserService {
	/**
	 * Add User
	 * 
	 * @param pUser
	 */
	void addUser(User pUser);

	/**
	 * Update User
	 * 
	 * @param pUser
	 */
	void updateUser(User pUser);

	/**
	 * Delete User
	 * 
	 * @param pUser
	 */
	void deleteUser(User pUser);

	/**
	 * Get User
	 * 
	 * @param pID
	 */
	User getUserById(long pID);

	/**
	 * Get User List
	 * 
	 * @return {@link List}
	 */
	List<User> getUsers();

	User getUserByUsername(String pUsername);

	User getUserByEMail(String pEMail);
}
