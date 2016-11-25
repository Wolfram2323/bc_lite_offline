package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.utils.DataSynchronizer;
import com.bftcom.dbtools.utils.DataUploader;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.utils.GuiUtils;
//import com.bftcom.report.BirtReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.query.Query;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
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
    private Button print_btn;

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
            if(arad.getDoc_status().longValue() == 0 || arad.getDoc_status().longValue() == 28) {
                doc_status_field.setText("Оффлайн - Черновик");
            } else {
                doc_status_field.setText("Форматирование завершено");

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
            Message.throwExceptionForJavaFX(e,"Произошла ошибка", null, false);
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
            Message.showInfoMessage("Сохранение завершено", "Сохранение успешно.");
        }catch (Throwable e){
            Message.throwExceptionForJavaFX(e,"Сохранение завершено с ошибкой","Сохранение невозможно. Смотрите описание ошибки ниже", false);
        }finally {
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
            try{
                HibernateUtils.getCurrentSession().getTransaction().rollback();
                HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
            } catch (Exception e){
                e.printStackTrace();
                System.exit(0);
            }

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
            try{
                Session session = HibernateUtils.getCurrentSession();
                session.getTransaction().rollback();
                session.beginTransaction();
                ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class,arad_id);
                arad.setDoc_status(new BigInteger("3"));
                session.saveOrUpdate(arad);
                session.getTransaction().commit();
                HibernateUtils.getCurrentSession().beginTransaction();
                doc_status_field.setText("Форматирование завершено");
                expBtn.setDisable(false);
                impBtn.setDisable(true);
                saveBtn.setDisable(true);
                stopBtn.setDisable(true);
                readOnly = true;
            } catch (Exception e){
                Message.throwExceptionForJavaFX(e,"Ошибка при процессе завершения форматирования", null, false);
            }


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
        Message.showInfoMessage("Завершение выгрузки данных", "Процесс выгрузки данных завершен.");

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
        Message.showInfoMessage("Завершение загрузки данных", "Процесс загрузки данных в систему завершен.");

    }
    @FXML
    private void printDoc(ActionEvent event){
        try{
            print_btn.setDisable(true);
            Session session = HibernateUtils.getSession(null);
            Connection conection = ((SessionImpl)session).connection();
            String outputFormat = "doc";
            Map<String,Object> params = new HashMap<>();
            params.put("ID",arad_id);
            try {
                File report = File.createTempFile("birt","." + outputFormat);
                InputStream template = getClass().getResourceAsStream("/reportTemplates/actResultControlEvent.rptdesign");
                FileOutputStream reportStream = new FileOutputStream(report);
//                BirtReport.generate(outputFormat, template,reportStream ,params,conection);
                template.close();
                reportStream.close();
                Desktop.getDesktop().open(report);
            } catch (IOException e) {
                e.printStackTrace();
                Message.throwExceptionForJavaFX(e,"Ошибка генерации отчета","", false);
            }
        } finally {
            print_btn.setDisable(false);
        }



    }


}
