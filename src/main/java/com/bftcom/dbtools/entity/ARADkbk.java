package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;
import javafx.beans.property.SimpleLongProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="ACTRESULTSAUDITDOCKBK")
@OnLineJoin(sqlExpression = " where ACTRESULTSAUDITDOCKBK.ARAUDITDOC_ID = ?")
public class ARADkbk {
    @Id
    @GeneratedValue
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="AUDITEDAMOUNT", precision = 15, scale = 2)
    @OnLineColumnInfo(synch = true)
    private BigDecimal auditedamount;

    @ManyToOne
    @JoinColumn(name="ARAUDITDOC_ID", foreignKey = @ForeignKey(name="FK_ARADKBK_ARAD"), nullable = false, updatable = false, insertable = false)
    @OnLineColumnInfo(columnName = "ARAUDITDOC_ID",synch = true)
    private ActResultsAuditDoc actResultsAuditDoc;

    @ManyToOne
    @JoinColumn(name="KBKDETAIL_ID", foreignKey = @ForeignKey(name = "FK_ARADKBK_KBK"),nullable = false)
    @OnLineColumnInfo(columnName = "KBKDETAIL_ID", synch = true)
    private KBKDetail kbkDetail;

    public ActResultsAuditDoc getActResultsAuditDoc() {
        return actResultsAuditDoc;
    }

    public void setActResultsAuditDoc(ActResultsAuditDoc actResultsAuditDoc) {
        this.actResultsAuditDoc = actResultsAuditDoc;
    }


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigDecimal getAuditedamount() {
        return auditedamount;
    }

    public void setAuditedamount(BigDecimal auditedamount) {
        this.auditedamount = auditedamount;
    }

    public KBKDetail getKbkDetail() {
        return kbkDetail;
    }

    public void setKbkDetail(KBKDetail kbkDetail) {
        this.kbkDetail = kbkDetail;
    }

}
