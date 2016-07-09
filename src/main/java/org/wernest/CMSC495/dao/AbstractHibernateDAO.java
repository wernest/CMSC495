package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Abstract DAO to provide the boilerplate crud operations
 * @param <T>
 */
public abstract class AbstractHibernateDAO< T extends Serializable> {

    private Class< T > clazz;

    SessionFactory sessionFactory;

    /**
     * Sets the class for use with finding entities
     * @param clazzToSet Class to get
     */
    public void setClazz( final Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    /**
     * Locates a single record by ID and returns it
     * @param id Integer
     * @return Object with the matching id
     */
    public T findOne( final Integer id ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        T t = (T) session.get(clazz, id);
        session.getTransaction().commit();
        session.close();
        return t;
    }

    /**
     * Returns all objects of type clazz in the database
     * @return List of T Objects
     */
    public List< T > findAll(){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<T> list = session.createQuery( "from " + clazz.getName() ).list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    /**
     * Saves if new, Updates if it already exists.
     * @param entity entity to be saved
     */
    public void save( final T entity ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Delete's the object provided
     * @param entity to delete
     */
    public void delete( final T entity ){
        Session session = getCurrentSession();
        session.beginTransaction();
        session.delete( entity );
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Utility method to delete by ID
     * @param entityId ID of entity to delete
     */
    public void deleteById( final Integer entityId ){
        final T entity = findOne( entityId );

        delete( entity );
    }

    /**
     * Utility method to get the current Hibernate Session
     * @return Session
     */
    protected Session getCurrentSession() {
        if (sessionFactory == null) {
            this.sessionFactory = HibernateUtil.getSessionFactory();
        }
        return sessionFactory.openSession();
    }

}