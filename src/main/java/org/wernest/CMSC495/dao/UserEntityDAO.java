package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.authentication.SessionIdentifierGenerator;
import org.wernest.CMSC495.entities.UserEntity;


/**
 * UserEntityDao for getting UserEntity objects from the DB
 */
public class UserEntityDAO extends AbstractHibernateDAO<UserEntity>{

    /**
     * Returns a UserEntity Object with a matching Email address
     * @param email email address of user
     * @return UserEntity if it matches, null if not
     */
    public UserEntity getByEmail(String email){
        return (UserEntity) this.getCurrentSession().getNamedQuery("UserEntity.findByEmail").setString("email", email).uniqueResult();
    }

    /**
     * Returns a UserEntity object with a matching username
     * @param username username of the user
     * @return UserEntity if it matches, null if not
     */
    public UserEntity getByUsername(String username){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UserEntity userEntity = (UserEntity) session.getNamedQuery("UserEntity.findByUsername").setString("username", username).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userEntity;
    }
}
