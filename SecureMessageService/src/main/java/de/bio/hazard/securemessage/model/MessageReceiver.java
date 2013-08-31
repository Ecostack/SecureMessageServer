package de.bio.hazard.securemessage.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.bio.hazard.securemessage.model.helper.MessageReceiverVisibility;

public class MessageReceiver {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;
	
	@Column
	private User user;
	
	@Column
	@Enumerated(EnumType.STRING)
	private MessageReceiverVisibility visibility;
	
}
