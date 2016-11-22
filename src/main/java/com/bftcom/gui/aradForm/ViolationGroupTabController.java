package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.*;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.referenceList.ReferenceListController;
import com.bftcom.gui.referenceList.ViolationKbkNewFormController;
import com.bftcom.gui.tableViewStoreObj.*;
import com.bftcom.gui.utils.GuiUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.w3c.dom.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public class ViolationGroupTabController extends AbstractBftTabController {
    @FXML
    private Tab violTab;
    @FXML
    private TextField violation_field;
    @FXML
    private TextArea violationCaption_field;
    @FXML
    private TextField bnpa_look_field;
    @FXML
    private TextField bnpa_num_field;
    @FXML
    private DatePicker bnpa_date_field;
    @FXML
    private TextField bnpa_caption_field;
    @FXML
    private TextField bnpa_chapter_field;
    @FXML
    private TextField bnpa_clause_field;
    @FXML
    private TextField bnpa_paragraph_field;
    @FXML
    private TextField bnpa_subparagraph_field;
    @FXML
    private TextField bnpa_rubric_field;
    @FXML
    private TextField violationType_field;
    @FXML
    private TextField actpagenumber_field;
    @FXML
    private WebView violDesc_web;
    @FXML
    private TextArea violationDescription_short_field;
    @FXML
    private CheckBox noneedcorrection_cb;
    @FXML
    private WebView noCorrect_web;
    @FXML
    private TextArea supdocuments_detaill_field;
    @FXML
    private CheckBox hasDisAgr_cb;
    @FXML
    private CheckBox disArgAccepted_cb;
    @FXML
    private TableView violKbK_tbl;
    @FXML
    private TableColumn id_col;
    @FXML
    private TableColumn kbkDetail_id_col;
    @FXML
    private TableColumn fsr_caption_col;
    @FXML
    private TableColumn amount_col;
    @FXML
    private TableColumn cash_col;
    @FXML
    private TableColumn grbs_col;
    @FXML
    private TableColumn account_col;
    @FXML
    private TableColumn budget_col;

    private BigInteger quest_id;
    private BigInteger violationGroup_id;

    private BigInteger violation_id;
    private BigInteger violationType_id;
    private BigInteger bnpa_id;

    private Label tabName;
    private WebEngine violDesc_engine;
    private WebEngine noCorrect_engine;

    @FXML
    private void browseViolation(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/referenceList.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Выбор нарушения");
        ReferenceListController controller = loader.getController();
        Session session = HibernateUtils.getCurrentSession();
        List<Violation> kbkDetailList = session.createQuery("FROM Violation").getResultList();
        controller.createTable(kbkDetailList, ViolationTVObject.createColumns(), ViolationTVObject.class, this,"violation");
        stage.showAndWait();


    }

    @FXML
    private void clearViolation(ActionEvent event){
        violation_field.setText("");
        violation_id = null;

    }

    @FXML
    private void  browseBNPA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/referenceList.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Выбор нарушенных НПА");
        ReferenceListController controller = loader.getController();
        Session session = HibernateUtils.getCurrentSession();
        List<BrokenNPA> kbkDetailList = session.createQuery("FROM BrokenNPA").getResultList();
        controller.createTable(kbkDetailList, BrokenNpaTVObject.createColumns(), BrokenNpaTVObject.class, this,"bnpa");
        stage.showAndWait();
    }

    @FXML
    private void clearBNPA(ActionEvent event){
        bnpa_caption_field.setText("");
        bnpa_look_field.setText("");
        bnpa_num_field.setText("");
        bnpa_date_field.setValue(null);
        bnpa_id = null;

    }
    @FXML
    private void  browseViolType(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/referenceList.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Выбор типа нарушения");
        ReferenceListController controller = loader.getController();
        Session session = HibernateUtils.getCurrentSession();
        List<ViolationType> kbkDetailList = session.createQuery("FROM ViolationType").getResultList();
        controller.createTable(kbkDetailList, ViolaionTypeTVObject.createColumns(), ViolaionTypeTVObject.class, this,"violType");
        stage.showAndWait();

    }

    @FXML
    private void clearViolType(ActionEvent event){
        violationType_field.setText("");
        violationType_id = null;

    }


    @FXML
    private void addViolKBK(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/violKbk.fxml"));
        Parent parent = loader.load();
        parent.getStylesheets().add("/css/styles.css");
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Создание средств с нарушениями");
        ViolationKbkNewFormController controller = loader.getController();
        controller.setParentController(this);
        stage.showAndWait();

    }

    public  void addNewViolKbk(ViolationKBKTVObject violKbkTV){
        violKbK_tbl.getItems().add(violKbkTV);
    }

    @FXML
    private void deleteViolKbk(ActionEvent event){
        ObservableList<ViolationKBKTVObject> allRows, selectedRows;
        allRows = violKbK_tbl.getItems();
        selectedRows = violKbK_tbl.getSelectionModel().getSelectedItems();
        allRows.removeAll(selectedRows);

    }


    public ViolationGroupTabController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/violationGroup.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    public void initialize(ViolationGroup violation, BigInteger quest_id){
        this.quest_id = quest_id;
        tabName = new Label();
        tabName.setText(violation == null ? "Нарушение" : violation.getViolation().getCaption());
        tabName.setWrapText(true);
        tabName.setMaxWidth(90);

        StackPane stack = new StackPane();
        stack.getChildren().add(tabName);
        tabName.prefWidthProperty().bind(stack.prefWidthProperty());
        violTab.setGraphic(stack);

        violDesc_engine = violDesc_web.getEngine();
        GuiUtils.loadCkEditorToWebView(violDesc_engine, violation == null ? "violatioDesc_" + UUID.randomUUID().toString() : "violatioDesc_" +violation.getId().toString());

        noCorrect_engine = noCorrect_web.getEngine();
        GuiUtils.loadCkEditorToWebView(noCorrect_engine, violation == null ? "noCorrect_" + UUID.randomUUID().toString() : "noCorrect_" +violation.getId().toString());

       id_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Long>("id"));
       kbkDetail_id_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Long>("kbkDetail_id"));
       fsr_caption_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,String>("fsr_caption"));
       amount_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Double>("amount"));
       amount_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
       cash_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Double>("cash"));
       cash_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
       grbs_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Double>("grbs"));
       grbs_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
       account_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Double>("account"));
       account_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
       budget_col.setCellValueFactory(new PropertyValueFactory<ViolationKBKTVObject,Double>("budget"));
       budget_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));


        if(violation != null){
            violationGroup_id = violation.getId();
            GuiUtils.textAndDateFieldSetUp(violation,this, Arrays.asList("violation_field", "violationCaption_field", "violationType_field"));

            violation_field.setText(violation.getViolation().getCode() == null ? "" : violation.getViolation().getCode());
            if(violation.getViolation() != null ){
                violationCaption_field.setText(violation.getViolation().getCaption() == null ? "" : violation.getViolation().getCaption());
                violation_id = violation.getViolation().getId();
            }
            if(violation.getViolationType() != null ){
                violationType_field.setText(violation.getViolationType().getCaption() == null ? "" : violation.getViolationType().getCaption());
                violationType_id = violation.getViolationType().getId();
            }


            if(violation.getViolationDescription() != null){
                violDesc_engine.documentProperty().addListener(new ChangeListener<Document>() {
                    @Override public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                        if (newDoc != null) {
                            violDesc_engine.documentProperty().removeListener(this);
                            violDesc_engine.executeScript("getCkEditor().setData('" + violation.getViolationDescription() + "');");
                        }
                    }
                });
            }
            if(violation.getNoneedcorrectionBasis() != null){
                noCorrect_engine.documentProperty().addListener(new ChangeListener<Document>() {
                    @Override public void changed(ObservableValue<? extends Document> observableValue, Document document, Document newDoc) {
                        if (newDoc != null) {
                            noCorrect_engine.documentProperty().removeListener(this);
                            noCorrect_engine.executeScript("getCkEditor().setData('" + violation.getNoneedcorrectionBasis() + "');");
                        }
                    }
                });
            }
            if(violation.getNoneedcorrection() != null){
                noneedcorrection_cb.setSelected(GuiUtils.IntegerToBoolean(violation.getNoneedcorrection()));
            }
            if(violation.getHavedisagreements() != null){
                hasDisAgr_cb.setSelected(GuiUtils.IntegerToBoolean(violation.getHavedisagreements()));
            }
            if(violation.getDisagreementaccepted() != null){
                disArgAccepted_cb.setSelected(GuiUtils.IntegerToBoolean(violation.getDisagreementaccepted()));
            }

            ObservableList<ViolationKBKTVObject> violkbkData = FXCollections.observableArrayList();
            violation.getViolationGroupKBK().forEach(kbkRow->violkbkData.add(new ViolationKBKTVObject(kbkRow)));
        } else {
            violTab.getStyleClass().add("rotateTab"); //при заполненных полял крутит 2 раза
        }
    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        if(violation_id == null){
            violation_field.getStyleClass().add("validation-error");
            RuntimeException e = new RuntimeException("Необходимо заполнить поле \"Номер по классификатору\"");
            Message.throwExceptionForJavaFX(e,"Ошибка заполнения поля", null,false);
            throw e;
        }
        Session session = HibernateUtils.getCurrentSession();
        ViolationGroup violation;
        if(violationGroup_id != null ){
            violation = session.get(ViolationGroup.class, violationGroup_id);
        } else {
            violation = new ViolationGroup();
        }
        if(violation_id != null){
            violation.setViolation(session.get(Violation.class,violation_id));
        }
        if(violationType_id != null){
            violation.setViolationType(session.get(ViolationType.class,violationType_id));
        }
        if(bnpa_id != null){
            violation.setBrokenNPA(session.get(BrokenNPA.class, bnpa_id));
        }
        violation.setViolationDescription(violDesc_engine.executeScript("getCkEditor().getData();").toString());
        violation.setNoneedcorrectionBasis(noCorrect_engine.executeScript("getCkEditor().getData();").toString());
        violation.setNoneedcorrection(GuiUtils.BooleanToInteger(noneedcorrection_cb.isSelected()));
        violation.setHavedisagreements(GuiUtils.BooleanToInteger(hasDisAgr_cb.isSelected()));
        violation.setDisagreementaccepted(GuiUtils.BooleanToInteger(disArgAccepted_cb.isSelected()));
        GuiUtils.textAndDateFieldGetUp(violation,this,Arrays.asList("violation_field", "violationCaption_field", "violationType_field"));
        submitKBK(violation);
        session.saveOrUpdate(violation);
        Questions quest = session.get(Questions.class, quest_id);
        quest.getViolationGroup().add(violation);

    }

    public BigInteger getViolationGroupId(){
        return violationGroup_id;
    }

    public void submitKBK(ViolationGroup violation){
        ObservableList<ViolationKBKTVObject> tblData = violKbK_tbl.getItems();
        ObservableList<ViolationKBKTVObject> forSynch = FXCollections.observableArrayList();
        List<ViolationGroupKBK> dbData = violation.getViolationGroupKBK();
        Session session = HibernateUtils.getCurrentSession();
        for(Iterator<ViolationGroupKBK> dbIter = dbData.iterator(); dbIter.hasNext();){
            ViolationGroupKBK dbRow = dbIter.next();
            long dbId = dbRow.getId().longValue();
            boolean needToDelete = true;
            for(Iterator<ViolationKBKTVObject> tblIter = tblData.iterator(); tblIter.hasNext();) {
                ViolationKBKTVObject tblRow = tblIter.next();
                if(tblRow.getId() == dbId){
                    dbRow.setAmount(new BigDecimal(tblRow.getAmount()));
                    dbRow.setCompensatedToAuditOrgAccount(new BigDecimal(tblRow.getAccount()));
                    dbRow.setCompensatedToAuditOrgCash(new BigDecimal(tblRow.getCash()));
                    dbRow.setCompensatedToGRBSAccount(new BigDecimal(tblRow.getGrbs()));
                    dbRow.setCompensatedToBudget(new BigDecimal(tblRow.getBudget()));
                    dbRow.setKbkDetail(session.get(KBKDetail.class,BigInteger.valueOf(tblRow.getKbkdetail_id())));
                    forSynch.add(tblRow);
                    tblIter.remove();
                    needToDelete = false;
                    break;
                }
            }
            if(needToDelete){
                session.delete(dbRow);
                dbIter.remove();
            }
        }
        if(tblData.size() > 0){
            tblData.forEach(tblRow -> {
                ViolationGroupKBK dbRow = new ViolationGroupKBK();
                dbRow.setAmount(new BigDecimal(tblRow.getAmount()));
                dbRow.setCompensatedToAuditOrgAccount(new BigDecimal(tblRow.getAccount()));
                dbRow.setCompensatedToAuditOrgCash(new BigDecimal(tblRow.getCash()));
                dbRow.setCompensatedToGRBSAccount(new BigDecimal(tblRow.getGrbs()));
                dbRow.setCompensatedToBudget(new BigDecimal(tblRow.getBudget()));
                dbRow.setKbkDetail(session.get(KBKDetail.class,BigInteger.valueOf(tblRow.getKbkdetail_id())));
                session.saveOrUpdate(dbRow);
                dbData.add(dbRow);
            });
        }
        forSynch.addAll(tblData);
        violKbK_tbl.setItems(forSynch);
    }

    public void parseDataFromReference(ObservableList dataList, String param){
        switch (param){
            case "violation":
                ViolationTVObject violTvObj = (ViolationTVObject)dataList.get(0);
                violation_field.setText(violTvObj.getCode());
                violationCaption_field.setText(violTvObj.getCaption());
                violation_id = BigInteger.valueOf(violTvObj.getId());
                tabName.setText(violTvObj.getCaption());
                break;
            case "violType":
                ViolaionTypeTVObject violTypeObj = (ViolaionTypeTVObject) dataList.get(0);
                violationType_field.setText(violTypeObj.getCaption());
                violationType_id = BigInteger.valueOf(violTypeObj.getId());
                break;
            case "bnpa":
                BrokenNpaTVObject bnpa = (BrokenNpaTVObject) dataList.get(0);
                bnpa_id = BigInteger.valueOf(bnpa.getId());
                bnpa_look_field.setText(bnpa.getLook());
                bnpa_num_field.setText(bnpa.getNum());
                LocalDate localDate = LocalDate.parse(bnpa.getDate());
                bnpa_date_field.setValue(localDate);
                bnpa_chapter_field.setText(bnpa.getChapter());
                bnpa_clause_field.setText(bnpa.getClause());
                bnpa_paragraph_field.setText(bnpa.getParag());
                bnpa_subparagraph_field.setText(bnpa.getSubParag());
                bnpa_rubric_field.setText(bnpa.getRubric());
                bnpa_caption_field.setText(bnpa.getCaption());
                break;
        }
    }
}
