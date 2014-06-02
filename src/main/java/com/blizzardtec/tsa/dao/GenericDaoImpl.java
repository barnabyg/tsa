 /*************************************************************************
 *  2013 BHGAGILE
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of BHGAGILE.
 */
package com.blizzardtec.tsa.dao;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Generic ORM DAO implementation.
 *
 * @author Barnaby Golden
 *
 * @param <T> param
 * @param <P> param
 */
public class GenericDaoImpl<T, P extends Serializable>
                                    implements GenericDao<T, P> {

    /**
     * Class type.
     */
    private final transient Class<T> type;
    /**
     * Hibernate template.
     */
    private transient HibernateTemplate hibernateTemplate;

    /**
     * Constructor - takes the class type.
     * @param type class type
     */
    public GenericDaoImpl(final Class<T> type) {

        this.type = type;
    }

    /**
     * Set the session factory through injection.
     * @param sessionFactory session factory
     */
    @Autowired
    public final void setSessionFactory(final SessionFactory sessionFactory) {

        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public final P create(final T entity) {

        return (P) getHibernateTemplate().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    public final T retrieve(final P id) {

        return (T) getHibernateTemplate().get(type, id);
    }

    /**
     * {@inheritDoc}
     */
    public final void update(final T entity) {

        getHibernateTemplate().update(entity);
    }

    /**
     * {@inheritDoc}
     */
    public final void delete(final T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * Return the Hibernate template.
     * @return template
     */
    protected final HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }
}
