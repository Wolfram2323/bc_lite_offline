package com.bftcom.gui.loginForm;

import com.bftcom.context.Context;
import com.bftcom.context.Customization;
import com.bftcom.dbtools.entity.SystemParameters;
import com.bftcom.dbtools.utils.AuthentificationUtils;
import com.bftcom.dbtools.utils.HibernateUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by k.nikitin on 05.11.2016.
 */
public class LoginFormController implements Initializable {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField pswdField;
    @FXML
    private Label messageLbl;
    @FXML
    private Button loginBtn;

    private Session session;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session = HibernateUtils.getCurrentSession();
        Query<SystemParameters> paramQuery = session.createQuery(" FROM SystemParameters where name = :name", SystemParameters.class).setParameter("name", "customization");
        List<SystemParameters> params = paramQuery.getResultList();
        if(params.isEmpty()){
            Context.getCurrentContext().setCust(Customization.find(""));
        } else {
            Context.getCurrentContext().setCust(Customization.find(params.get(0).getValue()));
        }
        loginBtn.setDefaultButton(true);
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException{
        if(userNameField.getText().isEmpty()){
            userNameField.getStyleClass().add("validation-error");
            messageLbl.setText("Введите логин");
            return;
        }
        if(AuthentificationUtils.confirmAthorizate(userNameField.getText(),pswdField.getText(), session)){
            Parent parent = FXMLLoader.load(getClass().getResource("/fxml/aradForm.fxml"));
            ((Node)event.getSource()).getScene().getWindow().hide();
            parent.getStylesheets().add("/css/styles.css");
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Электронный документ Акт проверки");
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Выход из системы");
                    alert.setHeaderText("Вы действительно хотите завершить работу с ситемой?");
                    alert.setContentText("В случае завершения работы, все несохраненные данные пропадут.");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.CANCEL){
                        we.consume();
                    }
                    HibernateUtils.closeConnection(HibernateUtils.getCurrentSession());
                }
            });
            stage.show();
        } else{
            pswdField.setText("");
            messageLbl.setText("Неверный логин или пароль.");
        }


    }
}
