package com.bftcom.gui.aradForm;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.utils.GuiUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import java.io.IOException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import org.w3c.dom.Document;


/**
 * Created by k.nikitin on 06.11.2016.
 */
public class BaseFieldsTitledPaneController extends AbstractBftTitledPaneController {

    @FXML
    private TextField docNumber_field;
    @FXML
    private DatePicker docDate_field;
    @FXML
    private TextField casenumber_field;
    @FXML
    private DatePicker approvedate_field;
    @FXML
    private TextField actSigning_field;
    @FXML
    private TextField auditOrg_caption_field;
    @FXML
    private TextField controlform_field;
    @FXML
    private TextField auditTheme_field;
    @FXML
    private TextField controlOrg_caption_field;
    @FXML
    private TextField territory_field;
    @FXML
    private DatePicker auditFrom_field;
    @FXML
    private DatePicker auditTo_field;
    @FXML
    private DatePicker actualExecFrom_field;
    @FXML
    private DatePicker actualExecTo_field;
    @FXML
    private WebView obstacle_field;

    private WebEngine obstacle_engine;

    public BaseFieldsTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/baseFieldsTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(ActResultsAuditDoc arad){
        GuiUtils.textAndDateFieldSetUp(arad, this, Collections.singletonList("actSigning_field"));
        switch (arad.getActSigning()){
            case 1: actSigning_field.setText("Подписан Объектом контроля");break;
            case 2: actSigning_field.setText("Не подписан Объектом контроля"); break;
            case 3: actSigning_field.setText("Подписан Объектом контроля с возражениями");break;
            default: actSigning_field.setText("");break;

        }
        obstacle_engine = obstacle_field.getEngine();
        GuiUtils.loadCkEditorToWebView(obstacle_engine, "obstacle_field");
        if(arad.getObstacle() != null){
            obstacle_engine.documentProperty().addListener(new ChangeListener<Document>() {
                @Override public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                    if (newDoc != null) {
                        obstacle_engine.documentProperty().removeListener(this);
                        obstacle_engine.executeScript("getCkEditor().setData('" + arad.getObstacle() + "');");
                    }
                }
            });
        }
        if(!Context.getCurrentContext().isAdmin()){
            obstacle_field.setDisable(true);
        }
    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        Object value = obstacle_engine.executeScript("getCkEditor().getData();");
        arad.setObstacle(value.toString());
    }

    public Date stopEdit(){
        LocalDate now = LocalDate.now();
        approvedate_field.setValue(now);
        return Date.valueOf(now);
    }
}
