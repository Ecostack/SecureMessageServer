package de.bio.hazard.securemessage.dto.message;

import java.util.ArrayList;

public class MessageContentWebserviceDTO {

    private String data;
    private String filename;
  //ACHTUNG: Typ muss Serializable sein / also nicht Interface List<E> ... sondern z.B. ArrayList
    private ArrayList<MessageContentKeyWebserviceDTO> symmetricKeys;

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public ArrayList<MessageContentKeyWebserviceDTO> getSymmetricKeys() {
	if (symmetricKeys == null) {
	    symmetricKeys = new ArrayList<MessageContentKeyWebserviceDTO>();
	}
	return symmetricKeys;
    }

    public String getFilename() {
	return filename;
    }

    public void setFilename(String filename) {
	this.filename = filename;
    }

    public void setSymmetricKeys(ArrayList<MessageContentKeyWebserviceDTO> symmetricKeys) {
        this.symmetricKeys = symmetricKeys;
    }
}
