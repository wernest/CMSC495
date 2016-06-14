package org.wernest.CMSC495.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Strats implements Serializable{

    public enum StratType{
        SO, WO, ST, WT
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer ID;

    @ManyToOne
    @JoinColumn(name = "swot_report_id", nullable = false)
    private SwotReport swotReport;

    @Column(columnDefinition = "enum('SO', 'WO', 'ST', 'WT')")
    @Enumerated(EnumType.STRING)
    private StratType stratType;

    private String description;

    public Strats() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public SwotReport getSwotReport() {
        return swotReport;
    }

    public void setSwotReport(SwotReport swotReport) {
        this.swotReport = swotReport;
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


