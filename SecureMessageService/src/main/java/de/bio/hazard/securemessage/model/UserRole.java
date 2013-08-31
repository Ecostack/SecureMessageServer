package de.bio.hazard.securemessage.model;

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

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.bio.hazard.securemessage.util.Statics;



@Entity
@Table(name="USERROLE")
@Access(AccessType.FIELD)
@NamedQueries({@NamedQuery(name = UserRole.FIND_ALL, query = "from UserRole ur"),
	@NamedQuery(name = UserRole.FIND_ALL_BY_USER, query = "from UserRole ur where user = ?"),
	@NamedQuery(name = UserRole.FIND_ALL_BY_TARGETTYPE, query = "from UserRole ur where targetType = ?")})
public class UserRole {
	
	/** Konstante für die NamedQuery. */
	public static final String FIND_ALL = "UserRole.findAll";
	public static final String FIND_ALL_BY_USER = "UserRole.findAllByUser";
	public static final String FIND_ALL_BY_TARGETTYPE = "UserRole.findAllByTargetType";

	
	public static final int ROLE_USER = 1;

	public static final int ROLE_REGISTERED = 2;
	
	public static final int ROLE_PAYING =3;
	
	public static final int ROLE_ADMINISTRATOR = 4;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID", unique = true, nullable = false)
	@Basic(optional = false)
	private Long id;
	
	@Column(unique = false,nullable = false)
	private Integer targetType;
	
	@Column(unique = false,nullable = false)
	private Integer allowance = Statics.ROLE_ALLOWANCE_NONE;
	
	@Column(unique = false,nullable = true)
	private Long targetID;
	
	//@Column(unique = false,nullable = false)
	@LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne()
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAllowance() {
		return allowance;
	}

	public void setAllowance(Integer allowance) {
		this.allowance = allowance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getTargetID() {
		return targetID;
	}

	public void setTargetID(Long targetID) {
		this.targetID = targetID;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allowance == null) ? 0 : allowance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((targetID == null) ? 0 : targetID.hashCode());
		result = prime * result
				+ ((targetType == null) ? 0 : targetType.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (allowance == null) {
			if (other.allowance != null)
				return false;
		} else if (!allowance.equals(other.allowance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (targetID == null) {
			if (other.targetID != null)
				return false;
		} else if (!targetID.equals(other.targetID))
			return false;
		if (targetType == null) {
			if (other.targetType != null)
				return false;
		} else if (!targetType.equals(other.targetType))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
