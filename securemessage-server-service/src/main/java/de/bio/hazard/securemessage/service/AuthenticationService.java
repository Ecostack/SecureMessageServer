package de.bio.hazard.securemessage.service;

import de.bio.hazard.securemessage.model.AuthenticationToken;
import de.bio.hazard.securemessage.model.Device;
import de.bio.hazard.securemessage.model.HandshakeToken;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOne;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepOneReturn;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwo;
import de.bio.hazard.securemessage.model.facade.AuthenticationStepTwoReturn;

public interface AuthenticationService {

    String getDeviceIdByTokenId(String pTokenId);

    AuthenticationStepOneReturn authenticationStepOne(AuthenticationStepOne pAuthenticationStepOne);

    AuthenticationStepTwoReturn authenticationStepTwo(AuthenticationStepTwo pAuthenticationStepTwo);

    AuthenticationStepOneReturn getAuthenticationStepOne(Device pDevice);

    AuthenticationStepTwoReturn getAuthenticationStepTwo(Device pDevice);

    AuthenticationToken getNewAuthenticationToken(Device pDevice);

    HandshakeToken getNewHandshakeToken(Device pDevice, String pRandomHashValue);

    boolean isAuthTokenValid(String pTokenIdToCheck);

    boolean isAuthTokenValidWithException(String pTokenIdToCheck);

    Device getDeviveWhenAuthTokenIsValidWithException(String pTokenIdToCheck);

    boolean isHandshakeTokenValid(String pTokenIdToCheck);

    boolean isRandomHashedValueMatching(String pToEqual, String pHandshakeId);
}
