package de.bio.hazard.securemessage.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.message.MessageContentKeyWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageContentWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageReceiverWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageWebserviceDTO;
import de.bio.hazard.securemessage.model.Config;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.Message;
import de.bio.hazard.securemessage.model.MessageContent;
import de.bio.hazard.securemessage.model.MessageContentKey;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.helper.MessageContentType;
import de.bio.hazard.securemessage.service.AuthenticationService;
import de.bio.hazard.securemessage.service.ConfigService;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.MessageContentKeyService;
import de.bio.hazard.securemessage.service.MessageContentService;
import de.bio.hazard.securemessage.service.MessageService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.service.helper.ConfigType;
import de.bio.hazard.securemessage.tecframework.encryption.facade.helper.EncryptionObjectModifier;
import de.bio.hazard.securemessage.tecframework.exception.AuthenticationExceptionBiohazard;
import de.bio.hazard.securemessage.tecframework.exception.EncryptionExceptionBiohazard;

@Component
public class MessageFacade {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageContentService messageContentService;

    @Autowired
    private MessageContentKeyService messageContentKeyService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired(required = true)
    private ConfigService configService;

    @Autowired(required = true)
    private EncryptionObjectModifier encryptionObjectModifier;

    public void addMessage(MessageWebserviceDTO pMessageWebserviceDTO) throws EncryptionExceptionBiohazard {
	// TODO MessageReceiverType beachten
	
	MessageWebserviceDTO lcMessageWebserviceDTO = decryptMessageWebserviceDTO(pMessageWebserviceDTO);
	if (lcMessageWebserviceDTO == null) {
	    throw new IllegalArgumentException("MessageWebserviceDTO is not allowed to be null");
	}
	if (authenticationService.isAuthTokenValid(lcMessageWebserviceDTO.getTokenId())) {
	    Device lcDevice = deviceService.getDeviceByDeviceId(authenticationService.getDeviceIdByTokenId(lcMessageWebserviceDTO.getTokenId()));
	    User lcSender = lcDevice.getUser();
	    List<Message> lcMessages = extractMessagesFromMessageWebserviceDTO(lcMessageWebserviceDTO, lcSender);
	    addMessage(lcMessages);
	    extractAndAddMessageContent(lcMessageWebserviceDTO, lcMessages);
	}
	else {
	    throw new AuthenticationExceptionBiohazard("Invalid Authentication-Token");
	}
    }
    
    //TODO SebastianS; andere Idee "interne" DTOs auf dem Client zu generieren?
    /*public void helperGetInnerDTOsToClient(
	    MessageReceiverWebserviceDTO pReceiver ,
	    MessageContentWebserviceDTO pContent, 
	    MessageContentKeyWebserviceDTO pContentKey) {
	//do nothing, only helper for DTOs
    }*/
    
    private void extractAndAddMessageContent(MessageWebserviceDTO lcMessageWebserviceDTO, List<Message> lcMessages) {
	for (MessageContentWebserviceDTO lcMessageContentWDTO : lcMessageWebserviceDTO.getContent()) {
	    MessageContent lcMessageContent = new MessageContent();
	    lcMessageContent.setData(lcMessageContentWDTO.getData().getBytes());
	    lcMessageContent.setFilename(lcMessageContentWDTO.getFilename());
	    
	    // TODO MessageContentType beachten
	    lcMessageContent.setMessageContentType(MessageContentType.Message);
	    
	    messageContentService.addMessageContent(lcMessageContent);

	    for (MessageContentKeyWebserviceDTO lcMessageContentKeyWDTO : lcMessageContentWDTO.getSymmetricKeys()) {
		for (Message lcMessage : lcMessages) {
		    // XXX oder Vergleich über User?
		    if (lcMessage.getReceiver().getUsername().equals(lcMessageContentKeyWDTO.getUsername())) {
			MessageContentKey lcMessageContentKey = new MessageContentKey();
			lcMessageContentKey.setSynchEncryptionKey(lcMessageContentKeyWDTO.getSymmetricEncryptionKey());
			lcMessageContentKey.setMessageContent(lcMessageContent);
			lcMessageContentKey.setMessage(lcMessage);
			messageContentKeyService.addMessageContentKey(lcMessageContentKey);
		    }
		}
	    }
	}
    }

    private void addMessage(List<Message> pMessages) {
	for (Message lcMessage : pMessages) {
	    messageService.addMessage(lcMessage);
	}
    }

    private List<Message> extractMessagesFromMessageWebserviceDTO(MessageWebserviceDTO pMessageWebserviceDTO, User pSender) {
	List<Message> lcResult = new ArrayList<Message>();
	for (MessageReceiverWebserviceDTO lcMessageReceiverWDTO : pMessageWebserviceDTO.getReceiver()) {
	    Message lcMessage = new Message();
	    User lcReceiver = userService.getUserByUsername(lcMessageReceiverWDTO.getUsername());
	    lcMessage.setSender(pSender);
	    lcMessage.setReceiver(lcReceiver);
	    lcResult.add(lcMessage);
	}
	return lcResult;
    }

    private MessageWebserviceDTO decryptMessageWebserviceDTO(MessageWebserviceDTO pMessageWebserviceDTO) throws EncryptionExceptionBiohazard {
	MessageWebserviceDTO lcMessageWebserviceDTO = pMessageWebserviceDTO;
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(pMessageWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);

	    lcMessageWebserviceDTO.setTokenId(encryptionObjectModifier.symmetricDecrypt(lcMessageWebserviceDTO.getTokenId(), lcSymmetricKey));
	    for (MessageContentWebserviceDTO mcwDTO : lcMessageWebserviceDTO.getContent()) {
		mcwDTO.setData(encryptionObjectModifier.symmetricDecrypt(mcwDTO.getData(), lcSymmetricKey));
		mcwDTO.setFilename(encryptionObjectModifier.symmetricDecrypt(mcwDTO.getFilename(), lcSymmetricKey));
		for (MessageContentKeyWebserviceDTO mckwDTO : mcwDTO.getSymmetricKeys()) {
		    mckwDTO.setUsername(encryptionObjectModifier.symmetricDecrypt(mckwDTO.getUsername(), lcSymmetricKey));
		    mckwDTO.setSymmetricEncryptionKey(encryptionObjectModifier.symmetricDecrypt(mckwDTO.getSymmetricEncryptionKey(), lcSymmetricKey));
		}
	    }
	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	    throw new EncryptionExceptionBiohazard();
	}
	return lcMessageWebserviceDTO;
    }
}
