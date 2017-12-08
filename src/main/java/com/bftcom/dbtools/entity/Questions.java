package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name = "QUESTRESULT")
@OnLineJoin(sqlExpression = " inner join programsauditdocdetail pdet on pdet.id = QUESTRESULT.programsauditdocdetail_id" +
        " inner join TYPICALQUEST tq on tq.id = pdet.TYPICALQUEST_ID" +
        " inner join org orga on orga.id = QUESTRESULT.auditorg_id" +
        " where QUESTRESULT.actresultsauditdoc_id = ?")
public class Questions {
    @Id
    @Column(name = "ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name = "CAPTION", length = 800)  //todo связка с инспектором
    @OnLineColumnInfo(joinAlias = "TQ")
    private String caption;

    @Column(name = "RESULT", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo(synch = true)
    private String result;

    @Column(name = "TYPICALQUEST_LINE_NUMBER", length = 16)
    @OnLineColumnInfo(joinAlias = "PDET")
    private String typicalquest_line_number;

    @Column(name = "RESOLVED")
    @OnLineColumnInfo(synch = true)
    private Integer resolved;

    @Column(name="AUDITORG_CAPTION", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "ORGA")
    private String auditOrg_caption;

    @Column(name = "COUNTER_AUDIT")
    @OnLineColumnInfo
    private Integer counter_audit;

    @Column(name = "AUDITTHEME")
    @OnLineColumnInfo(synch = true)
    private String auditTheme;

    @Column(name = "AUDITFROM")
    @OnLineColumnInfo(synch = true)
    private Date auditFrom;

    @Column(name = "AUDITTO")
    @OnLineColumnInfo(synch = true)
    private Date auditTo;

    @OneToMany
    @JoinColumn(name = "QUESTIONS_ID", foreignKey = @ForeignKey(name = "FK_VG_QUESTIONS"))
    private List<ViolationGroup> violationGroup = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ACTRESULTSAUDITDOC_ID", foreignKey = @ForeignKey(name = "FK_QUESTIONS_ARAD"))
    @OnLineColumnInfo(columnName = "ACTRESULTSAUDITDOC_ID", synch = true)
    private ActResultsAuditDoc actResultsAuditDoc;

    @OneToMany
    @JoinColumn(name = "QUEST_ID", foreignKey = @ForeignKey(name = "FK_QUESTINSP_QUEST"))
    private Set<QuestInspectors> questInspectorsSet = new HashSet<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTypicalquest_line_number() {
        return typicalquest_line_number;
    }

    public void setTypicalquest_line_number(String typicalquest_line_number) {
        this.typicalquest_line_number = typicalquest_line_number;
    }


    public List<ViolationGroup> getViolationGroup() {
        return violationGroup;
    }

    public void setViolationGroup(List<ViolationGroup> violationGroup) {
        this.violationGroup = violationGroup;
    }

    public ActResultsAuditDoc getActResultsAuditDoc() {
        return actResultsAuditDoc;
    }

    public void setActResultsAuditDoc(ActResultsAuditDoc actResultsAuditDoc) {
        this.actResultsAuditDoc = actResultsAuditDoc;
    }

    public Integer getResolved() {
        return resolved;
    }

    public void setResolved(Integer resolved) {
        this.resolved = resolved;
    }

    public Set<QuestInspectors> getQuestInspectorsSet() {
        return questInspectorsSet;
    }

    public void setQuestInspectorsSet(Set<QuestInspectors> questInspectorsSet) {
        this.questInspectorsSet = questInspectorsSet;
    }

    public Integer getCounter_audit() {
        return counter_audit;
    }

    public void setCounter_audit(Integer counter_audit) {
        this.counter_audit = counter_audit;
    }

    public String getAuditTheme() {
        return auditTheme;
    }

    public void setAuditTheme(String auditTheme) {
        this.auditTheme = auditTheme;
    }

    public Date getAuditFrom() {
        return auditFrom;
    }

    public void setAuditFrom(Date auditFrom) {
        this.auditFrom = auditFrom;
    }

    public Date getAuditTo() {
        return auditTo;
    }

    public void setAuditTo(Date auditTo) {
        this.auditTo = auditTo;
    }

    public String getAuditOrg_caption() {
        return auditOrg_caption;
    }

    public void setAuditOrg_caption(String auditOrg_caption) {
        this.auditOrg_caption = auditOrg_caption;
    }
}
