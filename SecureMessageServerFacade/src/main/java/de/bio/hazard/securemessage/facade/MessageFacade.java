package de.bio.hazard.securemessage.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.message.MessageContentKeyWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageContentWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageReceiverWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.MessageWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.RequestMessageWebserviceDTO;
import de.bio.hazard.securemessage.dto.message.RequestMessageWebserviceReturnDTO;
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
import de.bio.hazard.securemessage.tecframework.encryption.symmetric.SymmetricKeygen;
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
    
    @Autowired
    private SymmetricKeygen symmetricKeygen;

    public void addMessage(MessageWebserviceDTO pMessageWebserviceDTO) throws EncryptionExceptionBiohazard {
	// TODO MessageReceiverType beachten

	MessageWebserviceDTO lcMessageWebserviceDTO = decryptMessageWebserviceDTO(pMessageWebserviceDTO);
	if (lcMessageWebserviceDTO == null) {
	    throw new IllegalArgumentException("MessageWebserviceDTO is not allowed to be null");
	}
	Device lcDevice = authenticationService.getDeviveWhenAuthTokenIsValidWithException(lcMessageWebserviceDTO.getTokenId());
	User lcSender = lcDevice.getUser();
	List<Message> lcMessages = extractMessagesFromMessageWebserviceDTO(lcMessageWebserviceDTO, lcSender);
	addMessage(lcMessages);
	extractAndAddMessageContent(lcMessageWebserviceDTO, lcMessages);
    }

    public RequestMessageWebserviceReturnDTO getMessages(RequestMessageWebserviceDTO pRequestMessageDTO) throws EncryptionExceptionBiohazard {
	RequestMessageWebserviceDTO lcRequestMessageDTO = decryptRequestMessageWebserviceDTO(pRequestMessageDTO);
	RequestMessageWebserviceReturnDTO lcRequestMessageReturnDTO = new RequestMessageWebserviceReturnDTO();
	Device lcDevice = authenticationService.getDeviveWhenAuthTokenIsValidWithException(lcRequestMessageDTO.getTokenId());
	User lcSender = lcDevice.getUser();
	List<Message> lcUserMessages = messageService.getMessagesByReceiver(lcSender);
	// TODO andere Receiver übermitteln
	for (Message lcMessage : lcUserMessages) {
	    MessageWebserviceDTO lcMessageWebserviceDTO = new MessageWebserviceDTO();
	    List<MessageContent> lcMessageContents = messageContentService.getMessagesContentsByMessage(lcMessage);
	    for (MessageContent lcMessageContent : lcMessageContents) {
		MessageContentWebserviceDTO lcContentWebserviceDTO = new MessageContentWebserviceDTO();
		lcContentWebserviceDTO.setData(new String(lcMessageContent.getData()));
		lcContentWebserviceDTO.setFilename(lcMessageContent.getFilename());

		MessageContentKey lcMessageContentKey = messageContentKeyService.getMessagesContentKeysByMessageAndMessageContent(lcMessage.getId(), lcMessageContent.getId());
		MessageContentKeyWebserviceDTO lcContentKeyWebserviceDTO = new MessageContentKeyWebserviceDTO();
		lcContentKeyWebserviceDTO.setSymmetricEncryptionKey(lcMessageContentKey.getSymmetricEncryptionKey());
		lcContentWebserviceDTO.getSymmetricKeys().add(lcContentKeyWebserviceDTO);
		lcMessageWebserviceDTO.getContent().add(lcContentWebserviceDTO);
	    }
	    lcRequestMessageReturnDTO.getMessages().add(lcMessageWebserviceDTO);
	}
	lcRequestMessageReturnDTO = encryptRequestMessageWebserviceReturnDTO(lcRequestMessageReturnDTO, lcDevice);
	return lcRequestMessageReturnDTO;
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
			lcMessageContentKey.setSymmetricEncryptionKey(lcMessageContentKeyWDTO.getSymmetricEncryptionKey());
			lcMessageContentKey.setMessageContent(lcMessageContent);
			lcMessageContentKey.setMessage(lcMessage);
			messageContentKeyService.addMessageContentKey(lcMessageContentKey);
		    }
		}
	    }
	}
    }

    private MessageWebserviceDTO decryptMessageWebserviceDTO(MessageWebserviceDTO pMessageWebserviceDTO) throws EncryptionExceptionBiohazard {
	MessageWebserviceDTO lcMessageWebserviceDTO = pMessageWebserviceDTO;
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(lcMessageWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);

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

    private RequestMessageWebserviceDTO decryptRequestMessageWebserviceDTO(RequestMessageWebserviceDTO pRequestMessageDTO) throws EncryptionExceptionBiohazard {
	RequestMessageWebserviceDTO lcRequestMessageWebserviceDTO = pRequestMessageDTO;
	try {
	    Config lcServerPrivateKey = configService.getConfigByEnumType(ConfigType.SERVER_PRIVATE_KEY);
	    byte[] lcSymmetricKey = encryptionObjectModifier.asymmetricDecryptToByte(lcRequestMessageWebserviceDTO.getSymEncryptionKey(), lcServerPrivateKey.getValue(), true);
	    lcRequestMessageWebserviceDTO.setTokenId(encryptionObjectModifier.symmetricDecrypt(lcRequestMessageWebserviceDTO.getTokenId(), lcSymmetricKey));
	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	    throw new EncryptionExceptionBiohazard();
	}
	return lcRequestMessageWebserviceDTO;
    }

    private RequestMessageWebserviceReturnDTO encryptRequestMessageWebserviceReturnDTO(RequestMessageWebserviceReturnDTO pRequestMessageReturnDTO, Device pDevice) throws EncryptionExceptionBiohazard {
	RequestMessageWebserviceReturnDTO lcRequestMessageWebserviceReturnDTO = pRequestMessageReturnDTO;
	try {
	    byte[] lcDevicePublicKey = pDevice.getPublicAsyncKey();
	    byte[] lcSymmetricKey = //symmetricKeygen.getKey(128);
		    //TODO NicoH; DEBUG!!
		    new byte[] {57, -104, 10, -47, 66, 8, -17, -19, 126, -23, 92, -92, -116, -66, 111, 100};
	    
	    lcRequestMessageWebserviceReturnDTO.setSymEncryptionKey(encryptionObjectModifier.asymmetricEncrypt(lcSymmetricKey, lcDevicePublicKey, false));
	    System.err.println("encryptedKey: "+lcRequestMessageWebserviceReturnDTO.getSymEncryptionKey());
	    for(MessageWebserviceDTO lcMessage : lcRequestMessageWebserviceReturnDTO.getMessages()) {
		for(MessageContentWebserviceDTO lcMessageContent : lcMessage.getContent()) {
		    lcMessageContent.setData(encryptionObjectModifier.symmetricEncrypt(lcMessageContent.getData(), lcSymmetricKey));
		    lcMessageContent.setFilename(encryptionObjectModifier.symmetricEncrypt(lcMessageContent.getFilename(), lcSymmetricKey));
		    for(MessageContentKeyWebserviceDTO lcMessageContentKey : lcMessageContent.getSymmetricKeys()) {
			lcMessageContentKey.setSymmetricEncryptionKey(encryptionObjectModifier.symmetricEncrypt(lcMessageContentKey.getSymmetricEncryptionKey(), lcSymmetricKey));
		    }
		}
	    }
	}
	catch (Exception e) {
	    // TODO SebastianS; Logging
	    e.printStackTrace();
	    throw new EncryptionExceptionBiohazard();
	}
	return lcRequestMessageWebserviceReturnDTO;
    }
}
