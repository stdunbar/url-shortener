package com.hotjoe.persistence.dao;

import com.hotjoe.persistence.dao.base.BaseWritableDAO;
import com.hotjoe.persistence.model.ShortUrl;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class ShortUrlDAO extends BaseWritableDAO<ShortUrl> {

    @SuppressWarnings("unchecked")
    private List<ShortUrl> findByProperty(String propertyName, final Object value) {
        final String queryString = "select model from ShortUrl model where model."
                + propertyName + "= :propertyValue";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("propertyValue", value);
        return query.getResultList();
    }

    public ShortUrl findById( Integer id ) {
        List<ShortUrl> shortUrls = findByProperty("shortUrlId", id);

        if( shortUrls.size() == 0)
            return null;

        return shortUrls.get(0);
    }

    public ShortUrl findByEncryptedId( Integer id ) {
        List<ShortUrl> shortUrls = findByProperty("shortUrlEncryptedId", id);

        if( shortUrls.size() == 0)
            return null;

        return shortUrls.get(0);
    }
}
