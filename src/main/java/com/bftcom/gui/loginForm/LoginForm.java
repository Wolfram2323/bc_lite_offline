package com.bftcom.gui.loginForm;


import com.bftcom.context.Context;
import com.bftcom.context.Customization;
import com.bftcom.context.OfflineVersion;
import com.bftcom.customizator.Action;
import com.bftcom.customizator.ActionDeserializer;
import com.bftcom.customizator.Custom;
import com.bftcom.dbtools.entity.SystemParameters;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.utils.Message;
import com.bftcom.gui.utils.PropertiesAndParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by k.nikitin on 03.11.2016.
 */
public class LoginForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        OfflineVersion version = new OfflineVersion();
        findDerby(version);
        loadCustomization();
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

    private void loadCustomization() throws IOException {
        try (Session session = HibernateUtils.getCurrentSession()) {
            Query<SystemParameters> paramQuery = session.createQuery(" FROM SystemParameters where name = :name", SystemParameters.class).setParameter("name", PropertiesAndParameters.CUSTOMIZATION.toString());
            List<SystemParameters> params = paramQuery.getResultList();
            Context con = Context.getCurrentContext();
            if (params.isEmpty()) {
                con.setCust(Customization.find(""));
            } else {
                Customization cust = Customization.find(params.get(0).getValue());
                con.setCust(cust);
                ObjectMapper mapper = new XmlMapper();
                SimpleModule module = new SimpleModule();
                module.addDeserializer(Action.class, new ActionDeserializer());
                mapper.registerModule(module);
                Custom custom = mapper.readValue(getClass()
                        .getResource("/customizator/customizator_" + cust.toString().toLowerCase() + ".xml"), Custom.class);
                con.setCustom(custom);
            }

        }
    }

}
