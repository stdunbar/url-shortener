package com.hotjoe.persistence.dao.base;

/**
 * Defines operations for a writable DAO.  Not all DAO's support writing
 * but those that do should extend this class.
 * 
 * @param <T> the type of model object that this DAO is for.
 * 
 */
public class BaseWritableDAO<T> extends BaseDAO<T> {

	/**
	 * Save the given entity.
	 * 
	 * @param entity the entity to save.
	 * 
	 */
    public void save(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Update the given entity.
     * 
     * @param entity the entity to update
     * 
     * @return the entity after it has been updated.
     * 
     */
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }


    /**
     * Delete the given entity.
     * 
     * @param entity the entity to delete.
     * 
     */
    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }
}