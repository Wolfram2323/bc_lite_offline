package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k.nikitin on 20.10.2016.
 */
@Entity
@Table(name = "KBKDETAIL")
@OnLineJoin(sqlExpression = " inner join controleventdoc ced on ced.document_id = KBKDETAIL.link_document_id" +
        " inner join budget budg on budg.id = KBKDETAIL.budget_id" +
        " inner join actresultsauditdoc arad on arad.maindoc_id = ced.id" +
        " where arad.id = ?")

public class KBKDetail {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="BUDGET", length = 500)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "BUDG")
    private String budget;

    @Column(name="FINYEAR", precision = 4, scale = 0)
    @OnLineColumnInfo(columnName = "FINYEAR", joinAlias = "BUDG")
    private  BigInteger finyear;

    @Column(name="FSR_ID")
    @OnLineColumnInfo
    private Short fsr_id;

    @Column(name="FINANCEAMT", precision = 15, scale = 2)
    @OnLineColumnInfo
    private BigDecimal financeamt;

    @OneToMany
    @JoinColumn(name="KBKDETAIL_ID", foreignKey = @ForeignKey(name = "FK_ARADKBK_KBK"))
    private List<ARADkbk> araDkbks = new ArrayList<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public BigInteger getFinyear() {
        return finyear;
    }

    public void setFinyear(BigInteger finyear) {
        this.finyear = finyear;
    }

    public Short getFsr_id() {
        return fsr_id;
    }

    public void setFsr_id(Short fsr_id) {
        this.fsr_id = fsr_id;
    }

    public BigDecimal getFinanceamt() {
        return financeamt;
    }

    public void setFinanceamt(BigDecimal financeamt) {
        this.financeamt = financeamt;
    }

    public List<ARADkbk> getAraDkbks() {
        return araDkbks;
    }

    public void setAraDkbks(List<ARADkbk> araDkbks) {
        this.araDkbks = araDkbks;
    }
}
