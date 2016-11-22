package com.bftcom.gui.utils;

import com.bftcom.context.Context;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.GuiUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class Message {
    public static void  throwExceptionForJavaFX(Throwable e,String headerText, String contentText, boolean fatal ){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Произошла ошибка");
        alert.setHeaderText(headerText == null ? "" : headerText);
        alert.setContentText(contentText == null ? "" : contentText);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && fatal){
            if(Context.getCurrentContext().getSession() != null){
                HibernateUtils.getCurrentSession().getTransaction().rollback();
                HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
            }
            GuiUtils.cleanHtmlDir();
            System.exit(0);
        }
    }

    public static void showInfoMessage(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
