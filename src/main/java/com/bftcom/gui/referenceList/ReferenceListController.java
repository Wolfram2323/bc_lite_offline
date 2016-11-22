package com.bftcom.gui.referenceList;

import com.bftcom.gui.utils.Message;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by k.nikitin on 13.11.2016.
 */
public class ReferenceListController implements Initializable {
    @FXML
    private TableView table;

    private Object controller;

    private String param;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void createTable(List<?> entityList, List<TableColumn> columnList, Class tvObject, Object controller, String param){
        this.controller = controller;
        this.param = param;
        if(columnList.size() > 0){
            columnList.forEach(table.getColumns()::add);
        }
        if(entityList.size() > 0){
            entityList.forEach(entity->{
                try {
                    Constructor constructor = tvObject.getConstructor(entity.getClass());
                    table.getItems().add(constructor.newInstance(entity));
                } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e) {
                    e.printStackTrace();
                    Message.throwExceptionForJavaFX(e,"Произошла ошибка загрузки справочника", null,false);
                }

            });
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void select(ActionEvent event){
        try {
            Method method = controller.getClass().getDeclaredMethod("parseDataFromReference", ObservableList.class, String.class);
            method.invoke(controller, table.getSelectionModel().getSelectedItems(), param);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
