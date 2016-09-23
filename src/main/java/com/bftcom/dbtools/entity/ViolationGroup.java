package com.bftcom.dbtools.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="VIOLATIONGROUP")
public class ViolationGroup {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="VIOLATIONDESCRIPTION")
    private Clob violationDescription;

    @Column(name="VIOLATIONDESCRIPTION_SHORT")
    private Clob violationDescription_short;

    @Column(name="NONEEDCORRECTION", nullable = false)
    private Boolean noneedcorrection;

    @Column(name="NONEEDCORRECTIONBASIS")
    private Clob noneedcorrectionBasis;

    @Column(name="SUPDOCUMENTS_DETAIL", length = 1000)
    private String supdocuments_detaill;

    @OneToOne
    @JoinColumn(name="BROKENNPA_ID")    //todo bnpa ручное заполнение
    private BrokenNPA brokenNPA;

    @OneToOne
    @JoinColumn(name="VIOLATION_ID")
    private Violation violation;


    @OneToMany
    @JoinColumn(name="VIOLATIONGROUP_ID", foreignKey = @ForeignKey(name="FK_VGKBK_VG"))
    private List<ViolationGroupKBK> violationGroupKBK = new ArrayList<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Clob getViolationDescription() {
        return violationDescription;
    }

    public void setViolationDescription(Clob violationDescription) {
        this.violationDescription = violationDescription;
    }

    public Clob getViolationDescription_short() {
        return violationDescription_short;
    }

    public void setViolationDescription_short(Clob violationDescription_short) {
        this.violationDescription_short = violationDescription_short;
    }

    public Boolean getNoneedcorrection() {
        return noneedcorrection;
    }

    public void setNoneedcorrection(Boolean noneedcorrection) {
        this.noneedcorrection = noneedcorrection;
    }

    public Clob getNoneedcorrectionBasis() {
        return noneedcorrectionBasis;
    }

    public void setNoneedcorrectionBasis(Clob noneedcorrectionBasis) {
        this.noneedcorrectionBasis = noneedcorrectionBasis;
    }

    public String getSupdocuments_detaill() {
        return supdocuments_detaill;
    }

    public void setSupdocuments_detaill(String supdocuments_detaill) {
        this.supdocuments_detaill = supdocuments_detaill;
    }

    public BrokenNPA getBrokenNPA() {
        return brokenNPA;
    }

    public void setBrokenNPA(BrokenNPA brokenNPA) {
        this.brokenNPA = brokenNPA;
    }

    public Violation getViolation() {
        return violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }


    public List<ViolationGroupKBK> getViolationGroupKBK() {
        return violationGroupKBK;
    }

    public void setViolationGroupKBK(List<ViolationGroupKBK> violationGroupKBK) {
        this.violationGroupKBK = violationGroupKBK;
    }
}
