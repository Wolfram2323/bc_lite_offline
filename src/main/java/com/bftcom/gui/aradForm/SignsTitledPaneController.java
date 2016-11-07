package com.bftcom.gui.aradForm;

import com.bftcom.gui.exception.ExceptionMessage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;

import java.io.IOException;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class SignsTitledPaneController extends TitledPane {

    public SignsTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/signsTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ExceptionMessage.throwExceptionForJavaFX(e,null,null,true);
        }
    }
}
