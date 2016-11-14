package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="VIOLATIONGROUPKBK")
@OnLineJoin(sqlExpression = " inner join violationgroup vg on vg.id = VIOLATIONGROUPKBK.violationgroup_id" +
        " inner join actresultsauditdoc arad on arad.document_id = vg.link_document_id" +
        " where arad.id = ?")

public class ViolationGroupKBK {
    @Id
    @GeneratedValue
    @Column(name="ID", nullable=false, precision = 15, scale=0)
    private BigInteger id;

    @Column(name="AMOUNT")
    @OnLineColumnInfo(synch = true)
    private BigDecimal amount;

    @Column(name="COMPENSATEDTOAUDITORGCASH")
    @OnLineColumnInfo(synch = true)
    private BigDecimal compensatedToAuditOrgCash;

    @Column(name="COMPENSATEDTOAUDITORGACCOUNT")
    @OnLineColumnInfo(synch = true)
    private BigDecimal compensatedToAuditOrgAccount;

    @Column(name="COMPENSATEDTOGRBSACCOUNT")
    @OnLineColumnInfo(synch = true)
    private BigDecimal compensatedToGRBSAccount;

    @Column(name="COMPENSATEDTOBUDGET")
    @OnLineColumnInfo(synch = true)
    private BigDecimal  compensatedToBudget;

    @OneToOne
    @JoinColumn(name="KBKDETAIL_ID")
    @OnLineColumnInfo(columnName = "KBKDETAIL_ID", synch = true)
    private KBKDetail  kbkDetail;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCompensatedToAuditOrgCash() {
        return compensatedToAuditOrgCash;
    }

    public void setCompensatedToAuditOrgCash(BigDecimal compensatedToAuditOrgCash) {
        this.compensatedToAuditOrgCash = compensatedToAuditOrgCash;
    }

    public BigDecimal getCompensatedToAuditOrgAccount() {
        return compensatedToAuditOrgAccount;
    }

    public void setCompensatedToAuditOrgAccount(BigDecimal compensatedToAuditOrgAccount) {
        this.compensatedToAuditOrgAccount = compensatedToAuditOrgAccount;
    }

    public BigDecimal getCompensatedToGRBSAccount() {
        return compensatedToGRBSAccount;
    }

    public void setCompensatedToGRBSAccount(BigDecimal compensatedToGRBSAccount) {
        this.compensatedToGRBSAccount = compensatedToGRBSAccount;
    }

    public BigDecimal getCompensatedToBudget() {
        return compensatedToBudget;
    }

    public void setCompensatedToBudget(BigDecimal compensatedToBudget) {
        this.compensatedToBudget = compensatedToBudget;
    }

    public KBKDetail getKbkDetail() {
        return kbkDetail;
    }

    public void setKbkDetail(KBKDetail kbkDetail) {
        this.kbkDetail = kbkDetail;
    }

}
