package org.wernest.CMSC495.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Factors implements Serializable{

    public enum FactorType{
        S, W, O, T
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('S', 'W', 'O', 'T')")
    private FactorType factorType;

    private String description;
    private double weight;
    private int rating;
    private double weightedScore;


    public Factors() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getWeightedScore() {
        return weightedScore;
    }

    public void setWeightedScore(double weightedScore) {
        this.weightedScore = weightedScore;
    }

    public FactorType getFactorType() {
        return factorType;
    }

    public void setFactorType(FactorType factorType) {
        this.factorType = factorType;
    }
}
