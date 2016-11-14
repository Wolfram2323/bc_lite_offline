package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.ARADInspectors;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public class AradInspectorsTVbject {
    private final SimpleLongProperty id;
    private final SimpleLongProperty employeegroup_id;
    private final SimpleStringProperty person_fio;
    private final SimpleStringProperty app_caption;
    private final SimpleStringProperty org_caption;
    private final SimpleStringProperty insp_status;


    public AradInspectorsTVbject(ARADInspectors aradInspectors){
        this.id = new SimpleLongProperty(aradInspectors.getId().longValue());
        this.employeegroup_id = new SimpleLongProperty(aradInspectors.getEmployeeGroup().getId().longValue());
        this.person_fio = new SimpleStringProperty(aradInspectors.getEmployeeGroup().getEmployee().getPerson_fio());
        this.app_caption = new SimpleStringProperty(aradInspectors.getEmployeeGroup().getEmployee().getAppointment().getCaption());
        this.org_caption = new SimpleStringProperty(aradInspectors.getEmployeeGroup().getEmployee().getOrg_caption());
        this.insp_status = new SimpleStringProperty(aradInspectors.getEmployeeGroup().getInsp_status());
    }

    public AradInspectorsTVbject(EmployeeGroupTVObject empg){
        this.id = new SimpleLongProperty();
        this.employeegroup_id = empg.idProperty();
        this.person_fio = empg.person_fioProperty();
        this.app_caption = empg.app_captionProperty();
        this.org_caption = empg.org_captionProperty();
        this.insp_status = empg.insp_statusProperty();
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getEmployeegroup_id() {
        return employeegroup_id.get();
    }

    public SimpleLongProperty employeegroup_idProperty() {
        return employeegroup_id;
    }

    public void setEmployeegroup_id(long employeegroup_id) {
        this.employeegroup_id.set(employeegroup_id);
    }

    public String getPerson_fio() {
        return person_fio.get();
    }

    public SimpleStringProperty person_fioProperty() {
        return person_fio;
    }

    public void setPerson_fio(String person_fio) {
        this.person_fio.set(person_fio);
    }

    public String getApp_caption() {
        return app_caption.get();
    }

    public SimpleStringProperty app_captionProperty() {
        return app_caption;
    }

    public void setApp_caption(String app_caption) {
        this.app_caption.set(app_caption);
    }

    public String getOrg_caption() {
        return org_caption.get();
    }

    public SimpleStringProperty org_captionProperty() {
        return org_caption;
    }

    public void setOrg_caption(String org_caption) {
        this.org_caption.set(org_caption);
    }

    public String getInsp_status() {
        return insp_status.get();
    }

    public SimpleStringProperty insp_statusProperty() {
        return insp_status;
    }

    public void setInsp_status(String insp_status) {
        this.insp_status.set(insp_status);
    }
}
