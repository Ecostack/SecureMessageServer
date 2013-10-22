package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.User;

public interface UserService {

	void addUser(User pUser);

	void updateUser(User pUser);

	void deleteUser(User pUser);

	User getUserById(long pID);

	List<User> getUsers();

	User getUserByUsername(String pUsername);

	User getUserByEMail(String pEMail);

	User getUserByPhonenumber(String pPhonenumber);
}
