package de.bio.hazard.securemessage.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bio.hazard.securemessage.dto.device.NewDeviceWebserviceDTO;
import de.bio.hazard.securemessage.dto.user.NewUserWebserviceDTO;
import de.bio.hazard.securemessage.facade.NewDeviceFacade;
import de.bio.hazard.securemessage.facade.NewUserFacade;

@WebService
@Component
public class AuthenticationWebservice  {

	@Autowired
	private NewUserFacade newUserFacade;
	
	@Autowired
	private NewDeviceFacade newDeviceFacade;
	
	@WebMethod
	public void addNewUser(NewUserWebserviceDTO pNewUser) {
		newUserFacade.addNewUser(pNewUser);
	}
	
	@WebMethod
	public void addNewDevice(NewDeviceWebserviceDTO pNewDevice) {
		newDeviceFacade.addNewDevice(pNewDevice);
	}


}
