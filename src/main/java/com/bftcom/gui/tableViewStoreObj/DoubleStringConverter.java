package com.bftcom.gui.tableViewStoreObj;

import javafx.util.StringConverter;

/**
 * Created by k.nikitin on 12.11.2016.
 */
public class DoubleStringConverter extends StringConverter<Double> {
    @Override
    public String toString(Double object) {
        if(object == null){
            return "";
        }
        return object.toString();
    }

    @Override
    public Double fromString(String string) {
        if(string == null || string.isEmpty()){
            return null;
        }
        try{
            return Double.parseDouble(string);
        } catch (NumberFormatException e){
            return Double.NaN;
        }

    }

}
