package de.bio.hazard.securemessage.model;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.bio.hazard.securemessage.model.helper.MessageReceiverType;

@Entity
@Table(name = "MESSAGERECEIVER")
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = MessageReceiver.FIND_ALL, query = "from MessageReceiver mr"),
		@NamedQuery(name = MessageReceiver.FIND_BY_MESSAGE, query = "select distinct mr from MessageReceiver mr JOIN mr.messages messages WHERE messages IN ( ?1)") })
public class MessageReceiver {

	public static final String FIND_ALL = "FIND_ALL";
	public static final String FIND_BY_MESSAGE = "FIND_BY_MESSAGE";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@ManyToMany
	private List<Message> messages;

	@Column(unique = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private MessageReceiverType messageReceiverType;

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

	public MessageReceiverType getMessageReceiverType() {
		return messageReceiverType;
	}

	public void setMessageReceiverType(MessageReceiverType messageReceiverType) {
		this.messageReceiverType = messageReceiverType;
	}

}
