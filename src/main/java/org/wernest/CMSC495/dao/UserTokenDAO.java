package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.wernest.CMSC495.entities.UserEntity;
import org.wernest.CMSC495.entities.UserToken;

/**
 * Created by will on 5/22/16.
 */
public class UserTokenDAO extends AbstractHibernateDAO<UserToken> {

    public UserToken getByToken(String token){
        return (UserToken) this.getCurrentSession().getNamedQuery("UserToken.getByToken").setString("token", token).uniqueResult();
    }

    public UserToken getByUser(Long userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        UserToken userToken = (UserToken) session.getNamedQuery("UserToken.getByUserId").setString("userId", String.valueOf(userId)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userToken;
    }
}
