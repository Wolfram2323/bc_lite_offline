package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.utils.HibernateUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
    private Button synchBtn;

    private BigInteger arad_id;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BaseFieldsTitledPaneController baseFieldsController = new BaseFieldsTitledPaneController();
        mainAccordion.getPanes().add(0,baseFieldsController);
        AradKbkTitledPaneController aradKbkController = new AradKbkTitledPaneController();
        mainAccordion.getPanes().add(1,aradKbkController);
        SignsTitledPaneController signsController = new SignsTitledPaneController();
        mainAccordion.getPanes().add(2,signsController);
        AuditBasisTitledPaneController auditBasisController = new AuditBasisTitledPaneController();
        mainAccordion.getPanes().add(3,auditBasisController);

        Session session = HibernateUtils.getCurrentSession();
        Query<ActResultsAuditDoc> query = session.createQuery("FROM ActResultsAuditDoc");
        ActResultsAuditDoc arad = query.getSingleResult();
        if(arad.getDoc_status().longValue() == 0) {
            doc_status_field.setText("Оффлайн - Черновик");
        } else {
            doc_status_field.setText("Форматироние завершено");
            synchBtn.setDisable(false);
        }
        baseFieldsController.initialize(arad);



    }

    @FXML
    private void exitAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выход из системы");
        alert.setHeaderText("Вы действительно хотите завершить работу с ситемой?");
        alert.setContentText("В случае завершения работы, все несохраненные данные пропадут.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
            System.exit(0);
        }
    }
}
