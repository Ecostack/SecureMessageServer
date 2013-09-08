package de.bio.hazard.securemessage.model;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.bio.hazard.securemessage.model.helper.UserRoleType;

@Entity
@Table(name = "USERROLE")
@Access(AccessType.FIELD)
@NamedQueries({
		@NamedQuery(name = UserRole.FIND_ALL, query = "from UserRole ur"),
		@NamedQuery(name = UserRole.FIND_ALL_BY_USER, query = "from UserRole ur where user = ?"),
		@NamedQuery(name = UserRole.FIND_ALL_BY_TYPE, query = "from UserRole ur where type = ?") })
public class UserRole {

	/** Konstante für die NamedQuery. */
	public static final String FIND_ALL = "UserRole.findAll";
	public static final String FIND_ALL_BY_USER = "UserRole.findAllByUser";
	public static final String FIND_ALL_BY_TYPE = "UserRole.findAllByType";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	@Basic(optional = false)
	private Long id;

	@Column(unique = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRoleType type;

	// @Column(unique = false,nullable = false)
	// TODO SebastianS; Muss evtl. noch eingestellt werden
	// @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.LAZY)
	private List<User> users;

	public UserRoleType getType() {
		return type;
	}

	public void setType(UserRoleType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		// result = prime * result + ((users == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRole other = (UserRole) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		// if (user == null) {
		// if (other.user != null)
		// return false;
		// } else if (!user.equals(other.user))
		// return false;
		return true;
	}

}
