package org.wernest.CMSC495.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name="UserEntity.findByEmail", query="from UserEntity e where e.email = :email"),
        @NamedQuery(name="UserEntity.findByUsername", query="from UserEntity e where e.username = :username")
})
public class UserEntity implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name="firstName")
    private String companyName;

    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "userEntity")
    public List<SwotReport> swotReports;

    public UserEntity() {}

    public UserEntity(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public UserEntity(String email) {
        this.email = email;
    }

    public Integer getID() {

        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
