package com.bftcom.dbtools.entity;

import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 23.09.2016.
 */
@Entity
@Table(name="ACTRESULTSAUDITDOC")
@OnLineJoin(sqlExpression = " inner join controleventdoc ced on ced.id = ACTRESULTSAUDITDOC.maindoc_id" +
        " inner join org orga on ced.auditorg_id = orga.id" +
        " inner join org orgc on ced.controlorg_id = orgc.id" +
        " inner join controlform cf on ced.controlform_id = cf.id" +
        " inner join territory ter on ced.territory_id = ter.id" +
        " inner join orderauditappointmentdoc oaad on ced.id = oaad.maindoc_id" +
        " inner join employee emphead on emphead.id = ced.head_id" +
        " inner join employee empdeputy on empdeputy.id = ced.deputy_head_id" +
        " inner join person phead on phead.id = emphead.person_id" +
        " inner join person pdeputy on pdeputy.id = empdeputy.person_id" +
        " inner join appointment apphead on apphead.id = emphead.appointment_id" +
        " inner join appointment appdeputy on appdeputy.id = empdeputy.appointment_id" +
        " where ACTRESULTSAUDITDOC.ID = ?")
public class ActResultsAuditDoc {
    @Id
    @Column(name="ID", nullable = false, precision = 15, scale = 0)
    private BigInteger id;

    @Column(name="CASENUMBER", length = 200)
    @OnLineColumnInfo
    private String casenumber;

    @Column(name="APPROVEDATE")
    @OnLineColumnInfo
    private Date approvedate;

    @Column(name="AUDITTHEME", length = 1000)
    @OnLineColumnInfo(joinAlias = "CED")
    private String auditTheme;

    @Column(name="AUDITORG_CAPTION", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "ORGA")
    private String auditOrg_caption;

    @Column(name="AUDITORG_DESCRIPTION", length = 2000)
    @OnLineColumnInfo(columnName = "DESCRIPTION", joinAlias = "ORGA")
    private String auditOrg_description;

    @Column(name="CONTROLFORM",length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "CF")
    private String controlform;


    @Column(name="CONTROLORG_CAPTION", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "ORGC")
    private String controlOrg_caption;

    @Column(name="CONTROLORG_DESCRIPTION", length = 2000)
    @OnLineColumnInfo(columnName = "DESCRIPTION", joinAlias = "ORGC")
    private String controlOrg_description;

    @Column(name="TERRITORY", length = 100)
    @OnLineColumnInfo(columnName = "CAPTION",joinAlias = "TER")
    private String territory;

    @Column(name="AUDITFROM")
    @OnLineColumnInfo(joinAlias = "CED")
    private Date auditFrom;

    @Column(name="AUDITTO")
    @OnLineColumnInfo(joinAlias = "CED")
    private Date auditTo;

    @Column(name="ACTUALEXECFROM")
    @OnLineColumnInfo(joinAlias = "CED")
    private Date actualExecFrom;

    @Column(name="ACTUALEXECTO")
    @OnLineColumnInfo(joinAlias = "CED")
    private Date actualExecTo;

