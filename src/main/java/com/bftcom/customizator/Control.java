package com.bftcom.customizator;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Created by k.nikitin on 14.02.2018.
 */
public class Control {
    @JacksonXmlProperty(localName = "fxid", isAttribute = true)
    private String fxid;
    @JacksonXmlProperty(localName = "action")
    @JacksonXmlElementWrapper(localName = "action", useWrapping = false)
    private List<Action> actions;

    public Control() {
    }

    public Control(String fxid, List<Action> actions) {
        this.fxid = fxid;
        this.actions = actions;
    }

    public String getFxid() {
        return fxid;
    }

    public void setFxid(String fxid) {
        this.fxid = fxid;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
