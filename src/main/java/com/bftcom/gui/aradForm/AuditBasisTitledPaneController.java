package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.gui.exception.ExceptionMessage;
import com.bftcom.gui.utils.GuiUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class AuditBasisTitledPaneController extends AbstractBftTitledPaneController {

    @FXML
    private TextField auditBasis_field;
    @FXML
    private TextField auditBasis_type_field;
    @FXML
    private  TextField auditBasis_number_field;
    @FXML
    private DatePicker auditBasis_date_field;



    public AuditBasisTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/auditBasisTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ExceptionMessage.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @Override
    public void initialize(ActResultsAuditDoc arad) {
        GuiUtils.textAndDateFieldSetUp(arad,this, new ArrayList<>());
    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {

    }
}
