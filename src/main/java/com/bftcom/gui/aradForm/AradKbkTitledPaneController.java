package com.bftcom.gui.aradForm;

import com.bftcom.gui.exception.ExceptionMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;

import java.io.IOException;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class AradKbkTitledPaneController extends TitledPane {
    @FXML
    private TableView aradKbk_tbl;

    public AradKbkTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/auditedKbkTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ExceptionMessage.throwExceptionForJavaFX(e,null,null,true);
        }
    }


}
