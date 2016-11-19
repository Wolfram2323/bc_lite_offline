package com.bftcom.gui.loginForm;

import com.bftcom.context.OfflineVersion;
import com.bftcom.gui.exception.ExceptionMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by k.nikitin on 03.11.2016.
 */
public class LoginForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        OfflineVersion version = new OfflineVersion();
        findDerby(version);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginForm.fxml"));
        root.getStylesheets().add("/css/styles.css");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Авторизация");
        primaryStage.show();
    }

    public void findDerby(OfflineVersion version){
        File home = new File("./offlineDataBase");
        File[] resultSearch = home.listFiles((pathname, fileName) -> {
            return fileName.startsWith("derby_");
        });
        if(resultSearch.length == 0){
            RuntimeException e = new RuntimeException("База данных не найдена!");
            e.printStackTrace();
            ExceptionMessage.throwExceptionForJavaFX(e, "Не удалось найти базу данных для подключения", "Скопируйтее базу данных, полученную с онлайн клиента " +
                    "в корневую директорию оффлайн клиента", true);

        }
        for(File derbyDir : resultSearch) {
            String derbyName = derbyDir.getName();
            if(derbyName.substring("derby_".length()).equals(version.toString())){{
                System.setProperty("derby.system.home", derbyDir.getAbsolutePath());
                return;
            }
        }
            RuntimeException e = new RuntimeException("База данных не найдена!");
            e.printStackTrace();
            ExceptionMessage.throwExceptionForJavaFX(e, "Не удалось найти базу данных для подключения требуемой версии", "Скопируйтее базу данных версии " + version.toString()
                    + ", полученную с онлайн клиента в корневую директорию оффлайн клиента", true);
    }

    }

}
