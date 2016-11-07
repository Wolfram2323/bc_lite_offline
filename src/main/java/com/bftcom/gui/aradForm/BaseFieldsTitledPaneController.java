package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.gui.exception.ExceptionMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebView;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by k.nikitin on 06.11.2016.
 */
public class BaseFieldsTitledPaneController extends TitledPane {

    @FXML
    private TextField docNumber_field;
    @FXML
    private DatePicker docDate_field;
    @FXML
    private TextField casenumber_field;
    @FXML
    private DatePicker approvedate_field;
    @FXML
    private TextField actSigning_field;
    @FXML
    private TextField auditOrg_caption_field;
    @FXML
    private TextField controlform_field;
    @FXML
    private TextField auditTheme_field;
    @FXML
    private TextField controlOrg_caption_field;
    @FXML
    private TextField territory_field;
    @FXML
    private DatePicker auditFrom_field;
    @FXML
    private DatePicker auditTo_field;
    @FXML
    private DatePicker actualExecFrom_field;
    @FXML
    private DatePicker actualExecTo_field;
//    @FXML
//    private WebView obstacle_field;

    public BaseFieldsTitledPaneController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/baseFieldsTitledPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            ExceptionMessage.throwExceptionForJavaFX(e,null,null,true);
        }
    }

    @SuppressWarnings("unchecked")
    public void initialize(ActResultsAuditDoc arad){
        Field[] formFields = getClass().getDeclaredFields();
        for(Field field : formFields){
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName().substring(0, field.getName().indexOf("_field")), arad.getClass());
                Object value = pd.getReadMethod().invoke(arad);
                Class fieldClass = field.getType();
                String fieldClassName = fieldClass.getName().substring(fieldClass.getName().lastIndexOf(".") + 1);
                Method method = null;
                switch(fieldClassName){
                    case "TextField":
                        method = fieldClass.getSuperclass().getDeclaredMethod("setText",String.class);
                        if(value == null){
                            value="";
                        }
                        break;
                    case  "DatePicker":
                        method = fieldClass.getSuperclass().getDeclaredMethod("setValue",Object.class);
                        if(value == null){
                            continue;
                        }
                        value = ((Date)value).toLocalDate();
                        break;
                }
                if(method != null){
                    method.setAccessible(true);
                    method.invoke(field.get(this),value);
                }
            } catch (IllegalAccessException|InvocationTargetException|IntrospectionException|NoSuchMethodException e) {
                ExceptionMessage.throwExceptionForJavaFX(e,"Загрузка электронного документа завершилась с ошибкой",null,true);
            }

        }
    }
}
