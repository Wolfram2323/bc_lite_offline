package com.bftcom.gui.aradForm;


import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.Questions;
import com.bftcom.dbtools.entity.ViolationGroup;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.exception.ExceptionMessage;
import com.bftcom.gui.utils.GuiUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public class QuestTabController extends AbstractBftTabController {
    @FXML
    private Tab quest_Tab;
    @FXML
    private TextArea caption_field;
    @FXML
    private CheckBox resolved_cb;
    @FXML
    private WebView result_field;
    @FXML
    private  TabPane violGroup_tabs;

    private WebEngine result_engine;

    private BigInteger quest_id;

    @FXML
    private void addViolation(ActionEvent event){
        ViolationGroupTabController violationGroupController = new ViolationGroupTabController();
        violGroup_tabs.getTabs().add(violationGroupController);
        violationGroupController.initialize(null, quest_id);
    }
    @FXML
    private void deleteViolation(ActionEvent event){
        ViolationGroupTabController currentTab = (ViolationGroupTabController) violGroup_tabs.getSelectionModel().getSelectedItem();
        if(currentTab.getViolationGroupId() != null){
            Session session = HibernateUtils.getCurrentSession();
            Questions quest = session.get(Questions.class,quest_id);
            ViolationGroup vg = session.get(ViolationGroup.class, currentTab.getViolationGroupId());
            quest.getViolationGroup().remove(vg);
            session.delete(vg);
            session.saveOrUpdate(quest);
        }
        violGroup_tabs.getTabs().remove(currentTab);
    }

    public QuestTabController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/questTab.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ExceptionMessage.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    public void initialize(Questions quest){
        Label tabName = new Label();
        tabName.setText(quest.getCaption());
        tabName.setWrapText(true);
        tabName.setMaxWidth(90);

        StackPane stack = new StackPane();
        stack.getChildren().add(tabName);
        tabName.prefWidthProperty().bind(stack.prefWidthProperty());
        quest_Tab.setGraphic(stack);
        quest_Tab.getStyleClass().add("rotateTab");

        quest_id = quest.getId();

        caption_field.setText(quest.getCaption());

        if(quest.getResolved() != null){
            resolved_cb.setSelected(quest.getResolved());
        }

        result_engine = result_field.getEngine();
        GuiUtils.loadCkEditorToWebView(result_engine,"result_field_" + quest.getId().toString());

        if(quest.getResult() != null){
            result_engine.documentProperty().addListener(new ChangeListener<Document>() {
                @Override public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                    if (newDoc != null) {
                        result_engine.documentProperty().removeListener(this);
                        result_engine.executeScript("getCkEditor().setData('" + quest.getResult() + "');");
                    }
                }
            });
        }
        violGroup_tabs.getStyleClass().add("vTabPane");

        quest.getViolationGroup().forEach(violation->{
            ViolationGroupTabController violationGroupController = new ViolationGroupTabController();
            violGroup_tabs.getTabs().add(violationGroupController);
            violationGroupController.initialize(violation, quest_id);
        });


    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        Session session = HibernateUtils.getCurrentSession();
        Questions quest = session.get(Questions.class,quest_id);
        Object value = result_engine.executeScript("getCkEditor().getData();");
        quest.setResult(value.toString());
        quest.setResolved(resolved_cb.isSelected());
        violGroup_tabs.getTabs().forEach(tab->((AbstractBftTabController)tab).submitData(arad));
        session.saveOrUpdate(quest);
    }
}
