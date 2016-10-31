package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="EMPLOYEEGROUP")
@OnLineJoin(sqlExpression = " inner join controleventdoc ced on ced.document_id = EMPLOYEEGROUP.link_document_id" +
        " inner join actresultsauditdoc arad on arad.maindoc_id = ced.id" +
        " where arad.id = ?")
public class EmployeeGroup {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="INSP_STATUS", length = 2000)
    @OnLineColumnInfo
    private String insp_status;

    @OneToOne
    @JoinColumn(name="EMPLOYEE_ID")
    @OnLineColumnInfo(columnName = "EMPLOYEE_ID")
    private Employee employee;

    @OneToMany
    @JoinColumn(name="EMPLOYEEGROUP_ID", foreignKey = @ForeignKey(name="FK_ARADINSP_EMPG"))
    private List<ARADInspectors> aradInspectorses = new ArrayList<>();



    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getInsp_status() {
        return insp_status;
    }

    public void setInsp_status(String insp_status) {
        this.insp_status = insp_status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<ARADInspectors> getAradInspectorses() {
        return aradInspectorses;
    }

    public void setAradInspectorses(List<ARADInspectors> aradInspectorses) {
        this.aradInspectorses = aradInspectorses;
    }
}
