package org.wernest.CMSC495.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Object to hold Strategies for SWOT Reports
 */
@Entity
public class Strats implements Serializable{

    /**
     * Enumerated list of possible types
     * SO - Strength Opportunities
     * WO - Weakness Opportunities
     * ST - Strength Threats
     * WT - Weakness Threats
     */
    public enum StratType{
        SO, WO, ST, WT
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @Column(columnDefinition = "enum('SO', 'WO', 'ST', 'WT')")
    @Enumerated(EnumType.STRING)
    private StratType stratType;

    private String description;

    /**
     * Default c'tor
     */
    public Strats() {
    }

    /**
     * Standard Generated Getters/Setters
     */

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public StratType getStratType() {
        return stratType;
    }

    public void setStratType(StratType stratType) {
        this.stratType = stratType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


