package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.SystemParameters;
import com.bftcom.dbtools.utils.DataSynchronizer;
import com.bftcom.dbtools.utils.DataUploader;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.ReportPrint;
import com.bftcom.gui.utils.PropertiesAndParameters;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.utils.ReportGenerator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Session;

import org.hibernate.query.Query;


import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class ActResultsAuditDocFormController implements Initializable, ReportPrint {
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
    private MenuButton print_btn;
    @FXML
    private RadioMenuItem printOdt_rm;
    @FXML
    private RadioMenuItem printDoc_rm;
    @FXML
    private ProgressBar printDoc_prgBar;

    @FXML
    private Button saveBtn;

    private BigInteger arad_id;

    private boolean readOnly;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            processCustom();
            setUpPrintBtn();
            BaseFieldsTitledPaneController baseFieldsController = new BaseFieldsTitledPaneController();
            mainAccordion.getPanes().add(0, baseFieldsController);
            AradKbkTitledPaneController aradKbkController = new AradKbkTitledPaneController();
            mainAccordion.getPanes().add(1, aradKbkController);
            SignsTitledPaneController signsController = new SignsTitledPaneController();
            mainAccordion.getPanes().add(2, signsController);
            AuditBasisTitledPaneController auditBasisController = new AuditBasisTitledPaneController();
            mainAccordion.getPanes().add(3, auditBasisController);
            QuestionsTitledPaneController questionsController = new QuestionsTitledPaneController();
            mainAccordion.getPanes().add(4, questionsController);

            Session session = HibernateUtils.getCurrentSession();
            Query<ActResultsAuditDoc> query = session.createQuery("FROM ActResultsAuditDoc");
            ActResultsAuditDoc arad = query.getSingleResult();
            arad_id = arad.getId();
            session.beginTransaction();
            if (Context.getCurrentContext().isAdmin()) {
                impBtn.setDisable(false);
            }
            if (arad.getDoc_status().longValue() == 0 || arad.getDoc_status().longValue() == 28) {
                doc_status_field.setText("Оффлайн - Черновик");
            } else {
                doc_status_field.setText("Форматирование завершено");

                expBtn.setDisable(false);
                impBtn.setDisable(true);
                saveBtn.setDisable(true);
                stopBtn.setDisable(true);
                readOnly = true;
            }
            baseFieldsController.initialize(arad);
            auditBasisController.initialize(arad);
            signsController.initialize(arad);
            aradKbkController.initialize(arad);
            questionsController.initialize(arad);

        } catch (Throwable e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Произошла ошибка", null, false);
        }


    }

    @FXML
    private void submitData(ActionEvent event) {
        Session session = HibernateUtils.getCurrentSession();
        ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class, arad_id);
        mainAccordion.getPanes().forEach(titledPane -> ((AbstractBftTitledPaneController) titledPane).submitData(arad));
        try {
            session.saveOrUpdate(arad);
            session.getTransaction().commit();
            Message.showInfoMessage("Сохранение завершено", "Сохранение успешно.");
        } catch (Throwable e) {
            Message.throwExceptionForJavaFX(e, "Сохранение завершено с ошибкой", "Сохранение невозможно. Смотрите описание ошибки ниже", false);
        } finally {
            HibernateUtils.closeSession();
            HibernateUtils.getCurrentSession().beginTransaction();
        }
    }

    @FXML
    private void exitAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выход из системы");
        alert.setHeaderText("Вы действительно хотите завершить работу с ситемой?");
        if (!readOnly) {
            alert.setContentText("В случае завершения работы, все несохраненные данные пропадут.");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                HibernateUtils.getCurrentSession().getTransaction().rollback();
                HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
                Platform.exit();
            }
        }
    }

    @FXML
    private void stopEdit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Завершение форматирования");
        alert.setHeaderText("Вы действительно хотите завершить изменения в документе?");
        alert.setContentText("В случае завершения редактирования, вносить изменения в документ будет невозможно. Текущие изменения не сохрнятся");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Session session = HibernateUtils.getCurrentSession();
                session.getTransaction().rollback();
                session.beginTransaction();
                ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class, arad_id);
                arad.setDoc_status(new BigInteger("3"));
                if (Context.getCurrentContext().isAdmin()) {
                    arad.setApprovedate(((BaseFieldsTitledPaneController) mainAccordion.getPanes().get(0)).stopEdit());
                }
                session.saveOrUpdate(arad);
                session.getTransaction().commit();
                HibernateUtils.getCurrentSession().beginTransaction();
                doc_status_field.setText("Форматирование завершено");
                expBtn.setDisable(false);
                impBtn.setDisable(true);
                saveBtn.setDisable(true);
                stopBtn.setDisable(true);
                readOnly = true;

            } catch (Exception e) {
                Message.throwExceptionForJavaFX(e, "Ошибка при процессе завершения форматирования", null, false);
            }


        }

    }

    @FXML
    private void exportData(ActionEvent event) {
        try {
            DataUploader dataUploader = new DataUploader();
            Context con = Context.getCurrentContext();
            if (con.isAdmin()) {
                dataUploader.fullExport(arad_id);
            } else {
                dataUploader.exportForOtherOffline();
            }
            Message.showInfoMessage("Завершение выгрузки данных", "Процесс выгрузки данных завершен.");
        } catch (Exception e) {
            Message.throwExceptionForJavaFX(e, "Ошибка при выгрузке данных", null, false);
        }


    }

    @FXML
    private void importData(ActionEvent event) throws IOException {
        try {
            HibernateUtils.getCurrentSession().getTransaction().rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выбор файла");
        File file = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            DataSynchronizer dataSynchronizer = new DataSynchronizer();
            dataSynchronizer.synchronizeData(file);
        }
        Message.showInfoMessage("Завершение загрузки данных", "Процесс загрузки данных в систему завершен.");

        Parent parent = FXMLLoader.load(getClass().getResource("/fxml/aradForm.fxml"));
        ((Node) event.getSource()).getScene().getWindow().hide();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Электронный документ Акт проверки");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Выход из системы");
                alert.setHeaderText("Вы действительно хотите завершить работу с ситемой?");
                alert.setContentText("В случае завершения работы, все несохраненные данные пропадут.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.CANCEL) {
                    we.consume();
                    return;
                }
                HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
            }
        });
        stage.show();

    }

    @FXML
    @Override
    public void printDoc(ActionEvent event) {
        print_btn.setDisable(true);
        Session session = HibernateUtils.getNewSession();
        SystemParameters outputFormat = session.createQuery("FROM SystemParameters where name =:name", SystemParameters.class).
                setParameter("name", PropertiesAndParameters.PRINT_FORMAT.toString()).uniqueResultOptional().orElse(new SystemParameters(PropertiesAndParameters.REPORT_TEMPLATE.toString(), "odt"));
        session.close();
        Map<String, Object> params = new HashMap<>();
        params.put("ID", arad_id);
        String reportTemplateName = ((MenuItem) event.getTarget()).getProperties().get(PropertiesAndParameters.REPORT_TEMPLATE).toString();

        Task generateReport = new ReportGenerator(outputFormat.getValue(), reportTemplateName, params, HibernateUtils.DERBY_DB_URL);
        printDoc_prgBar.progressProperty().bind(generateReport.progressProperty());
        new Thread(generateReport).start();

        print_btn.setDisable(false);
    }

    @FXML
    private void setPrintFormat(ActionEvent event) {
        String format = ((RadioMenuItem) event.getTarget()).getText();
        Session session = HibernateUtils.getNewSession();
        session.beginTransaction();
        SystemParameters printFormat = session.createQuery("FROM SystemParameters where name =:name", SystemParameters.class).setParameter("name", PropertiesAndParameters.PRINT_FORMAT.toString()).uniqueResult();
        if (printFormat != null) {
            printFormat.setValue(format);
            session.saveOrUpdate(printFormat);
        } else {
            printFormat = new SystemParameters();
            printFormat.setName(PropertiesAndParameters.PRINT_FORMAT.toString());
            printFormat.setValue(format);
            session.persist(printFormat);
        }
        session.getTransaction().commit();
    }


    public void setUpPrintBtn() {
        Session session = HibernateUtils.getCurrentSession();
        Query<SystemParameters> paramQuery = session.createQuery(" FROM SystemParameters where name = :name", SystemParameters.class).setParameter("name", PropertiesAndParameters.PRINT_FORMAT.toString());
        java.util.List<SystemParameters> params = paramQuery.getResultList();
        if (!params.isEmpty()) {
            switch (params.get(0).getValue()) {
                case "odt":
                    printOdt_rm.setSelected(true);
                    break;
                case "doc":
                    printDoc_rm.setSelected(true);
                    break;
            }
        }
    }


}
