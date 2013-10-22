package de.bio.hazard.securemessage.model;

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
import javax.persistence.Table;

@Entity
@Table(name = "DEVICE")
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = Device.FIND_ALL, query = "from Device d"),
		@NamedQuery(name = Device.FIND_BY_USER, query = "from Device d where user = ?1"),
		@NamedQuery(name = Device.FIND_BY_DEVICE_ID, query = "from Device d where deviceId = ?1"),
		@NamedQuery(name = Device.COUNT_BY_DEVICE_ID, query = "select count(d) from Device d where deviceId = ?1") })
public class Device {

	public static final String FIND_ALL = "Device.FIND_ALL";
	public static final String FIND_BY_USER = "Device.FIND_BY_USER";
	public static final String FIND_BY_DEVICE_ID = "Device.FIND_BY_DEVICE_ID";
	public static final String COUNT_BY_DEVICE_ID = "Device.COUNT_BY_DEVICE_ID";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@Column(unique = false, nullable = false)
	private String deviceDescription = "";

	@Column(unique = false, nullable = false)
	@Basic(fetch = FetchType.LAZY)
	private byte[] publicAsyncKey;

	@Column(unique = false, nullable = false)
	private boolean activated = true;

	@Column(unique = true, nullable = false)
	private String deviceId = "";

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceDescription() {
		return deviceDescription;
	}

	public void setDeviceDescription(String deviceDescription) {
		this.deviceDescription = deviceDescription;
	}

	public byte[] getPublicAsyncKey() {
		return publicAsyncKey;
	}

	public void setPublicAsyncKey(byte[] publicAsyncKey) {
		this.publicAsyncKey = publicAsyncKey;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
