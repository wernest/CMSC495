package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.entities.UserToken;

import java.util.Date;

/**
 * Created by will on 5/22/16.
 */
public class UserTokenDAO extends AbstractHibernateDAO<UserToken> {

    /**
     * Validates the session token and updates the initial date
     * @param token
     * @return
     */
    public boolean verifyToken(String token){
        boolean result = false;
        Session session = getCurrentSession();
        session.beginTransaction();
        UserToken userToken = (UserToken) this.getCurrentSession().getNamedQuery("UserToken.getByToken").setString("token", token).uniqueResult();
        if(userToken != null) {
            if (userToken.isValid()) {
                userToken.setDate(new Date(System.currentTimeMillis()));
                result = true;
                this.save(userToken);
            } else {
                this.delete(userToken);
            }
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public String getUsernameByToken(String token){
        String result = "";
        Session session = getCurrentSession();
        session.beginTransaction();
        UserToken userToken = (UserToken) this.getCurrentSession().getNamedQuery("UserToken.getByToken").setString("token", token).uniqueResult();
        if(userToken != null) {
            if (userToken.isValid()) {
                userToken.setDate(new Date(System.currentTimeMillis()));
                userToken.setNewExpiration();
                this.save(userToken);
                result = userToken.getUser().getUsername();
            } else {
                this.delete(userToken);
            }
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public UserToken getByUser(Long userId){
        Session session = getCurrentSession();
        session.beginTransaction();
        UserToken userToken = (UserToken) session.getNamedQuery("UserToken.getByUserId").setString("userId", String.valueOf(userId)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userToken;
    }
}
