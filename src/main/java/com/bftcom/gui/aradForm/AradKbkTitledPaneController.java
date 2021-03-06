package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ARADkbk;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.KBKDetail;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.custom.cell.NumberTextFieldTableCell;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.referenceList.ReferenceListController;
import com.bftcom.gui.tableViewStoreObj.AradKbkTVObject;
import com.bftcom.gui.tableViewStoreObj.DoubleStringConverter;
import com.bftcom.gui.tableViewStoreObj.KbkDetailTVObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class AradKbkTitledPaneController extends AbstractBftTitledPaneController {
    @FXML
    private TableView aradKbk_tbl;
    @FXML
    private TableColumn auditedamount_col;
    @FXML
    private TableColumn id_col;
    @FXML
    private TableColumn kbkdetail_id_col;
    @FXML
    private TableColumn fsr_caption_col;
    @FXML
    private TableColumn financeamt_col;
    @FXML
    private Button addKbk_btn;
    @FXML
    private Button deleteKbk_btn;

    public AradKbkTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/auditedKbkTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @FXML
    private void addKbk (ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/referenceList.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Выбор проверенных средств");
        ReferenceListController controller = loader.getController();
        Session session = HibernateUtils.getCurrentSession();
        List<KBKDetail> kbkDetailList = session.createQuery("FROM KBKDetail").getResultList();
        controller.createTable(kbkDetailList, KbkDetailTVObject.createColumns(), KbkDetailTVObject.class, this,"");
        stage.showAndWait();
    }

    @FXML
    private void deleteKbk(ActionEvent event){
        ObservableList<AradKbkTVObject> allRows, selectedRows;
        allRows = aradKbk_tbl.getItems();
        selectedRows = aradKbk_tbl.getSelectionModel().getSelectedItems();
        allRows.removeAll(selectedRows);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void initialize(ActResultsAuditDoc arad) {
        ObservableList<AradKbkTVObject> kbkData = FXCollections.observableArrayList();
        id_col.setCellValueFactory(new PropertyValueFactory<AradKbkTVObject,Long>("id"));
        kbkdetail_id_col.setCellValueFactory(new PropertyValueFactory<AradKbkTVObject,Long>("kbkdetail_id"));
        fsr_caption_col.setCellValueFactory(new PropertyValueFactory<AradKbkTVObject,String>("fsr_caption"));
        financeamt_col.setCellValueFactory(new PropertyValueFactory<AradKbkTVObject, Double>("financeamt"));
        auditedamount_col.setCellValueFactory(new PropertyValueFactory<AradKbkTVObject, Double>("auditedamount"));
        auditedamount_col.setCellFactory(param -> new NumberTextFieldTableCell<AradKbkTVObject>());
        auditedamount_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<AradKbkTVObject,String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<AradKbkTVObject,String> event) {
                        AradKbkTVObject aradKbkTVObject = event.getTableView().getItems().get(event.getTablePosition().getRow());
                        aradKbkTVObject.setAuditedamount(event.getNewValue());


                    }
                }
        );
        arad.getAraDkbks().forEach(row->kbkData.add(new AradKbkTVObject(row)));
        aradKbk_tbl.setItems(kbkData);
        if(!Context.getCurrentContext().isAdmin()){
            addKbk_btn.setDisable(true);
            deleteKbk_btn.setDisable(true);
            auditedamount_col.setEditable(false);
        }
    }

    public void parseDataFromReference(ObservableList dataList, String param){
        dataList.forEach(row->{
            aradKbk_tbl.getItems().add(new AradKbkTVObject((KbkDetailTVObject) row));
        });
    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        ObservableList<AradKbkTVObject> tblData = aradKbk_tbl.getItems();
        ObservableList<AradKbkTVObject> forSynch = FXCollections.observableArrayList();
        List<ARADkbk> dbData = arad.getAraDkbks();
        Session session = HibernateUtils.getCurrentSession();
        DoubleStringConverter converter = new DoubleStringConverter();
        for(Iterator<ARADkbk> dbIter = dbData.iterator(); dbIter.hasNext();){
            ARADkbk dbRow = dbIter.next();
            long dbId = dbRow.getId().longValue();
            boolean needToDelete = true;
            for(Iterator<AradKbkTVObject> tblIter = tblData.iterator(); tblIter.hasNext();) {
                AradKbkTVObject tblRow = tblIter.next();
                if(tblRow.getId() == dbId){
                    if (dbRow.getAuditedamount().doubleValue() != converter.fromString(tblRow.getAuditedamount())){
                        dbRow.setAuditedamount(new BigDecimal(tblRow.getAuditedamount()));
                    }
                    forSynch.add(tblRow);
                    tblIter.remove();
                    needToDelete = false;
                    break;
                }
            }
            if(needToDelete){
                Query query = session.createQuery("delete ARADkbk where id = :id");
                query.setParameter("id", dbRow.getId());
                query.executeUpdate();
                dbIter.remove();
            }
        }
        if(tblData.size() > 0){
            tblData.forEach(tblRow -> {
                ARADkbk dbRow = new ARADkbk();
                BigInteger id = HibernateUtils.getNextIdForLinkTables(session,"ARADkbk",dbRow);
                Query query = session.createNativeQuery("insert into ACTRESULTSAUDITDOCKBK (ID,AUDITEDAMOUNT,ARAUDITDOC_ID,KBKDETAIL_ID) " +
                        "values (?,?,?,?)");
                query.setParameter(1,id);
                query.setParameter(2,new BigDecimal(tblRow.getAuditedamount()));
                query.setParameter(3,arad.getId());
                query.setParameter(4,tblRow.getKbkdetail_id());
                query.executeUpdate();
            });
        }
        forSynch.addAll(tblData);
        aradKbk_tbl.setItems(forSynch);
    }
}
