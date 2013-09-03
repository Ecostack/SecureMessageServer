package de.bio.hazard.securemessage.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = Message.FIND_ALL, query = "from Message m")
 })
public class Message {
	
	public static final String FIND_ALL = "Message.FIND_ALL";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;
	
	@Column
	private String subject;
	
	@Column
	private String messageText;
	
	@Column
	@ManyToOne(fetch=FetchType.LAZY)
	private List<MessageAttachment> attachments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	
}
