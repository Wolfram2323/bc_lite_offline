package com.bftcom.gui.referenceList;

import com.bftcom.dbtools.entity.KBKDetail;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.aradForm.ViolationGroupTabController;
import com.bftcom.gui.tableViewStoreObj.DoubleStringConverter;
import com.bftcom.gui.tableViewStoreObj.KbkDetailTVObject;
import com.bftcom.gui.tableViewStoreObj.ViolationKBKTVObject;
import com.bftcom.gui.utils.Message;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class ViolationKbkNewFormController implements Initializable {
    @FXML
    private TextField amount_field;
    @FXML
    private TextField cash_field;
    @FXML
    private TextField account_field;
    @FXML
    private TextField grbs_field;
    @FXML
    private TextField budget_field;
    @FXML
    private TextField fsr_caption_field;

    private Long kbkdetail_id;

    private ViolationGroupTabController controller;

    @FXML
    private void clearKbk(ActionEvent event){
        fsr_caption_field.setText("");
        kbkdetail_id = null;
    }

    @FXML
    private void selectKbK(ActionEvent event) throws IOException {
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
    private void submitForm(ActionEvent event){
        DoubleStringConverter converter = new DoubleStringConverter();
        if(kbkdetail_id == null && amount_field.getText().isEmpty() && cash_field.getText().isEmpty() && account_field.getText().isEmpty()
                && grbs_field.getText().isEmpty() && budget_field.getText().isEmpty()){
            RuntimeException e = new RuntimeException("Необходимо заполнить хотя бы одно поле");
            Message.throwExceptionForJavaFX(e,"Ошибка заполнения формы", "Необходимо заполнить хотя бы одно поле",false);
            throw e;
        }
        ViolationKBKTVObject violKBKTV = new ViolationKBKTVObject(kbkdetail_id, fsr_caption_field.getText(), amount_field.getText(),cash_field.getText(),
                account_field.getText(),grbs_field.getText(),budget_field.getText());
        controller.addNewViolKbk(violKBKTV);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    private void closeForm(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void parseDataFromReference(ObservableList dataList, String param){
        KbkDetailTVObject kbkTvObject = (KbkDetailTVObject) dataList.get(0);
        fsr_caption_field.setText(kbkTvObject.getFsr_caption());
        kbkdetail_id = kbkTvObject.getId();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setParentController(ViolationGroupTabController controller){
        this.controller = controller;
    }
}
