package de.bio.hazard.securemessage.model;

import java.sql.Blob;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class MessageAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	// TODO Binding
	private Message message;

	@Column
	private Blob data;

	@Column
	private String dataHash;
}
