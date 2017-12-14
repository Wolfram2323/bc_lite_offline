package com.bftcom.gui.loginForm;


import com.bftcom.context.OfflineVersion;
import com.bftcom.gui.utils.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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

    public void findDerby(OfflineVersion version) {
        try {
            Stream<Path> derbyDirs = Files.find(Paths.get("./"), 6, ((path, attr) -> path.getFileName().toString().equals("derby_" + version.toString())));
            Path derbyDir = derbyDirs.findFirst().orElseThrow(() -> {
                RuntimeException e = new RuntimeException("База данных не найдена!");
                e.printStackTrace();
                Message.throwExceptionForJavaFX(e, "Не удалось найти базу данных для подключения", "Скопируйтее базу данных, полученную с онлайн клиента " +
                        "в корневую директорию оффлайн клиента", true);
                return e;
            });

            System.setProperty("derby.system.home", derbyDir.toAbsolutePath().toString());

        } catch (IOException e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Не удалось найти базу данных для подключения. Ошибка при поиске.", "Скопируйтее базу данных, полученную с онлайн клиента " +
                    "в корневую директорию оффлайн клиента", true);
        }
    }

}
