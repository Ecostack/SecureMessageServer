package de.bio.hazard.securemessage.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGECONTENTKEY")
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = MessageContentKey.FIND_ALL, query = "from MessageContentKey mck"),
		@NamedQuery(name = MessageContentKey.FIND_BY_MESSAGE, query = "from MessageContentKey mck where mck.message.id = ?1"),
		@NamedQuery(name = MessageContentKey.FIND_BY_MESSAGECONTENT, query = "from MessageContentKey mck where mck.messageContent.id = ?1"),
		@NamedQuery(name = MessageContentKey.FIND_BY_MESSAGE_AND_MESSAGECONTENT, query = "from MessageContentKey mck where mck.message.id = ?1 and mck.messageContent.id = ?2")})
public class MessageContentKey {

	public static final String FIND_ALL = "MessageContentKey.FIND_ALL";
	public static final String FIND_BY_MESSAGE = "MessageContentKey.FIND_BY_MESSAGE";
	public static final String FIND_BY_MESSAGECONTENT = "MessageContentKey.FIND_BY_MESSAGECONTENT";
	public static final String FIND_BY_MESSAGE_AND_MESSAGECONTENT = "MessageContentKey.FIND_BY_MESSAGE_AND_MESSAGECONTENT";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	//TODO SebastianS; Check Cascade; anders war es mir bisher nicht moeglich
	@ManyToOne(fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private Message message = null;

	//TODO SebastianS; Check Cascade; anders war es mir bisher nicht moeglich
	@ManyToOne(fetch=FetchType.LAZY, cascade= {CascadeType.REFRESH})
	private MessageContent messageContent = null;

	@Column(unique = false, nullable = false)
	//@Lob
	//private byte[] synchEncryptionKey;
	private String symmetricEncryptionKey;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	public byte[] getSynchEncryptionKey() {
//		return synchEncryptionKey;
//	}
//
//	public void setSynchEncryptionKey(byte[] synchEncryptionKey) {
//		this.synchEncryptionKey = synchEncryptionKey;
//	}
	
	public String getSymmetricEncryptionKey() {
	    return symmetricEncryptionKey;
	}

	public void setSymmetricEncryptionKey(String synchEncryptionKey) {
	    this.symmetricEncryptionKey = synchEncryptionKey;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageContent getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(MessageContent messageContent) {
		this.messageContent = messageContent;
	}

}
