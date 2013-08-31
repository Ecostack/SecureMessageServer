package de.bio.hazard.securemessage.model;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Access(AccessType.FIELD)
@NamedQueries({
	@NamedQuery(name = User.FIND_ALL, query = "from User u"),
	@NamedQuery(name = User.FIND_BY_USERNAME, query = "from User u where name = ?") ,
	@NamedQuery(name = User.FIND_BY_EMAIL, query = "from User u where email = ?") })
public class User {

	public static final String FIND_ALL = "User.FIND_ALL";
	public static final String FIND_BY_USERNAME = "User.FIND_BY_USERNAME";
	public static final String FIND_BY_EMAIL = "User.FIND_BY_EMAIL";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private long id;

	@Column(unique = false, nullable = false)
	private String name;

	@Column(unique = false, nullable = true)
	private String vorname;

	@Column(unique = false, nullable = true)
	private String email;

	@Column(unique = false, nullable = true)
	private String password;
	
	@Column(unique = false, nullable = true)
	private Date letzterLoginAm;

	@ManyToOne
	private UserRole role;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLetzterLoginAm() {
		return letzterLoginAm;
	}

	public void setLetzterLoginAm(Date letzterLoginAm) {
		this.letzterLoginAm = letzterLoginAm;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setId(long id) {
		this.id = id;
	}


}
