package org.wernest.CMSC495.entities;



import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by will on 5/19/16.
 */
@Entity
@Table(name = "rows")
public class SampleRow implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonProperty
    public Integer id;

    @JsonProperty
    public String name;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="owner_id")
    @JsonProperty
    public UserEntity owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }
}
