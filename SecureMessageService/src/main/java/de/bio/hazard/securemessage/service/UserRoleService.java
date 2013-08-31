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

	/**
	 * Add UserRole
	 * 
	 * @param pUserRole
	 */
	void addUserRole(UserRole pUserRole);

	/**
	 * Update UserRole
	 * 
	 * @param pUserRole
	 */
	void updateUserRole(UserRole pUserRole);

	/**
	 * Delete UserRole
	 * 
	 * @param pUserRole
	 */
	void deleteUserRole(UserRole pUserRole);

	/**
	 * Get UserRole
	 * 
	 * @param pID
	 */
	UserRole getUserRoleById(long pID);

	/**
	 * Get UserRole List
	 * 
	 * @return {@link List}
	 */
	List<UserRole> getUserRoles();
	
	Boolean isAdministrator(User pUser);

}
