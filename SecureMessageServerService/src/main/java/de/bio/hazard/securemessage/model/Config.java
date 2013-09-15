package de.bio.hazard.securemessage.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG")
@Access(AccessType.FIELD)
@NamedQueries({ @NamedQuery(name = Config.FIND_ALL, query = "from Config c") })
public class Config {

	public static final String FIND_ALL = "Config.FIND_ALL";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@Column(unique = false, nullable = false)
	private String description = "";

	@Column(unique = true, nullable = false)
	private int runningNumber = 1;

	@Column(unique = true, nullable = false)
	private byte[] value = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(int runningNumber) {
		this.runningNumber = runningNumber;
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}
}
