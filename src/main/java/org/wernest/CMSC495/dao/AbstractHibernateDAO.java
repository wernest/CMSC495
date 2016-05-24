package org.wernest.CMSC495.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDAO< T extends Serializable> {

    private Class< T > clazz;

    SessionFactory sessionFactory;

    public void setClazz( final Class< T > clazzToSet ){
        this.clazz = clazzToSet;
    }

    public T findOne( final Long id ){
        return (T) getCurrentSession().get( clazz, id );
    }
    public List< T > findAll(){
        return getCurrentSession()
                .createQuery( "from " + clazz.getName() ).list();
    }

    public void save( final T entity ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.flush();
        session.close();
    }

    public void update( final T entity ){
        getCurrentSession().merge( entity );
    }

    public void delete( final T entity ){
        getCurrentSession().delete( entity );
    }
    public void deleteById( final Long entityId ){
        final T entity = findOne( entityId );

        delete( entity );
    }

    protected Session getCurrentSession(){
        if(sessionFactory == null) {
            this.sessionFactory = HibernateUtil.getSessionFactory();
        }
        return sessionFactory.getCurrentSession();
    }
}