package org.wernest.CMSC495;



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
    public Integer id;

    public String name;
}
