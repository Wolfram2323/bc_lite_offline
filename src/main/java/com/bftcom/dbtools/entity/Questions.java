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
@Table(name="QUESTRESULT")
@OnLineJoin(sqlExpression = " inner join programsauditdocdetail pdet on pdet.id = QUESTRESULT.programsauditdocdetail_id" +
        " inner join TYPICALQUEST tq on tq.id = pdet.TYPICALQUEST_ID" +
        " where QUESTRESULT.actresultsauditdoc_id = ?")
public class Questions {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="CAPTION", length = 800)  //todo связка с инспектором
    @OnLineColumnInfo(joinAlias = "TQ")
    private String caption;

    @Column(name="RESULT")
    @OnLineColumnInfo
    private Clob result;

    @Column(name="TYPICALQUEST_LINE_NUMBER", length = 16)
    @OnLineColumnInfo(joinAlias = "PDET")
    private String typicalquest_line_number;


    @OneToMany
    @JoinColumn(name="QUESTIONS_ID", foreignKey = @ForeignKey(name="FK_VG_QUESTIONS"))
    private List<ViolationGroup> violationGroup = new ArrayList<>();

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

    public Clob getResult() {
        return result;
    }

    public void setResult(Clob result) {
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
}
