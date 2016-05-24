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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        T t = (T) session.get(clazz, id);
        session.getTransaction().commit();
        session.close();
        return t;
    }
    public List< T > findAll(){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<T> list = session.createQuery( "from " + clazz.getName() ).list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    public void save( final T entity ){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    public void update( final T entity ){
        Session session = getCurrentSession();
        session.beginTransaction();
        session.merge( entity );
        session.getTransaction().commit();
        session.close();
    }

    public void delete( final T entity ){
        Session session = getCurrentSession();
        session.beginTransaction();
        session.delete( entity );
        session.getTransaction().commit();
        session.close();
    }
    public void deleteById( final Long entityId ){
        final T entity = findOne( entityId );

        delete( entity );

    }

    protected Session getCurrentSession() {
        if (sessionFactory == null) {
            this.sessionFactory = HibernateUtil.getSessionFactory();
        }
        return sessionFactory.openSession();
    }

}