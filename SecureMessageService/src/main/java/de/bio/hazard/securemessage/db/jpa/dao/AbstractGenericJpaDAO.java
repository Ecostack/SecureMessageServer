package de.bio.hazard.securemessage.db.jpa.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.bio.hazard.securemessage.db.dao.AbstractGenericDAO;
import de.bio.hazard.securemessage.db.dao.GenericDAO;

public abstract class AbstractGenericJpaDAO<Entity, PrimaryKey extends Serializable>
		extends AbstractGenericDAO<Entity, PrimaryKey> implements
		GenericJpaDAO<Entity, PrimaryKey> {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @return Liefert den {@link #entityManager}
	 */
	protected final EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GenericDAO#create(java.lang.Object)
	 */
	@Override
	public PrimaryKey create(Entity newPersistentObject) {
		entityManager.persist(newPersistentObject);
		return getPrimaryKey(newPersistentObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GenericDAO#save(java.lang.Object)
	 */
	@Override
	public void save(Entity persistentObject) {
		entityManager.merge(persistentObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GenericDAO#remove(java.lang.Object)
	 */
	@Override
	public void remove(Entity persistentObject) {
		entityManager.remove(persistentObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GenericDAO#findByPrimaryKey(java.io.Serializable)
	 */
	@Override
	public Entity findByPrimaryKey(PrimaryKey key) {
		return entityManager.find(entityClass, key);
	}

	public void refresh(Entity pEntity) {
		entityManager.refresh(pEntity);
	}

}