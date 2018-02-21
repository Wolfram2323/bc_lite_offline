package com.bftcom.gui;

import com.bftcom.context.Context;
import com.bftcom.customizator.Control;
import com.bftcom.customizator.Custom;
import com.bftcom.customizator.Form;
import javafx.fxml.Initializable;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by k.nikitin on 15.02.2018.
 */
public interface Customizable  {

    default public void processCustom() {
        Context con = Context.getCurrentContext();
        Custom custom = con.getCustom();
        Form form = custom.findForm(this.getClass().getSimpleName());
        if (form != null) {
            List<Control> controls = form.getControls();
            for (Control control : controls) {
                try {
                    Field field = this.getClass().getDeclaredField(control.getFxid());
                    field.setAccessible(true);
                    Object controlObj = field.get(this);
                    control.getActions().stream().forEach(action -> action.process(controlObj, this));

                } catch (NoSuchFieldException |IllegalAccessException e) {

                }
            }


        }


    }

}
