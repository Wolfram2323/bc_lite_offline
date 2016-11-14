package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Date;
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
    @GeneratedValue
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="VIOLATIONDESCRIPTION", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo(synch = true)
    private String violationDescription;

    @Column(name="VIOLATIONDESCRIPTION_SHORT", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo(synch = true)
    private String violationDescription_short;

    @Column(name="NONEEDCORRECTION", nullable = false)
    @OnLineColumnInfo(synch = true)
    private Boolean noneedcorrection;

    @Column(name="NONEEDCORRECTIONBASIS", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo(synch = true)
    private String noneedcorrectionBasis;

    @Column(name="SUPDOCUMENTS_DETAIL", length = 1000)
    @OnLineColumnInfo(synch = true)
    private String supdocuments_detaill;

    @Column(name = "ACTPAGENUMBER")
    @OnLineColumnInfo(synch = true)
    private String actpagenumber;

    @Column(name = "HAVEDISAGREEMENTS")
    @OnLineColumnInfo(synch = true)
    private Boolean havedisagreements;

    @Column(name = "DISAGREEMENTSACCEPTED")
    @OnLineColumnInfo(synch = true)
    private Boolean disagreementaccepted;

    @OneToOne
    @JoinColumn(name="BROKENNPA_ID")
    private BrokenNPA brokenNPA;

    @Column(name="BNPA_CAPTION" , length = 500)
    @OnLineColumnInfo(synch = true)
    private Date bnpa_caption;

    @Column(name="BNPA_DATE_BNPA", length = 500)
    @OnLineColumnInfo(synch = true)
    private Date bnpa_date;

    @Column(name="BNPA_LOOK", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_look;

    @Column(name="BNPA_NUM", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_num;

    @Column(name="BNPA_CHAPTER", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_chapter;

    @Column(name="BNPA_CLAUSE", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_clause;

    @Column(name="BNPA_PARAGRAPH", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_paragraph;

    @Column(name="BNPA_SUBPARAGRAPH", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_subparagraph;

    @Column(name="BNPA_RUBRIC", length = 500)
    @OnLineColumnInfo(synch = true)
    private String bnpa_rubric;

    @OneToOne
    @JoinColumn(name="VIOLATION_ID")
    @OnLineColumnInfo(columnName = "VIOLATION_ID", synch = true)
    private Violation violation;

    @OneToOne
    @JoinColumn(name="VIOLATIONTYPE_ID")
    @OnLineColumnInfo(columnName = "VIOLATIONTYPE_ID", synch = true)
    private ViolationType violationType;


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

    public String getActpagenumber() {
        return actpagenumber;
    }

    public void setActpagenumber(String actpagenumber) {
        this.actpagenumber = actpagenumber;
    }

    public Boolean getHavedisagreements() {
        return havedisagreements;
    }

    public void setHavedisagreements(Boolean havedisagreements) {
        this.havedisagreements = havedisagreements;
    }

    public Boolean getDisagreementaccepted() {
        return disagreementaccepted;
    }

    public void setDisagreementaccepted(Boolean disagreementaccepted) {
        this.disagreementaccepted = disagreementaccepted;
    }

    public Date getBnpa_caption() {
        return bnpa_caption;
    }

    public void setBnpa_caption(Date bnpa_caption) {
        this.bnpa_caption = bnpa_caption;
    }

    public Date getBnpa_date() {
        return bnpa_date;
    }

    public void setBnpa_date(Date bnpa_date) {
        this.bnpa_date = bnpa_date;
    }

    public String getBnpa_look() {
        return bnpa_look;
    }

    public void setBnpa_look(String bnpa_look) {
        this.bnpa_look = bnpa_look;
    }

    public String getBnpa_num() {
        return bnpa_num;
    }

    public void setBnpa_num(String bnpa_num) {
        this.bnpa_num = bnpa_num;
    }

    public String getBnpa_chapter() {
        return bnpa_chapter;
    }

    public void setBnpa_chapter(String bnpa_chapter) {
        this.bnpa_chapter = bnpa_chapter;
    }

    public String getBnpa_clause() {
        return bnpa_clause;
    }

    public void setBnpa_clause(String bnpa_clause) {
        this.bnpa_clause = bnpa_clause;
    }

    public String getBnpa_paragraph() {
        return bnpa_paragraph;
    }

    public void setBnpa_paragraph(String bnpa_paragraph) {
        this.bnpa_paragraph = bnpa_paragraph;
    }

    public String getBnpa_subparagraph() {
        return bnpa_subparagraph;
    }

    public void setBnpa_subparagraph(String bnpa_subparagraph) {
        this.bnpa_subparagraph = bnpa_subparagraph;
    }

    public String getBnpa_rubric() {
        return bnpa_rubric;
    }

    public void setBnpa_rubric(String bnpa_rubric) {
        this.bnpa_rubric = bnpa_rubric;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }
}
