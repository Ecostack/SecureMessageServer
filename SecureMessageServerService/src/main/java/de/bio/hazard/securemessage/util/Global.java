package de.bio.hazard.securemessage.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.bio.hazard.securemessage.encryption.hashing.BCrypt;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.model.MessageContentKey;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;
import de.bio.hazard.securemessage.model.helper.MessageContentType;
import de.bio.hazard.securemessage.model.helper.UserRoleType;
import de.bio.hazard.securemessage.service.MessageContentKeyService;
import de.bio.hazard.securemessage.service.MessageContentService;
import de.bio.hazard.securemessage.service.MessageService;
import de.bio.hazard.securemessage.service.UserRoleService;
import de.bio.hazard.securemessage.service.UserService;

@Service
public class Global {

	private UserRoleService userRoleService;

	private UserService userService;

	private MessageService messageService;

	private MessageContentService messageContentService;

	private MessageContentKeyService messageContentKeyService;
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
	public Global(UserRoleService pUserRoleService, UserService pUserService,
			MessageService pMessageService,
			MessageContentService pMessageContentService,MessageContentKeyService pMessageContentKeyService) throws UnsupportedEncodingException {

		userRoleService = pUserRoleService;
		userService = pUserService;
		messageService = pMessageService;
		messageContentService = pMessageContentService;
		messageContentKeyService = pMessageContentKeyService;

		log.debug("Global init");

		UserRole lcRoleRegistered = new UserRole();
		lcRoleRegistered.setType(UserRoleType.Registered);
		userRoleService.addUserRole(lcRoleRegistered);

		UserRole lcRolePaying = new UserRole();
		lcRolePaying.setType(UserRoleType.Paying);
		userRoleService.addUserRole(lcRolePaying);

		UserRole lcRoleUnregistered = new UserRole();
		lcRoleUnregistered.setType(UserRoleType.Unregistered);
		userRoleService.addUserRole(lcRoleUnregistered);

		UserRole lcRoleAdmin = new UserRole();
		lcRoleAdmin.setType(UserRoleType.Admin);
		userRoleService.addUserRole(lcRoleAdmin);

		User lcUser = new User();
		lcUser.setUsername("Admin");
		lcUser.setEmail("Admin");
		lcUser.setName("Admin");
		lcUser.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(11)));
		lcUser.setRole(lcRoleAdmin);
		lcUser.setLetzterLoginAm(new Date());
		lcUser.setPublicAsyncKey(new byte[] { 1 });
		getUserService().addUser(lcUser);

		User lcUserTest = new User();
		lcUserTest.setEmail("test");
		lcUserTest.setName("test");
		lcUserTest.setUsername("test");
		lcUserTest.setPassword(BCrypt.hashpw("test", BCrypt.gensalt(11)));
		lcUserTest.setRole(lcRoleRegistered);
		lcUserTest.setLetzterLoginAm(new Date());
		lcUserTest.setPublicAsyncKey(new byte[] { 1 });
		getUserService().addUser(lcUserTest);

		// #################################################################################################################################

		Message lcMessage = new Message();
		lcMessage.setSender(lcUser);
		lcMessage.setReceiver(lcUserTest);

		messageService.addMessage(lcMessage);

		MessageContent lcMessageContent = new MessageContent();
		lcMessageContent.setMessageContentType(MessageContentType.Subject);
		lcMessageContent.setData("ü".getBytes("UTF-8"));
		lcMessageContent.setSynchEncryptionKey("ü".getBytes("UTF-16"));
		lcMessageContent.getMessages().add(lcMessage);

		MessageContent lcMessageContent2 = new MessageContent();
		lcMessageContent2.setMessageContentType(MessageContentType.Message);
		lcMessageContent2.setData("ü".getBytes("cp1252"));
		lcMessageContent2.setSynchEncryptionKey("ü".getBytes());
		lcMessageContent2.getMessages().add(lcMessage);

		messageContentService.addMessageContent(lcMessageContent);

		messageContentService.addMessageContent(lcMessageContent2);
		
		
		MessageContentKey lcMCK = new MessageContentKey();
		lcMCK.setMessage(lcMessage);
		lcMCK.setMessageContent(lcMessageContent);
		lcMCK.setSynchEncryptionKey(new byte[]{1,2,3});
		
		messageContentKeyService.addMessageContentKey(lcMCK);
		
		for (Message lcMessageItem : messageService.getMessages()) {
			System.err.println("Message: " + lcMessageItem.getId());
			for (MessageContent lcContent : lcMessageItem.getContents()) {
				System.err.println("Message content: " + lcContent.getId());
				System.err.println("Message content type: "
						+ lcContent.getMessageContentType());
			}
		}

		List<MessageContent> lcList = messageContentService
				.getMessagesContentsByMessage(lcMessage);
		for (MessageContent lcMessageContentItem : lcList) {
			System.err.println("Messagecontent: "
					+ lcMessageContentItem.getId());
		}
		
		
		
		List<MessageContentKey> lcMCKList = messageContentKeyService.getMessagesContentKeysByMessage(lcMessage.getId());
		for (MessageContentKey lcMCKItem : lcMCKList) {
			System.err.println("MessageContentKey: " + lcMCKItem.getId());
			System.err.println("MessageContentKey encKey: " + lcMCKItem.getSynchEncryptionKey());
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
