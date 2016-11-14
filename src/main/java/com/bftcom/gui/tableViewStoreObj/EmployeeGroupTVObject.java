package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.EmployeeGroup;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class EmployeeGroupTVObject {
    private final SimpleLongProperty id;
    private final SimpleStringProperty person_fio;
    private final SimpleStringProperty app_caption;
    private final SimpleStringProperty org_caption;
    private final SimpleStringProperty insp_status;

    public EmployeeGroupTVObject(EmployeeGroup empg){
        this.id = new SimpleLongProperty(empg.getId().longValue());
        this.person_fio = new SimpleStringProperty(empg.getEmployee().getPerson_fio());
        this.app_caption = new SimpleStringProperty(empg.getEmployee().getAppointment().getCaption());
        this.org_caption = new SimpleStringProperty(empg.getEmployee().getOrg_caption());
        this.insp_status = new SimpleStringProperty(empg.getInsp_status());
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

    public static List<TableColumn> createColumns(){
        List<TableColumn> result = new LinkedList<>();
        TableColumn id  = new TableColumn();
        id.setCellValueFactory(new PropertyValueFactory<KbkDetailTVObject,Long>("id"));
        id.setEditable(false);
        id.setVisible(false);
        result.add(id);
        TableColumn person_fio = new TableColumn("ФИО инспектора");
        person_fio.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject,String>("person_fio"));
        person_fio.setEditable(false);
        result.add(person_fio);
        TableColumn app_caption = new TableColumn();
        app_caption.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject,String>("app_caption"));
        app_caption.setEditable(false);
        result.add(app_caption);
        TableColumn org_caption = new TableColumn();
        org_caption.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject,String>("org_caption"));
        org_caption.setEditable(false);
        result.add(org_caption);
        TableColumn insp_status = new TableColumn();
        insp_status.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject, String>("insp_status"));
        insp_status.setEditable(false);
        result.add(insp_status);
        return result;
    }
}
