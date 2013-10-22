package de.bio.hazard.securemessage.db.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<Entity, PrimaryKey extends Serializable> {

	PrimaryKey getPrimaryKey(Entity persistentObject);

	PrimaryKey create(Entity newPersistentObject);

	void save(Entity persistentObject);

	void remove(Entity persistentObject);

	void remove(PrimaryKey key);

	Entity findByPrimaryKey(PrimaryKey key);

	List<Entity> findAll();

}