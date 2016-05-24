package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.authentication.SessionIdentifierGenerator;
import org.wernest.CMSC495.entities.UserEntity;

/**
 * Created by will on 5/22/16.
 */
public class UserEntityDAO extends AbstractHibernateDAO<UserEntity>{

    public UserEntity getByEmail(String email){
        return (UserEntity) this.getCurrentSession().getNamedQuery("UserEntity.findByEmail").setString("email", email).uniqueResult();
    }


    public UserEntity getByUsername(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UserEntity userEntity = (UserEntity) session.getNamedQuery("UserEntity.findByUsername").setString("username", username).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userEntity;
    }
}
