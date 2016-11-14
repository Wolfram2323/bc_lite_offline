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
        " inner join finsource fsr on fsr.id = KBKDETAIL.fsr_id" +
        " inner join actresultsauditdoc arad on arad.maindoc_id = ced.id" +
        " where arad.id = ?")

public class KBKDetail {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="FSRCAPTION", length = 500)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "FSR")
    private String fsr_caption;


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


    public Short getFsr_id() {
        return fsr_id;
    }

    public String getFsr_caption() {
        return fsr_caption;
    }

    public void setFsr_caption(String fsr_caption) {
        this.fsr_caption = fsr_caption;
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
