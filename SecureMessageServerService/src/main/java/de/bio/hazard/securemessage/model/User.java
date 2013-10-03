package de.bio.hazard.securemessage.model;

import java.util.Calendar;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = User.FIND_ALL, query = "from User u"),
		@NamedQuery(name = User.FIND_BY_USERNAME, query = "from User u where username = ?1"),
		@NamedQuery(name = User.FIND_BY_PHONENUMBER, query = "from User u where phonenumber = ?1"),
		@NamedQuery(name = User.FIND_BY_EMAIL, query = "from User u where email = ?1") })
public class User {

	public static final String FIND_ALL = "User.FIND_ALL";
	public static final String FIND_BY_USERNAME = "User.FIND_BY_USERNAME";
	public static final String FIND_BY_EMAIL = "User.FIND_BY_EMAIL";
	public static final String FIND_BY_PHONENUMBER = "User.FIND_BY_PHONENUMBER";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = false, nullable = false)
	private String password;

	@Column(unique = false, nullable = false)
	private String name = "";

	@Column(unique = false, nullable = false)
	private String prename = "";

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = false, nullable = false)
	private String telephone = "";

	@Column(unique = false, nullable = false)
	@Lob
	private byte[] publicKeyForMessaging;

	@Column(unique = false, nullable = true)
	private Calendar lastLoginAt;

	@Column(unique = false, nullable = true)
	private Calendar creationDate;

	@ManyToOne
	private UserRole role;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Message> messages;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Device> devices;

	@Column(unique = false, nullable = false)
	private boolean deactivated = false;

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Calendar getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Calendar lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean isDeactivated() {
		return deactivated;
	}

	public void setDeactivated(boolean deactivated) {
		this.deactivated = deactivated;
	}

	public byte[] getPublicKeyForMessaging() {
		return publicKeyForMessaging;
	}

	public void setPublicKeyForMessaging(byte[] publicKeyForMessaging) {
		this.publicKeyForMessaging = publicKeyForMessaging;
	}

}
