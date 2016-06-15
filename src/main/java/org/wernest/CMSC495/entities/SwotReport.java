package org.wernest.CMSC495.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(name="SwotReport.findAllByUser", query="from SwotReport e where e.userEntity.id = :user_id")
})
public class SwotReport implements Serializable{

    public enum Strength{
        WEAK, STRONG, NULL
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private Date creationDate;

    private double iFactorScore;

    private double eFactorScore;

    @Column(columnDefinition = "enum('WEAK', 'STRONG', 'NULL')")
    @Enumerated(EnumType.STRING)
    private Strength internalStrength;

    @Column(columnDefinition = "enum('WEAK', 'STRONG', 'NULL')")
    @Enumerated(EnumType.STRING)
    private Strength externalStrength;

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public List<Strats> stratsList;

    @OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
    public List<Factors> factorsList;
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

    public double getiFactorScore() {
        return iFactorScore;
    }

    public void setiFactorScore(double iFactorScore) {
        this.iFactorScore = iFactorScore;
    }

    public double geteFactorScore() {
        return eFactorScore;
    }

    public void seteFactorScore(double eFactorScore) {
        this.eFactorScore = eFactorScore;
    }

    public Strength getInternalStrength() {
        return internalStrength;
    }

    public void setInternalStrength(Strength internalStrength) {
        this.internalStrength = internalStrength;
    }

    public Strength getExternalStrength() {
        return externalStrength;
    }

    public void setExternalStrength(Strength externalStrength) {
        this.externalStrength = externalStrength;
    }
}