    @Column(name="OBJECTINFO", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo
    private Clob objectInfo;

    @Column(name="OBSTACLE", columnDefinition = "clob")
    @Lob
    @OnLineColumnInfo
    private Clob obstacle;

    @Column(name="WRITEPLACE", length = 100)
    @OnLineColumnInfo
    private String writePlace;

    @Column(name="AUDITBASIS", length = 4000)
    @OnLineColumnInfo(joinAlias = "CED")
    private String auditBasis;

    @Column(name="AUDITBASIS_TYPE", length = 100)
    @OnLineColumnInfo(joinAlias = "CED")
    private String auditBasis_type;

    @Column(name="AUDITBASIS_NUMBER", length = 20)
    @OnLineColumnInfo(joinAlias = "CED")
    private  String auditBasis_number;

    @Column(name="AUDITBASIS_DATE")
    @OnLineColumnInfo(joinAlias = "CED")
    private Date aiditBasis_date;

    @Column(name="INSP_HEAD_FIO", length = 255)
    @OnLineColumnInfo(columnName = "FIO", joinAlias = "PHEAD")
    private String insp_head_fio;

    @Column(name="INSP_HEAD_APPOINTMENT", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "APPHEAD")
    private String insp_head_appointment;

    @Column(name="DEPUTY_HEAD_FIO", length = 255)
    @OnLineColumnInfo(columnName = "FIO", joinAlias = "PDEPUTY")
    private String deputy_head_fio;

    @Column(name="DEPUTY_HEAD_APPOINTMENT", length = 255)
    @OnLineColumnInfo(columnName = "CAPTION", joinAlias = "APPDEPUTY")
    private String deputy_head_appointment;

    @Column(name="AUDOTORG_HEAD_FIO", length = 255)
    @OnLineColumnInfo(columnName = "HEADFIO")
    private String auditorg_head_fio;

    @Column(name="AUDOTORG_HEAD_APP", length = 255)
    @OnLineColumnInfo(columnName = "HEADAPPOINTMENTCAPTION")
    private String auditorg_head_app;

    @Column(name="ACCOUNTANTFIO", length = 255)
    @OnLineColumnInfo
    private String accountantfio;

    @Column(name="ACCOUNTANTAPP", length = 255)
    @OnLineColumnInfo(columnName = "ACCAPPOINTMENTCAPTION")
    private String accountantapp;

    @Column(name="ORDERAUDIT_NUMBER", length = 80)
    @OnLineColumnInfo(columnName = "DOC_NUMBER", joinAlias = "OAAD")
    private String orderaudit_number;

    @Column(name="ORDERAUDIT_APPROVEDATE")
    @OnLineColumnInfo(columnName = "APPROVEDATE", joinAlias = "OAAD")
    private Date orderaudit_approvedate;

    @Column(name="AUDITSUBJECT", length = 1000)
    @OnLineColumnInfo(joinAlias = "CED")
    private String auditSubject;

//    @Column(name="DOC_STATUS", nullable = false) //не будем ставит default значение - пока..
//    private Short doc_status;

    @OneToMany
    @JoinColumn(name="ARAD_ID", foreignKey = @ForeignKey(name="FK_QUESTIONS_ARAD"))
    private List<Questions> questions = new LinkedList<>();

    @OneToMany
    @JoinColumn(name="ARAD_ID", foreignKey = @ForeignKey(name="FK_EMPG_ARAD_ID"))
    private List<EmployeeGroup> employeeGroup = new ArrayList<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

//    public Short getDoc_status() {
//        return doc_status;
//    }
//
//    public void setDoc_status(Short doc_status) {
//        this.doc_status = doc_status;
//    }

    public String getCasenumber() {
        return casenumber;
    }

    public void setCasenumber(String casenumber) {
        this.casenumber = casenumber;
    }

    public Date getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(Date approvedate) {
        this.approvedate = approvedate;
    }

    public String getAuditOrg_caption() {
        return auditOrg_caption;
    }

    public void setAuditOrg_caption(String auditOrg_caption) {
        this.auditOrg_caption = auditOrg_caption;
    }

    public String getAuditOrg_description() {
        return auditOrg_description;
    }

    public void setAuditOrg_description(String auditOrg_description) {
        this.auditOrg_description = auditOrg_description;
    }

    public String getControlform() {
        return controlform;
    }

    public void setControlform(String controlform) {
        this.controlform = controlform;
    }

    public String getAuditTheme() {
        return auditTheme;
    }

    public void setAuditTheme(String auditTheme) {
        this.auditTheme = auditTheme;
    }

    public String getControlOrg_caption() {
        return controlOrg_caption;
    }

    public void setControlOrg_caption(String controlOrg_caption) {
        this.controlOrg_caption = controlOrg_caption;
    }

    public String getControlOrg_description() {
        return controlOrg_description;
    }

    public void setControlOrg_description(String controlOrg_description) {
        this.controlOrg_description = controlOrg_description;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public Date getAuditFrom() {
        return auditFrom;
    }

    public void setAuditFrom(Date auditFrom) {
        this.auditFrom = auditFrom;
    }

    public Date getAuditTo() {
        return auditTo;
    }

    public void setAuditTo(Date auditTo) {
        this.auditTo = auditTo;
    }

    public Date getActualExecFrom() {
        return actualExecFrom;
    }

    public void setActualExecFrom(Date actualExecFrom) {
        this.actualExecFrom = actualExecFrom;
    }

    public Date getActualExecTo() {
        return actualExecTo;
    }

    public void setActualExecTo(Date actualExecTo) {
        this.actualExecTo = actualExecTo;
    }

    public Clob getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(Clob objectInfo) {
        this.objectInfo = objectInfo;
    }

    public Clob getObstacle() {
        return obstacle;
    }

    public void setObstacle(Clob obstacle) {
        this.obstacle = obstacle;
    }

    public String getWritePlace() {
        return writePlace;
    }

    public void setWritePlace(String writePlace) {
        this.writePlace = writePlace;
    }

    public String getAuditBasis() {
        return auditBasis;
    }

    public void setAuditBasis(String auditBasis) {
        this.auditBasis = auditBasis;
    }

    public String getAuditBasis_type() {
        return auditBasis_type;
    }

    public void setAuditBasis_type(String auditBasis_type) {
        this.auditBasis_type = auditBasis_type;
    }

    public String getAuditBasis_number() {
        return auditBasis_number;
    }

    public void setAuditBasis_number(String auditBasis_number) {
        this.auditBasis_number = auditBasis_number;
    }

    public Date getAiditBasis_date() {
        return aiditBasis_date;
    }

    public void setAiditBasis_date(Date aiditBasis_date) {
        this.aiditBasis_date = aiditBasis_date;
    }

    public String getInsp_head_fio() {
        return insp_head_fio;
    }

    public void setInsp_head_fio(String insp_head_fio) {
        this.insp_head_fio = insp_head_fio;
    }

    public String getInsp_head_appointment() {
        return insp_head_appointment;
    }

    public void setInsp_head_appointment(String insp_head_appointment) {
        this.insp_head_appointment = insp_head_appointment;
    }

    public String getDeputy_head_fio() {
        return deputy_head_fio;
    }

    public void setDeputy_head_fio(String deputy_head_fio) {
        this.deputy_head_fio = deputy_head_fio;
    }

    public String getDeputy_head_appointment() {
        return deputy_head_appointment;
    }

    public void setDeputy_head_appointment(String deputy_head_appointment) {
        this.deputy_head_appointment = deputy_head_appointment;
    }

    public String getAuditorg_head_fio() {
        return auditorg_head_fio;
    }

    public void setAuditorg_head_fio(String auditorg_head_fio) {
        this.auditorg_head_fio = auditorg_head_fio;
    }

    public String getAuditorg_head_app() {
        return auditorg_head_app;
    }

    public void setAuditorg_head_app(String auditorg_head_app) {
        this.auditorg_head_app = auditorg_head_app;
    }

    public String getAccountantfio() {
        return accountantfio;
    }

    public void setAccountantfio(String accountantfio) {
        this.accountantfio = accountantfio;
    }

    public String getAccountantapp() {
        return accountantapp;
    }

    public void setAccountantapp(String accountantapp) {
        this.accountantapp = accountantapp;
    }

    public String getOrderaudit_number() {
        return orderaudit_number;
    }

    public void setOrderaudit_number(String orderaudit_number) {
        this.orderaudit_number = orderaudit_number;
    }

    public Date getOrderaudit_approvedate() {
        return orderaudit_approvedate;
    }

    public void setOrderaudit_approvedate(Date orderaudit_approvedate) {
        this.orderaudit_approvedate = orderaudit_approvedate;
    }

    public String getAuditSubject() {
        return auditSubject;
    }

    public void setAuditSubject(String auditSubject) {
        this.auditSubject = auditSubject;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public List<EmployeeGroup> getEmployeeGroup() {
        return employeeGroup;
    }

    public void setEmployeeGroup(List<EmployeeGroup> employeeGroup) {
        this.employeeGroup = employeeGroup;
    }
}
