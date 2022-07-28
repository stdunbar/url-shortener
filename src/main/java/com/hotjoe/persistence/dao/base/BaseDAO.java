package com.hotjoe.persistence.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The base DAO for all other DAO's.  This simply defines the
 * EntityManager for use by any derived classes.
 * 
 * 
 * @author scott
 *
 */
public class BaseDAO<T> {
	
	@PersistenceContext
	EntityManager entityManager;

	/**
	 * Get the EntityManager for this class
	 * 
	 * @return the EntityManager
	 * 
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	/**
	 * Refresh the entity.
	 * 
	 * @param entity the entity to be refreshed.
	 * 
	 */
    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }
}