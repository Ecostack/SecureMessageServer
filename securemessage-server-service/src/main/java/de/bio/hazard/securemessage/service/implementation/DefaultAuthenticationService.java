package de.bio.hazard.securemessage.service.implementation;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.bio.hazard.securemessage.model.AuthenticationToken;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.HandshakeToken;
import de.bio.hazard.securemessage.model.User;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOne;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOneReturn;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwo;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwoReturn;
import de.bio.hazard.securemessage.service.AuthenticationService;
import de.bio.hazard.securemessage.service.DeviceService;
import de.bio.hazard.securemessage.service.UserService;
import de.bio.hazard.securemessage.tecframework.data.IdGenerator;
import de.bio.hazard.securemessage.tecframework.encryption.hashing.BCrypt;
import de.bio.hazard.securemessage.tecframework.exception.AuthenticationExceptionBiohazard;

@Service
public class DefaultAuthenticationService implements AuthenticationService {
	private HashMap<String, AuthenticationToken> authTokens = new HashMap<String, AuthenticationToken>();
	private HashMap<String, HandshakeToken> handshakeTokens = new HashMap<String, HandshakeToken>();

	@Autowired
	private UserService userService;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private IdGenerator idGenerator;

	@Override
	public AuthenticationStepOneReturn authenticationStepOne(
			AuthenticationStepOne pAuthenticationStepOne) {
		Device lcDevice = deviceService
				.getDeviceByDeviceId(pAuthenticationStepOne.getDeviceId());
		if (lcDevice != null) {
			User lcUser = userService.getUserByUsername(pAuthenticationStepOne
					.getUsername());

			if (lcUser != null) {
				if (BCrypt.checkpw(pAuthenticationStepOne.getPassword(),
						lcUser.getPassword())) {
					return getAuthenticationStepOne(lcDevice);
				}
			}
			throw new RuntimeException("Username or password is wrong.");
		} else {
			throw new RuntimeException("Device not found! FIX ME!");
		}
	}

	@Override
	public AuthenticationStepTwoReturn authenticationStepTwo(
			AuthenticationStepTwo pAuthenticationStepTwo) {

		String lcHandshakeId = pAuthenticationStepTwo.getHandshakeId();
		HandshakeToken lcHandshakeToken = handshakeTokens.get(lcHandshakeId);

		if (isHandshakeTokenValid(lcHandshakeId)) {
			if (isRandomHashedValueMatching(
					pAuthenticationStepTwo.getRandomHashedValue(),
					lcHandshakeId)) {
				Device lcDevice = deviceService
						.getDeviceByDeviceId(lcHandshakeToken.getDeviceId());

				return getAuthenticationStepTwo(lcDevice);
			}
			throw new RuntimeException("You are not the correct device!");
		} else {
			throw new RuntimeException("Handshake is not more valid!");
		}
	}

	@Override
	public AuthenticationStepOneReturn getAuthenticationStepOne(Device pDevice) {
		AuthenticationStepOneReturn lcReturn = new AuthenticationStepOneReturn();

		String lcRandomHashValue = idGenerator.nextId();

		lcReturn.setHandshakeId(this.getNewHandshakeToken(pDevice,
				lcRandomHashValue).getTokenid());
		lcReturn.setRandomHashedValue(lcRandomHashValue);
		return lcReturn;
	}

	@Override
	public AuthenticationStepTwoReturn getAuthenticationStepTwo(Device pDevice) {
		AuthenticationStepTwoReturn lcDTO = new AuthenticationStepTwoReturn();
		lcDTO.setTokenId(getNewAuthenticationToken(pDevice).getTokenid());
		return lcDTO;
	}

	public synchronized AuthenticationToken getNewAuthenticationToken(
			Device pDevice) {
		String lcTokenId = idGenerator.nextId();
		while (authTokens.containsKey(lcTokenId)) {
			lcTokenId = idGenerator.nextId();
		}

		System.err.println("authtoken create and put server: " + lcTokenId);
		AuthenticationToken lcToken = new AuthenticationToken(lcTokenId);
		lcToken.setDeviceId(pDevice.getDeviceId());
		authTokens.put(lcTokenId, lcToken);
		return lcToken;

	}

	@Override
	public HandshakeToken getNewHandshakeToken(Device pDevice,
			String pRandomHashValue) {

		String lcHandshakeId = idGenerator.nextId();
		while (handshakeTokens.containsKey(lcHandshakeId)) {
			lcHandshakeId = idGenerator.nextId();
		}

		HandshakeToken lcToken = new HandshakeToken(lcHandshakeId);

		System.err.println("handshake create and put server: " + lcHandshakeId);

		lcToken.setDeviceId(pDevice.getDeviceId());
		lcToken.setRandomHashValue(pRandomHashValue);
		handshakeTokens.put(lcHandshakeId, lcToken);
		return lcToken;
	}

	public synchronized boolean isAuthTokenValid(String pTokenIdToCheck) {
		if (!pTokenIdToCheck.isEmpty()) {
			AuthenticationToken lcToken = authTokens.get(pTokenIdToCheck);
			if (!lcToken.isInvalid()) {
				return true;
			} else {
				authTokens.remove(pTokenIdToCheck);
			}
		}
		return false;
	}

	public boolean isAuthTokenValidWithException(String pTokenIdToCheck) {
		if (isAuthTokenValid(pTokenIdToCheck)) {
			return true;
		}
		throw new AuthenticationExceptionBiohazard(
				"Invalid Authentication-Token");
	}

	public Device getDeviveWhenAuthTokenIsValidWithException(
			String pTokenIdToCheck) {
		if (isAuthTokenValidWithException(pTokenIdToCheck)) {
			return deviceService
					.getDeviceByDeviceId(getDeviceIdByTokenId(pTokenIdToCheck));
		}
		// unereichbarer Code durch Runtime-Exception
		return null;
	}

	@Override
	public boolean isHandshakeTokenValid(String pTokenIdToCheck) {
		if (!pTokenIdToCheck.isEmpty()) {
			HandshakeToken lcToken = handshakeTokens.get(pTokenIdToCheck);
			if (lcToken != null) {
				if (!lcToken.isInvalid()) {
					return true;
				} else {
					handshakeTokens.remove(pTokenIdToCheck);
				}
			}
		}
		return false;
	}

	public boolean isRandomHashedValueMatching(String pToEqual,
			String pHandshakeId) {
		if (handshakeTokens.containsKey(pHandshakeId)) {
			HandshakeToken lcToken = handshakeTokens.get(pHandshakeId);
			if (lcToken.getRandomHashValue().equals(pToEqual)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDeviceIdByTokenId(String pTokenId) {
		if (authTokens.containsKey(pTokenId)) {
			AuthenticationToken lcToken = authTokens.get(pTokenId);
			return lcToken.getDeviceId();
		}
		throw new RuntimeException("No device by token found.");
	}

}
