package com.bftcom.customizator;

import com.bftcom.gui.Customizable;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k.nikitin on 14.02.2018.
 */
public class Action {
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;


    @JacksonXmlProperty(localName = "params", isAttribute = true)
    private Map<String, Object> params = new HashMap<>();


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void process(Object control, Customizable controller){
        params.put("controller", controller);
        Actions.valueOf(this.getType().toUpperCase()).accept(control,this.getParams());
    }
}
