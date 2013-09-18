package de.bio.hazard.securemessage.service.implementation;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.bio.hazard.securemessage.dao.implementation.DefaultUserRoleDao;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;
import de.bio.hazard.securemessage.model.helper.UserRoleType;
import de.bio.hazard.securemessage.service.UserRoleService;

@SuppressWarnings("serial")
@Service(value = "userRoleService")
@Transactional(readOnly = true)
public class DefaultUserRoleService implements UserRoleService, Serializable {

	@Autowired
	private DefaultUserRoleDao userRoleDAO;

	@Transactional(readOnly = false)
	@Override
	public void addUserRole(UserRole userRole) {
		getUserRoleDAO().create(userRole);

	}

	@Transactional(readOnly = false)
	@Override
	public void updateUserRole(UserRole userRole) {
		getUserRoleDAO().save(userRole);

	}

	@Transactional(readOnly = false)
	@Override
	public void deleteUserRole(UserRole userRole) {
		getUserRoleDAO().remove(userRole);

	}

	@Override
	public UserRole getUserRoleById(long id) {
		return getUserRoleDAO().findByPrimaryKey(id);
	}

	@Override
	public List<UserRole> getUserRoles() {
		return getUserRoleDAO().findAll();
	}

	
	public DefaultUserRoleDao getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(DefaultUserRoleDao userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Override
	public Boolean isAdministrator(User pUser) {
		if (pUser != null) {
			return pUser.getRole().getType().compareTo(UserRoleType.Admin) == 0;
		} 
		return false;
	}

	@Override
	public UserRole getUserRoleByType(UserRoleType pUserRoleType) {
		return getUserRoleDAO().findByType(pUserRoleType);
	}

	
}
