package de.bio.hazard.securemessage.db.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractGenericDAO<Entity, PrimaryKey extends Serializable>
		implements GenericDAO<Entity, PrimaryKey> {

	/** Der zentrale Logger für die DAO's. */
	protected Logger logger = LoggerFactory.getLogger(GenericDAO.class);

	/** Die Klasse der Entität. */
	protected final Class<Entity> entityClass;

	/** Die Klasse des primären Key. */
	protected final Class<PrimaryKey> primaryKeyClass;

	{
		ParameterizedType type = (ParameterizedType) getClass()
				.getGenericSuperclass();
		Type[] actualTypeArguments = type.getActualTypeArguments();

		@SuppressWarnings("unchecked")
		Class<Entity> entityClass = (Class<Entity>) actualTypeArguments[0];
		this.entityClass = entityClass;

		@SuppressWarnings("unchecked")
		Class<PrimaryKey> primaryKey = (Class<PrimaryKey>) actualTypeArguments[1];
		this.primaryKeyClass = primaryKey;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see GenericDAO#remove(java.io.Serializable)
	 */
	@Override
	public void remove(PrimaryKey key) {
		remove(findByPrimaryKey(key));
	}

}