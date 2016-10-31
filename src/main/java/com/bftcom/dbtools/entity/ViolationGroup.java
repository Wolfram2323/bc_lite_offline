package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

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
@OnLineJoin(sqlExpression = " inner join actresultsauditdoc arad on arad.document_id = VIOLATIONGROUP.link_document_id" +
        " where arad.id = ?")
public class ViolationGroup {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="VIOLATIONDESCRIPTION", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo
    private String violationDescription;

    @Column(name="VIOLATIONDESCRIPTION_SHORT", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo
    private String violationDescription_short;

    @Column(name="NONEEDCORRECTION", nullable = false)
    @OnLineColumnInfo
    private Boolean noneedcorrection;

    @Column(name="NONEEDCORRECTIONBASIS", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo
    private String noneedcorrectionBasis;

    @Column(name="SUPDOCUMENTS_DETAIL", length = 1000)
    @OnLineColumnInfo
    private String supdocuments_detaill;

    @OneToOne
    @JoinColumn(name="BROKENNPA_ID")
    @OnLineColumnInfo(columnName = "BROKEN_NPA")
    private BrokenNPA brokenNPA;

    @OneToOne
    @JoinColumn(name="VIOLATION_ID")
    @OnLineColumnInfo(columnName = "VIOLATION_ID")
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

    public String getViolationDescription() {
        return violationDescription;
    }

    public void setViolationDescription(String violationDescription) {
        this.violationDescription = violationDescription;
    }

    public String getViolationDescription_short() {
        return violationDescription_short;
    }

    public void setViolationDescription_short(String violationDescription_short) {
        this.violationDescription_short = violationDescription_short;
    }

    public Boolean getNoneedcorrection() {
        return noneedcorrection;
    }

    public void setNoneedcorrection(Boolean noneedcorrection) {
        this.noneedcorrection = noneedcorrection;
    }

    public String getNoneedcorrectionBasis() {
        return noneedcorrectionBasis;
    }

    public void setNoneedcorrectionBasis(String noneedcorrectionBasis) {
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
