package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ARADInspectors;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.EmployeeGroup;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.referenceList.ReferenceListController;
import com.bftcom.gui.tableViewStoreObj.AradInspectorsTVbject;
import com.bftcom.gui.tableViewStoreObj.AradKbkTVObject;
import com.bftcom.gui.tableViewStoreObj.EmployeeGroupTVObject;

import com.bftcom.gui.utils.GuiUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class SignsTitledPaneController extends AbstractBftTitledPaneController {
    @FXML
    private TextField insp_head_fio_field;
    @FXML
    private TextField insp_head_appointment_field;
    @FXML
    private TextField deputy_head_fio_field;
    @FXML
    private TextField deputy_head_appointment_field;
    @FXML
    private TextField auditorg_head_fio_field;
    @FXML
    private TextField auditorg_head_app_field;
    @FXML
    private TextField accountantfio_field;
    @FXML
    private TextField accountantapp_field;

    @FXML
    private TableView signs_tbl;
    @FXML
    private TableColumn id_col;
    @FXML
    private TableColumn employeegroup_id_col;
    @FXML
    private TableColumn person_fio_col;
    @FXML
    private TableColumn app_caption_col;
    @FXML
    private TableColumn org_caption_col;
    @FXML
    private TableColumn insp_status_col;
    @FXML
    private Button add_btn;
    @FXML
    private Button delete_btn;

    private ObservableList<AradInspectorsTVbject> inspData;


    public SignsTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/signsTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(ActResultsAuditDoc arad) {
        GuiUtils.textAndDateFieldSetUp(arad,this,new ArrayList<>());
        inspData = FXCollections.observableArrayList();
        id_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,Long>("id"));
        employeegroup_id_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,Long>("employeegroup_id"));
        person_fio_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,String>("person_fio"));
        app_caption_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,String>("app_caption"));
        org_caption_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,String>("org_caption"));
        insp_status_col.setCellValueFactory(new PropertyValueFactory<AradInspectorsTVbject,String>("insp_status"));
        arad.getAradInspectorses().forEach(row->inspData.add(new AradInspectorsTVbject(row)));
        signs_tbl.setItems(inspData);
        if(!Context.getCurrentContext().isAdmin()){
            add_btn.setDisable(true);
            delete_btn.setDisable(true);
            auditorg_head_app_field.setEditable(false);
            auditorg_head_fio_field.setEditable(false);
            accountantapp_field.setEditable(false);
            accountantfio_field.setEditable(false);
        }
    }

    @FXML
    private void addEmployeeSign(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/referenceList.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Выбор инспекторов");
        ReferenceListController controller = loader.getController();
        Session session = HibernateUtils.getCurrentSession();
        List<EmployeeGroup> kbkDetailList = session.createQuery("FROM EmployeeGroup").getResultList();
        controller.createTable(kbkDetailList, EmployeeGroupTVObject.createColumns(), EmployeeGroupTVObject.class, this,"");
        stage.showAndWait();
    }

    @FXML
    private void deleteEmployeeSign(ActionEvent event){
        ObservableList<AradKbkTVObject> allRows, selectedRows;
        allRows = signs_tbl.getItems();
        selectedRows = signs_tbl.getSelectionModel().getSelectedItems();
        allRows.removeAll(selectedRows);
    }


    @Override
    public void submitData(ActResultsAuditDoc arad) {
        arad.setAuditorg_head_fio(auditorg_head_fio_field.getText());
        arad.setAuditorg_head_app(auditorg_head_app_field.getText());
        arad.setAccountantfio(accountantfio_field.getText());
        arad.setAccountantapp(accountantapp_field.getText());

        ObservableList<AradInspectorsTVbject> tblData = signs_tbl.getItems();
        ObservableList<AradInspectorsTVbject> forSynch = FXCollections.observableArrayList();
        List<ARADInspectors> dbData = arad.getAradInspectorses();
        Session session = HibernateUtils.getCurrentSession();
        for(Iterator<ARADInspectors> dbIter = dbData.iterator(); dbIter.hasNext();){
            ARADInspectors dbRow = dbIter.next();
            long db_id = dbRow.getId().longValue();
            boolean needToDelete = true;
            for(Iterator<AradInspectorsTVbject> tbIter = tblData.iterator(); tbIter.hasNext();){
                AradInspectorsTVbject tblRow = tbIter.next();
                if(tblRow.getId() == db_id){
                    needToDelete = false;
                    tbIter.remove();
                    forSynch.add(tblRow);
                    break;
                }
            }
            if(needToDelete){
                Query query = session.createQuery("delete ARADInspectors where id = :id");
                query.setParameter("id", dbRow.getId());
                query.executeUpdate();
                dbIter.remove();
            }
        }
        if(tblData.size() > 0){
            tblData.forEach(tblRow->{
                ARADInspectors newRow = new ARADInspectors();
                newRow.setEmployeeGroup(session.get(EmployeeGroup.class, BigInteger.valueOf(tblRow.getEmployeegroup_id())));
                newRow.setActResultsAuditDoc(arad);
                session.saveOrUpdate(newRow);
                dbData.add(newRow);
            });
        }
        forSynch.addAll(tblData);
        signs_tbl.setItems(forSynch);
    }

    public void parseDataFromReference(ObservableList dataList, String param){
        dataList.forEach(row->{
            signs_tbl.getItems().add(new AradInspectorsTVbject((EmployeeGroupTVObject) row));
        });
    }
}
