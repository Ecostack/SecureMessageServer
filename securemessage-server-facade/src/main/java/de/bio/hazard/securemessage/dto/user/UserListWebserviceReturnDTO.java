package de.bio.hazard.securemessage.dto.user;

import java.util.ArrayList;

public class UserListWebserviceReturnDTO {
	private ArrayList<UserListItemWebserviceReturnDTO> listItemWebserviceReturnDTOs;

	public UserListWebserviceReturnDTO(
			ArrayList<UserListItemWebserviceReturnDTO> listItemWebserviceReturnDTOs) {
		super();
		this.listItemWebserviceReturnDTOs = listItemWebserviceReturnDTOs;
	}

	public ArrayList<UserListItemWebserviceReturnDTO> getListItemWebserviceReturnDTOs() {
		return listItemWebserviceReturnDTOs;
	}

	public void setListItemWebserviceReturnDTOs(
			ArrayList<UserListItemWebserviceReturnDTO> listItemWebserviceReturnDTOs) {
		this.listItemWebserviceReturnDTOs = listItemWebserviceReturnDTOs;
	}
}
