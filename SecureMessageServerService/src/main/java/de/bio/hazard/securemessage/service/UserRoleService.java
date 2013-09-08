package de.bio.hazard.securemessage.service;

import java.util.List;

import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;

/**
 * 
 * User Service Interface
 * 
 * @author Sebastian Scheibe
 * @since 28 Mar 2013
 * 
 */
public interface UserRoleService {

	void addUserRole(UserRole pUserRole);

	void updateUserRole(UserRole pUserRole);

	void deleteUserRole(UserRole pUserRole);

	UserRole getUserRoleById(long pID);

	List<UserRole> getUserRoles();
	
	Boolean isAdministrator(User pUser);

}
