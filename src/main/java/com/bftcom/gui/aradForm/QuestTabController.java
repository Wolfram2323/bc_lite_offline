package com.bftcom.gui.aradForm;


import com.bftcom.context.Context;
import com.bftcom.context.Customization;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.Questions;
import com.bftcom.dbtools.entity.ViolationGroup;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.tableViewStoreObj.EmployeeGroupTVObject;
import com.bftcom.gui.utils.GuiUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

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
    private Accordion counterInfo_accor;
    @FXML
    private TextField auditOrg_caption_field;
    @FXML
    private TextField auditTheme_field;
    @FXML
    private DatePicker auditFrom_field;
    @FXML
    private DatePicker auditTo_field;
    @FXML
    private CheckBox counter_audit_cb;
    @FXML
    private WebView result_field;
    @FXML
    private TabPane violGroup_tabs;
    @FXML
    private TableView questInsp_tbl;
    @FXML
    private TableColumn id_col;
    @FXML
    private TableColumn fio_col;
    @FXML
    private TableColumn app_col;
    @FXML
    private TableColumn org_col;

    private WebEngine result_engine;

    private BigInteger quest_id;

    @FXML
    private void addViolation(ActionEvent event) {
        ViolationGroupTabController violationGroupController = new ViolationGroupTabController();
        violGroup_tabs.getTabs().add(violationGroupController);
        violationGroupController.initialize(null, quest_id);
        violGroup_tabs.getSelectionModel().select(violationGroupController);
    }

    @FXML
    private void deleteViolation(ActionEvent event) {
        ViolationGroupTabController currentTab = (ViolationGroupTabController) violGroup_tabs.getSelectionModel().getSelectedItem();
        if (currentTab.getViolationGroupId() != null) {
            Session session = HibernateUtils.getCurrentSession();
            Questions quest = session.get(Questions.class, quest_id);
            ViolationGroup vg = session.get(ViolationGroup.class, currentTab.getViolationGroupId());
            quest.getViolationGroup().remove(vg);
            session.delete(vg);
            session.saveOrUpdate(quest);
        }
        violGroup_tabs.getTabs().remove(currentTab);
    }

    public QuestTabController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/questTab.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e, null, null, true);
        }
    }

    public void initialize(Questions quest) {
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

        GuiUtils.textAndDateFieldSetUp(quest, this, null);

        if (Context.getCurrentContext().getCust().equals(Customization.TYUMEN)) {
            counter_audit_cb.setVisible(true);
            if (quest.getCounter_audit().equals(1)) {
                counterInfo_accor.setVisible(true);
            }
        } else {
            counter_audit_cb.setVisible(false);
            counterInfo_accor.setVisible(false);
        }


        if (quest.getResolved() != null) {
            resolved_cb.setSelected(GuiUtils.IntegerToBoolean(quest.getResolved()));
        }
        if(quest.getCounter_audit() != null){
            counter_audit_cb.setSelected(GuiUtils.IntegerToBoolean(quest.getCounter_audit()));
        }

        result_engine = result_field.getEngine();
        GuiUtils.loadCkEditorToWebView(result_engine, "result_field_" + quest.getId().toString());

        if (quest.getResult() != null) {
            result_engine.documentProperty().addListener(new ChangeListener<Document>() {
                @Override
                public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                    if (newDoc != null) {
                        result_engine.documentProperty().removeListener(this);
                        result_engine.executeScript("getCkEditor().setData('" + quest.getResult() + "');");
                    }
                }
            });
        }
        violGroup_tabs.getStyleClass().add("vTabPane");

        quest.getViolationGroup().forEach(violation -> {
            ViolationGroupTabController violationGroupController = new ViolationGroupTabController();
            violGroup_tabs.getTabs().add(violationGroupController);
            violationGroupController.initialize(violation, quest_id);
        });
        ObservableList<EmployeeGroupTVObject> qiList = FXCollections.observableArrayList();
        id_col.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject, Long>("id"));
        fio_col.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject, String>("person_fio"));
        app_col.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject, String>("app_caption"));
        org_col.setCellValueFactory(new PropertyValueFactory<EmployeeGroupTVObject, String>("org_caption"));
        quest.getQuestInspectorsSet().forEach(qi -> qiList.add(new EmployeeGroupTVObject(qi)));
        questInsp_tbl.setItems(qiList);

    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        Session session = HibernateUtils.getCurrentSession();
        Questions quest = session.get(Questions.class, quest_id);
        Object value = result_engine.executeScript("getCkEditor().getData();");
        quest.setResult(value.toString());
        GuiUtils.textAndDateFieldGetUp(quest,this,null );
        quest.setResolved(GuiUtils.BooleanToInteger(resolved_cb.isSelected()));
        violGroup_tabs.getTabs().forEach(tab -> ((AbstractBftTabController) tab).submitData(arad));
        session.saveOrUpdate(quest);
    }
}
