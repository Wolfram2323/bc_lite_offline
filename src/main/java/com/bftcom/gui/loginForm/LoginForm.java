package com.bftcom.gui.loginForm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by k.nikitin on 03.11.2016.
 */
public class LoginForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginForm.fxml"));
        root.getStylesheets().add("/css/styles.css");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Авторизация");
        primaryStage.show();
    }

}
