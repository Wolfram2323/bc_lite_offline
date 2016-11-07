package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 20.10.2016.
 */
@Entity
@Table(name = "ACTRESULTSAUDITINSPECTORS")
@OnLineJoin(sqlExpression = " where ACTRESULTSAUDITINSPECTORS.ACTRESULTSAUDITDOC_ID = ?")
public class ARADInspectors {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name="ACTRESULTSAUDITDOC_ID", foreignKey = @ForeignKey(name="FK_ARADINSP_ARAD"),nullable = false)
    @OnLineColumnInfo(columnName = "ACTRESULTSAUDITDOC_ID", synch = true)
    private ActResultsAuditDoc actResultsAuditDoc;

    @ManyToOne
    @JoinColumn(name="EMPLOYEEGROUP_ID", foreignKey = @ForeignKey(name="FK_ARADINSP_EMPG"), nullable = false)
    @OnLineColumnInfo(columnName = "EMPLOYEEGROUP_ID", synch = true)
    private EmployeeGroup employeeGroup;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public ActResultsAuditDoc getActResultsAuditDoc() {
        return actResultsAuditDoc;
    }

    public void setActResultsAuditDoc(ActResultsAuditDoc actResultsAuditDoc) {
        this.actResultsAuditDoc = actResultsAuditDoc;
    }

    public EmployeeGroup getEmployeeGroup() {
        return employeeGroup;
    }

    public void setEmployeeGroup(EmployeeGroup employeeGroup) {
        this.employeeGroup = employeeGroup;
    }
}
