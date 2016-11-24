package com.bftcom.gui.custom.fields;

import javafx.scene.control.TextField;

/**
 * Created by k.nikitin on 24.11.2016.
 */
public class NumberTextField extends TextField {
    public NumberTextField(){
        this.setPromptText("Только числа");
    }

    public NumberTextField(String str){
        super(str);
    }

    @Override
    public void replaceText(int i, int il, String str){
        if(str.matches("[0-9]") || str.isEmpty()) {
            super.replaceText(i,il,str);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
