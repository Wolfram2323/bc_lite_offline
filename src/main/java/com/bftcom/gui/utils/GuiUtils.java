package com.bftcom.gui.utils;

import javafx.scene.web.WebEngine;
import org.apache.commons.io.FileUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Date;
import java.util.List;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public  class GuiUtils {
    private static final String HTML_PATH = "./src/main/resources/ckeditor_4.5.4_20adb29f6738/html";
    public static void textAndDateFieldSetUp(Object entity, Object control, List<String> excludeFields){
        Field[] formFields = control.getClass().getDeclaredFields();
        for(Field field : formFields){
            if(excludeFields.contains(field.getName())){
                continue;
            }
            try {
                Class fieldClass = field.getType();
                String fieldClassName = fieldClass.getName().substring(fieldClass.getName().lastIndexOf(".") + 1);
                if(!fieldClassName.equals("TextField") && !fieldClassName.equals("DatePicker") && !fieldClassName.equals("TextArea")){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName().substring(0, field.getName().indexOf("_field")), entity.getClass());
                Object value = pd.getReadMethod().invoke(entity);
                Method method = null;
                switch(fieldClassName){
                    case "TextField":
                    case "TextArea":
                        method = fieldClass.getSuperclass().getDeclaredMethod("setText",String.class);
                        if(value == null){
                            value="";
                        }else {
                            value = ((String) value).trim();
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
                    field.setAccessible(true);
                    method.invoke(field.get(control),value);
                }
            } catch (IllegalAccessException|InvocationTargetException |IntrospectionException |NoSuchMethodException e) {
                Message.throwExceptionForJavaFX(e,"Загрузка электронного документа завершилась с ошибкой",null,true);
            }

        }
    }

    public static void textAndDateFieldGetUp(Object entity, Object control, List<String> excludeFields){
        Field[] formFields = control.getClass().getDeclaredFields();
        for(Field field : formFields){
            if(excludeFields.contains(field.getName())){
                continue;
            }
            try {
                Class fieldClass = field.getType();
                String fieldClassName = fieldClass.getName().substring(fieldClass.getName().lastIndexOf(".") + 1);
                if(!fieldClassName.equals("TextField") && !fieldClassName.equals("DatePicker") && !fieldClassName.equals("TextArea")){
                    continue;
                }
                Method method = null;
                switch(fieldClassName){
                    case "TextField":
                    case "TextArea":
                        method = fieldClass.getSuperclass().getDeclaredMethod("getText");
                        break;
                    case  "DatePicker":
                        method = fieldClass.getSuperclass().getDeclaredMethod("getValue");
                        break;
                }
                if(method != null){
                    method.setAccessible(true);
                    field.setAccessible(true);
                    Object value = method.invoke(field.get(control));
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName().substring(0, field.getName().indexOf("_field")), entity.getClass());
                    pd.getWriteMethod().invoke(entity,value);
                }
            } catch (IllegalAccessException|InvocationTargetException |IntrospectionException |NoSuchMethodException e) {
                Message.throwExceptionForJavaFX(e,"Загрузка электронного документа завершилась с ошибкой",null,true);
            }

        }
    }

    public static void loadCkEditorToWebView(WebEngine engine, String resourseName){
        URL url = GuiUtils.class.getResource("/ckeditor_4.5.4_20adb29f6738/ckEditor.html");
        File html = new File(HTML_PATH + "/" + resourseName + ".html");
        try {
            FileUtils.copyURLToFile(url, html);
            engine.load(html.toURI().toURL().toExternalForm());
        } catch (IOException e) {
            e.printStackTrace();
            Message.throwExceptionForJavaFX(e, "Ошибка при загрузке редактора текста", null, true);
        }

    }

    public  static void cleanHtmlDir(){
        try {
            FileUtils.cleanDirectory(new File(HTML_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean IntegerToBoolean(Integer value){
        return  value!= 0 ;
    }

    public static Integer BooleanToInteger(boolean value){
        if(value){
            return 1;
        } else {
            return 0;
        }
    }
}
