package com.bftcom.dbtools.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="KBKDETAIL")
public class KBKDetail {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0) //todo свзяи с чем либо: акт, вопрос, инспектор
    private BigInteger id;

    @Column(name="BUDGET", length = 500)
    private String budget;

    @Column(name="FINYEAR", precision = 4, scale = 0)
    private  BigInteger finyear;

    @Column(name="FSR_ID")
    private Short fsr_id;

    @Column(name="FINANCEAMT", precision = 15, scale = 2)
    private BigDecimal financeamt;

    @Column(name="AUDITEDAMOUNT", precision = 15, scale = 2)
    private BigDecimal auditedamount;

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

    public BigDecimal getAuditedamount() {
        return auditedamount;
    }

    public void setAuditedamount(BigDecimal auditedamount) {
        this.auditedamount = auditedamount;
    }
}
