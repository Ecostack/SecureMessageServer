package de.bio.hazard.securemessage.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepOneReturnDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoDTO;
import de.bio.hazard.securemessage.dto.authentication.AuthenticationStepTwoReturnDTO;
import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceDTO;
import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceReturnDTO;
import de.bio.hazard.securemessage.dto.user.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.facade.AuthenticationFacade;
import de.bio.hazard.securemessage.facade.NewDeviceFacade;
import de.bio.hazard.securemessage.facade.NewUserFacade;
import de.bio.hazard.securemessage.facade.exception.DeviceNotFoundException;
import de.bio.hazard.securemessage.tecframework.exception.EncryptionExceptionBiohazard;

@WebService
@Component
public class AuthenticationWebservice {

	@Autowired
	private NewUserFacade newUserFacade;

	@Autowired
	private NewDeviceFacade newDeviceFacade;

	@Autowired
	private AuthenticationFacade authenticationFacade;

	@WebMethod
	public void addNewUser(NewUserWebserviceDTO pNewUser) throws EncryptionExceptionBiohazard {
		newUserFacade.addNewUser(pNewUser);
	}

	@WebMethod
	public NewDeviceWebserviceReturnDTO addNewDevice(
			NewDeviceWebserviceDTO pNewDevice) {
		return newDeviceFacade.addNewDevice(pNewDevice);

	}

	public AuthenticationStepOneReturnDTO authenticateStepOne(
			AuthenticationStepOneDTO pAuthenticationStepOneDTO)
			throws EncryptionExceptionBiohazard, DeviceNotFoundException {
		return authenticationFacade
				.authenticateStepOne(pAuthenticationStepOneDTO);
	}

	public AuthenticationStepTwoReturnDTO authenticateStepTwo(
			AuthenticationStepTwoDTO pAuthenticationStepTwoDTO)
			throws EncryptionExceptionBiohazard {
		return authenticationFacade
				.authenticateStepTwo(pAuthenticationStepTwoDTO);
	}

}
