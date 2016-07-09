package org.wernest.CMSC495.entities;


/**
 * User credentials object for simplifying username/password logins
 */
public class UserCredentials {

    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
