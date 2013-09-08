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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = Message.FIND_ALL, query = "from Message m") })
public class Message {

	public static final String FIND_ALL = "Message.FIND_ALL";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@ManyToOne(fetch=FetchType.LAZY)
	private User sender;

	@ManyToOne(fetch=FetchType.LAZY)
	private User receiver;

	@ManyToMany(fetch = FetchType.LAZY,mappedBy="messages")
	private List<MessageContent> contents = new ArrayList<MessageContent>();

	@ManyToMany(fetch = FetchType.LAZY)
	private List<MessageReceiver> messageReceivers = new ArrayList<MessageReceiver>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public List<MessageContent> getContents() {
		return contents;
	}

	public void setContents(List<MessageContent> contents) {
		this.contents = contents;
	}

	public List<MessageReceiver> getMessageReceivers() {
		return messageReceivers;
	}

	public void setMessageReceivers(List<MessageReceiver> messageReceivers) {
		this.messageReceivers = messageReceivers;
	}

}
