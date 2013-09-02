package de.bio.hazard.securemessage.util;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.bio.hazard.securemessage.encryption.hashing.BCrypt;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;
import de.bio.hazard.securemessage.service.MessageService;
import de.bio.hazard.securemessage.service.UserRoleService;
import de.bio.hazard.securemessage.service.UserService;

@Service
public class Global {

	private UserRoleService userRoleService;

	private UserService userService;
	
	private MessageService messageService;

	// private OrgUnitService orgUnitService;
	//
	// private AutomarkeService automarkenService;
	//
	// private AbteilungService abteilungService;
	//
	// private OrgUnitAbteilungService orgUnitAbteilungService;
	//
	// private QuestionService questionService;

	private Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings(value = {})
	@Autowired
	public Global(UserRoleService pUserRoleService, UserService pUserService, MessageService pMessageService) {

		userRoleService = pUserRoleService;
		userService = pUserService;
		messageService = pMessageService;

		log.debug("Global init");

		UserRole lcRoleRegistered = new UserRole();
		lcRoleRegistered.setTargetType(UserRole.ROLE_REGISTERED);
		userRoleService.addUserRole(lcRoleRegistered);

		UserRole lcRolePaying = new UserRole();
		lcRolePaying.setTargetType(UserRole.ROLE_PAYING);
		userRoleService.addUserRole(lcRolePaying);

		UserRole lcRoleAdmin = new UserRole();
		lcRoleAdmin.setTargetType(UserRole.ROLE_ADMINISTRATOR);
		userRoleService.addUserRole(lcRoleAdmin);

		User lcUser = new User();
		lcUser.setEmail("Admin");
		lcUser.setName("Admin");
		lcUser.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(11)));
		lcUser.setRole(lcRoleAdmin);
		lcUser.setLetzterLoginAm(new Date());
		getUserService().addUser(lcUser);
		
		lcUser = new User();
		lcUser.setEmail("test");
		lcUser.setName("test");
		lcUser.setPassword(BCrypt.hashpw("test", BCrypt.gensalt(11)));
		lcUser.setRole(lcRoleAdmin);
		lcUser.setLetzterLoginAm(new Date());
		getUserService().addUser(lcUser);

		// #################################################################################################################################
		
		Message lcMessage = new Message();
		lcMessage.setMessageText("Test");
		lcMessage.setSubject("Betreff");
		
		messageService.addMessage(lcMessage);
		
		for (Message lcMessageItem : messageService.getMessages()) {
			System.err.println(lcMessageItem.getMessageText());
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService pUserService) {
		this.userService = pUserService;
	}

	public UserRoleService getUserRoleService() {
		return userRoleService;
	}

	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
}
