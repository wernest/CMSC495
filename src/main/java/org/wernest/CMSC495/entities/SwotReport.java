package org.wernest.CMSC495.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name="SwotReport.findAllByUser", query="from SwotReport e where e.userEntity.id = :user_id")
})
public class SwotReport implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private Date creationDate;

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public Set<Strats> stratsList;

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public Set<Factors> factorsList;
    public SwotReport() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
