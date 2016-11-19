package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 18.11.2016.
 */
@Entity
@Table(name="QUESTIONSAUDITINSPECTORS")
@OnLineJoin(sqlExpression = " inner join QUESTRESULT qr  on QUESTIONSAUDITINSPECTORS.PROGRAMSAUDITDOCDETAIL_ID = qr.PROGRAMSAUDITDOCDETAIL_ID" +
        " where qr.actresultsauditdoc_id = ?")
public class QuestInspectors {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name="EMPLOYEEGROUP_ID", foreignKey = @ForeignKey(name="FK_QUESTINSP_EMPG"), nullable = false)
    @OnLineColumnInfo(columnName = "EMPLOYEEGROUP_ID", synch = true)
    private EmployeeGroup employeeGroup;

    @ManyToOne
    @JoinColumn(name="QUEST_ID", foreignKey = @ForeignKey(name = "FK_QUESTINSP_QUEST"), nullable = false)
    @OnLineColumnInfo(columnName = "ID", joinAlias = "QR")
    private Questions  question;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public EmployeeGroup getEmployeeGroup() {
        return employeeGroup;
    }

    public void setEmployeeGroup(EmployeeGroup employeeGroup) {
        this.employeeGroup = employeeGroup;
    }

    public Questions getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }
}
