package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Created by k.nikitin on 22.09.2016.
 */
@Entity
@Table(name="EMPLOYEE")
@OnLineJoin(sqlExpression = " inner join person p on p.id = EMPLOYEE.person_id " +
        " inner join org on org.id = EMPLOYEE.org_id")
public class Employee {
    @Id
    @Column(name="ID", nullable=false, precision = 15, scale = 0)
    @OnLineColumnInfo
    private BigInteger id;

    @Column(name="PERSON_FIO", length = 255)
    @OnLineColumnInfo(columnName = "FIO", joinAlias = "P")
    private String person_fio;

    @Column(name="ORG_CAPTION", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "ORG")
    private String org_caption;

    @Column(name="ORG_DESCRIPTION", length = 500)
    @OnLineColumnInfo(columnName = "DESCRIPTION", joinAlias = "ORG")
    private  String org_description;

    @OneToOne
    @JoinColumn(name="APPOINTMENT_ID")
    @OnLineColumnInfo(columnName = "APPOINTMENT_ID")
    private Appointment appointment;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getPerson_fio() {
        return person_fio;
    }

    public void setPerson_fio(String person_fio) {
        this.person_fio = person_fio;
    }

    public String getOrg_caption() {
        return org_caption;
    }

    public void setOrg_caption(String org_caption) {
        this.org_caption = org_caption;
    }

    public String getOrg_description() {
        return org_description;
    }

    public void setOrg_description(String org_description) {
        this.org_description = org_description;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
