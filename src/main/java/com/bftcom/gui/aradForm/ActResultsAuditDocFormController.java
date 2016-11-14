package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.utils.DataSynchronizer;
import com.bftcom.dbtools.utils.DataUploader;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.exception.ExceptionMessage;
import com.bftcom.gui.utils.GuiUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class ActResultsAuditDocFormController implements Initializable {
    @FXML
    private Accordion mainAccordion;
    @FXML
    private TextField doc_status_field;
    @FXML
    private Button expBtn;
    @FXML
    private Button impBtn;
    @FXML
    private Button stopBtn;

    @FXML
    private Button saveBtn;

    private BigInteger arad_id;

    private boolean readOnly;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            BaseFieldsTitledPaneController baseFieldsController = new BaseFieldsTitledPaneController();
            mainAccordion.getPanes().add(0,baseFieldsController);
            AradKbkTitledPaneController aradKbkController = new AradKbkTitledPaneController();
            mainAccordion.getPanes().add(1,aradKbkController);
            SignsTitledPaneController signsController = new SignsTitledPaneController();
            mainAccordion.getPanes().add(2,signsController);
            AuditBasisTitledPaneController auditBasisController = new AuditBasisTitledPaneController();
            mainAccordion.getPanes().add(3,auditBasisController);
            QuestionsTitledPaneController questionsController = new QuestionsTitledPaneController();
            mainAccordion.getPanes().add(4,questionsController);

            Session session = HibernateUtils.getCurrentSession();
            Query<ActResultsAuditDoc> query = session.createQuery("FROM ActResultsAuditDoc");
            ActResultsAuditDoc arad = query.getSingleResult();
            arad_id = arad.getId();
            session.beginTransaction();
            if(arad.getDoc_status().longValue() == 0) {
                doc_status_field.setText("Оффлайн - Черновик");
            } else {
                doc_status_field.setText("Форматироние завершено");

                expBtn.setDisable(false);
                impBtn.setDisable(true);
                saveBtn.setDisable(true);
                stopBtn.setDisable(true);
                readOnly = true;
            }
            if(Context.getCurrentContext().isAdmin()){
                impBtn.setDisable(false);
            }
            baseFieldsController.initialize(arad);
            auditBasisController.initialize(arad);
            signsController.initialize(arad);
            aradKbkController.initialize(arad);
            questionsController.initialize(arad);

        } catch (Throwable e){
            e.printStackTrace();
            ExceptionMessage.throwExceptionForJavaFX(e,"Произошла ошибка", null, false);
        }


    }

    @FXML
    private void submitData (ActionEvent event){
        Session session = HibernateUtils.getCurrentSession();
        ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class,arad_id);
        mainAccordion.getPanes().forEach(titledPane -> ((AbstractBftTitledPaneController)titledPane).submitData(arad));
        try{
            session.saveOrUpdate(arad);
            session.getTransaction().commit();
        } finally {
            HibernateUtils.closeSession();
            HibernateUtils.getCurrentSession().beginTransaction();
        }
    }

    @FXML
    private void exitAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выход из системы");
        alert.setHeaderText("Вы действительно хотите завершить работу с ситемой?");
        if(!readOnly){
            alert.setContentText("В случае завершения работы, все несохраненные данные пропадут.");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            HibernateUtils.getCurrentSession().getTransaction().rollback();
            HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
            GuiUtils.cleanHtmlDir();
            System.exit(0);
        }
    }

    @FXML
    private void stopEdit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Завершение форматирования");
        alert.setHeaderText("Вы действительно хотите завершить изменения в документе?");
        alert.setContentText("В случае завершения редактирования, вносить изменения в документ будет невозможно. Текущие изменения не сохрнятся");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Session session = HibernateUtils.getCurrentSession();
            ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class,arad_id);
//            arad.setDoc_status(new BigInteger("3"));
            session.saveOrUpdate(arad);
            session.getTransaction().commit();
            HibernateUtils.getCurrentSession().beginTransaction();
            doc_status_field.setText("Форматироние завершено");
            expBtn.setDisable(false);
            impBtn.setDisable(true);
            saveBtn.setDisable(true);
            stopBtn.setDisable(true);
            readOnly = true;

        }

    }
    @FXML
    private void exportData(ActionEvent event){
        DataUploader dataUploader = new DataUploader();
        Context con = Context.getCurrentContext();
        if(con.isAdmin()){
            dataUploader.fullExport(arad_id);
        } else {
            dataUploader.exportForOtherOffline();
        }

    }
    @FXML
    private void importData(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выбор файла");
        File file = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if(file != null){
            DataSynchronizer dataSynchronizer = new DataSynchronizer();
            dataSynchronizer.synchronizeData(file);
        }

    }
}
