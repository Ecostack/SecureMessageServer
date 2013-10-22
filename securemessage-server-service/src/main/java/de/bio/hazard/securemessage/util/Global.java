package de.bio.hazard.securemessage.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.model.MessageContentKey;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.UserRole;
import de.bio.hazard.securemessage.model.helper.MessageContentType;
import de.bio.hazard.securemessage.model.helper.UserRoleType;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.MessageContentKeyService;
import de.bio.hazard.securemessage.service.MessageContentService;
import de.bio.hazard.securemessage.service.MessageService;
import de.bio.hazard.securemessage.service.UserRoleService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricKey;
import de.bio.hazard.securemessage.tecframework.encryption.asymmetric.AsymmetricKeygen;

@Service
public class Global {

	private UserRoleService userRoleService;

	private UserService userService;

	private MessageService messageService;

	private MessageContentService messageContentService;

	private MessageContentKeyService messageContentKeyService;

	private ConfigService configService;

	private AsymmetricKeygen asyncKeygen;

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
			MessageContentService pMessageContentService,
			MessageContentKeyService pMessageContentKeyService,
			ConfigService pConfigService, AsymmetricKeygen pAsyncKeygen)
			throws UnsupportedEncodingException {

		userRoleService = pUserRoleService;
		userService = pUserService;
		messageService = pMessageService;
		messageContentService = pMessageContentService;
		messageContentKeyService = pMessageContentKeyService;
		configService = pConfigService;
		asyncKeygen = pAsyncKeygen;

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
		// lcUser.setPassword(BCrypt.hashpw("admin", BCrypt.gensalt(11)));
		lcUser.setPassword("admin");
		lcUser.setRole(lcRoleAdmin);
		lcUser.setLastLoginAt(Calendar.getInstance());
		AsymmetricKey lcUserKeyPair = asyncKeygen.getKey(1024);
		lcUser.setPublicKeyForMessaging(lcUserKeyPair.getPublicKey());
		getUserService().addUser(lcUser);

		User lcUserTest = new User();
		lcUserTest.setEmail("test");
		lcUserTest.setName("test");
		lcUserTest.setUsername("test");
		// lcUserTest.setPassword(BCrypt.hashpw("test", BCrypt.gensalt(11)));
		lcUserTest.setPassword("test");
		lcUserTest.setRole(lcRoleRegistered);
		lcUserTest.setLastLoginAt(Calendar.getInstance());
		AsymmetricKey lcUserTestKeyPair = asyncKeygen.getKey(1024);
		lcUserTest.setPublicKeyForMessaging(lcUserTestKeyPair.getPublicKey());
		getUserService().addUser(lcUserTest);

		User lcUserTest2 = new User();
		lcUserTest2.setEmail("testMessage");
		lcUserTest2.setName("testMessage");
		lcUserTest2.setUsername("testMessage");
		// lcUserTest2.setPassword(BCrypt.hashpw("testMessage",
		// BCrypt.gensalt(11)));
		lcUserTest2.setPassword("testMessage");
		lcUserTest2.setRole(lcRoleRegistered);
		lcUserTest2.setLastLoginAt(Calendar.getInstance());
		AsymmetricKey lcUserTestMessageKeyPair = asyncKeygen.getKey(1024);
		lcUserTest2.setPublicKeyForMessaging(lcUserTestMessageKeyPair
				.getPublicKey());
		getUserService().addUser(lcUserTest2);

		log.debug("Global init - User OK");

		// #################################################################################################################################

		Message lcMessage = new Message();
		lcMessage.setSender(lcUser);
		lcMessage.setReceiver(lcUserTest);

		Message lcMessage2 = new Message();
		lcMessage2.setSender(lcUser);
		lcMessage2.setReceiver(lcUserTest2);

		messageService.addMessage(lcMessage);
		messageService.addMessage(lcMessage2);

		MessageContent lcMessageContent = new MessageContent();
		lcMessageContent.setMessageContentType(MessageContentType.Subject);
		lcMessageContent.setData("Hallo Welt".getBytes("UTF-8"));
		// lcMessageContent.setData("Hallo Welt");
		// lcMessageContent.setSynchEncryptionKey("1234".getBytes("UTF-16"));
		// lcMessageContent.getMessages().add(lcMessage);

		MessageContent lcMessageContent2 = new MessageContent();
		lcMessageContent2.setMessageContentType(MessageContentType.Message);
		lcMessageContent2.setData("Dies ist ein Text (oe)".getBytes("cp1252"));
		// lcMessageContent2.setData("Dies ist ein Text (ue)");
		// lcMessageContent2.setSynchEncryptionKey("5678".getBytes());
		// lcMessageContent2.getMessages().add(lcMessage);

		messageContentService.addMessageContent(lcMessageContent);
		messageContentService.addMessageContent(lcMessageContent2);

		MessageContentKey lcMCK = new MessageContentKey();
		lcMCK.setMessage(lcMessage);
		lcMCK.setMessageContent(lcMessageContent);
		lcMCK.setSymmetricEncryptionKey("SymmetricEncryptionKey Content1 (encrypted for User "
				+ lcMessage.getReceiver().getName() + ")");
		messageContentKeyService.addMessageContentKey(lcMCK);

		MessageContentKey lcMCK2 = new MessageContentKey();
		lcMCK2.setMessage(lcMessage);
		lcMCK2.setMessageContent(lcMessageContent2);
		lcMCK2.setSymmetricEncryptionKey("SymmetricEncryptionKey Content2 (encrypted for User "
				+ lcMessage.getReceiver().getName() + ")");
		messageContentKeyService.addMessageContentKey(lcMCK2);

		MessageContentKey lcMCK3 = new MessageContentKey();
		lcMCK3.setMessage(lcMessage2);
		lcMCK3.setMessageContent(lcMessageContent);
		lcMCK3.setSymmetricEncryptionKey("SymmetricEncryptionKey Content1 (encrypted for User "
				+ lcMessage2.getReceiver().getName() + ")");
		messageContentKeyService.addMessageContentKey(lcMCK3);

		MessageContentKey lcMCK4 = new MessageContentKey();
		lcMCK4.setMessage(lcMessage2);
		lcMCK4.setMessageContent(lcMessageContent2);
		lcMCK4.setSymmetricEncryptionKey("SymmetricEncryptionKey Content2 (encrypted for User "
				+ lcMessage2.getReceiver().getName() + ")");
		messageContentKeyService.addMessageContentKey(lcMCK4);

		log.debug("Global init - Message OK");

		// for (Message lcMessageItem : messageService.getMessages()) {
		// System.err.println("Message: " + lcMessageItem.getId());
		// for (MessageContent lcContent : lcMessageItem.getContents()) {
		// System.err.println("Message content: " + lcContent.getId());
		// System.err.println("Message content type: "
		// + lcContent.getMessageContentType());
		// }
		// }
		//
		// List<MessageContent> lcList = messageContentService
		// .getMessagesContentsByMessage(lcMessage);
		// for (MessageContent lcMessageContentItem : lcList) {
		// System.err.println("Messagecontent: "
		// + lcMessageContentItem.getId());
		// }
		//
		// List<MessageContentKey> lcMCKList = messageContentKeyService
		// .getMessagesContentKeysByMessage(lcMessage.getId());
		// for (MessageContentKey lcMCKItem : lcMCKList) {
		// System.err.println("MessageContentKey: " + lcMCKItem.getId());
		// System.err.println("MessageContentKey encKey: "
		// + lcMCKItem.getSynchEncryptionKey());
		// }

		createConfigValues();
	}

	private void createConfigValues() {

		AsymmetricKey lcKeyPair = asyncKeygen.getKey(2048);

		Config lcConfigPublicKey = new Config();
		lcConfigPublicKey.setRunningNumber(ConfigType.SERVER_PUBLIC_KEY
				.getRunningNumber());
		lcConfigPublicKey.setValue(lcKeyPair.getPublicKey());
		configService.addConfig(lcConfigPublicKey);

		Config lcConfigPrivateKey = new Config();
		lcConfigPrivateKey.setRunningNumber(ConfigType.SERVER_PRIVATE_KEY
				.getRunningNumber());
		lcConfigPrivateKey.setValue(lcKeyPair.getPrivateKey());
		configService.addConfig(lcConfigPrivateKey);

		for (Config lcConfig : configService.getConfigs()) {
			System.err.println("Config: " + lcConfig.getRunningNumber());

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
