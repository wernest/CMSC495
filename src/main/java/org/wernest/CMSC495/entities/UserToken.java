package org.wernest.CMSC495.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Object for managing our user sessions
 */
@Entity
@NamedQueries({
        @NamedQuery(name="UserToken.getByToken", query="from UserToken e where e.token = :token"),
        @NamedQuery(name="UserToken.getByUserId", query="from UserToken e where e.user.ID = :userId")
})
public class UserToken implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private String token;
    private Date date;
    private Date expires;

    /**
     * Default c'tor
     */
    public UserToken(){
    }

    /**
     * Used to generated a new token easily
     * @param user USer this token belongs to
     * @param token String token
     * @param dateMills Current date/time in millis
     */
    public UserToken(UserEntity user, String token, Long dateMills) {
        this.user = user;
        this.token = token;
        this.date = new Date(dateMills);
        this.expires = new Date( dateMills +60*60*1000);
    }

    public boolean isValid(){
        return this.expires.after(new Date(System.currentTimeMillis()));
    }


    public void setNewExpiration(){
        this.expires = new Date(System.currentTimeMillis() + 60*60*1000);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.setNewExpiration();
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
