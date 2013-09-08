package de.bio.hazard.securemessage.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.bio.hazard.securemessage.model.helper.MessageContentType;

@Entity
@Table(name = "MESSAGECONTENT")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = MessageContent.FIND_ALL, query = "from MessageContent mc"),
	@NamedQuery(name = MessageContent.FIND_BY_MESSAGE, query = "select distinct mc from MessageContent mc JOIN mc.messages messages WHERE messages IN ( ?)")})
public class MessageContent {

	public static final String FIND_ALL = "MessageContent.FIND_ALL";
	public static final String FIND_BY_MESSAGE = "MessageContent.FIND_BY_MESSAGE";


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Message> messages = new ArrayList<Message>();

	@Column(unique = false, nullable = false)
	private byte[] data;

	@Column(unique = false, nullable = false)
	private MessageContentType messageContentType;

	@Column(unique = false, nullable = false)
	private byte[] synchEncryptionKey;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public MessageContentType getMessageContentType() {
		return messageContentType;
	}

	public void setMessageContentType(MessageContentType messageContentType) {
		this.messageContentType = messageContentType;
	}

	public byte[] getSynchEncryptionKey() {
		return synchEncryptionKey;
	}

	public void setSynchEncryptionKey(byte[] synchEncryptionKey) {
		this.synchEncryptionKey = synchEncryptionKey;
	}

}
