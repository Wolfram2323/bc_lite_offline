package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.gui.Customizable;
import com.bftcom.gui.utils.Message;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;

import java.io.IOException;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public class QuestionsTitledPaneController extends AbstractBftTitledPaneController implements Customizable {
    @FXML
    private TabPane question_TabPane;

    public QuestionsTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/questionsTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            Message.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @Override
    public void initialize(ActResultsAuditDoc arad) {
        processCustom();
        question_TabPane.getStyleClass().add("vTabPane");
        arad.getQuestions().forEach(row->{
            QuestTabController questController = new QuestTabController();
            question_TabPane.getTabs().add(questController);
            questController.initialize(row);
        });
    }

    @Override
    public void submitData(ActResultsAuditDoc arad) {
        question_TabPane.getTabs().forEach(tab -> ((AbstractBftTabController)tab).submitData(arad));
    }
}
