package org.wernest.CMSC495.dao;

import org.hibernate.Session;
import org.wernest.CMSC495.entities.UserToken;

import java.util.Date;

/**
 * UserToken DAO
 * DAO for handling our Token objects for sessions
 */
public class UserTokenDAO extends AbstractHibernateDAO<UserToken> {

    /**
     * Validates the session token and updates the initial date
     * @param token Token
     * @return true if exists and is valid, false if not
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

    /**
     * Utility method to get the username based on supplied token
     * @param token Token
     * @return Username of the user this Token belongs to
     */
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

    /**
     * Gets the UserToken based on the userID supplied
     * @param userId Integer ID of User
     * @return UserToken for the user with userId
     */
    public UserToken getByUser(Integer userId){
        Session session = getCurrentSession();
        session.beginTransaction();
        UserToken userToken = (UserToken) session.getNamedQuery("UserToken.getByUserId").setString("userId", String.valueOf(userId)).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return userToken;
    }
}
