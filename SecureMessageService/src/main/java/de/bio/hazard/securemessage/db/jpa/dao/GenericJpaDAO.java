package de.bio.hazard.securemessage.db.jpa.dao;

import java.io.Serializable;

import de.bio.hazard.securemessage.db.dao.GenericDAO;

public interface GenericJpaDAO<Entity, PrimaryKey extends Serializable> extends
		GenericDAO<Entity, PrimaryKey> {

	// Noch keine spezielle Definitionen.

}