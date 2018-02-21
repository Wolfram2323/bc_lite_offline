package com.bftcom.customizator;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Created by k.nikitin on 14.02.2018.
 */
public class Form {
    @JacksonXmlProperty(localName = "controlClass", isAttribute = true)
    private String controlClass;
    @JacksonXmlProperty(localName = "control")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Control> controls;

    public Form() {
    }

    public Form(String controlClass, List<Control> controls) {
        this.controlClass = controlClass;
        this.controls = controls;
    }

    public String getControlClass() {
        return controlClass;
    }

    public void setControlClass(String controlClass) {
        this.controlClass = controlClass;
    }

    public List<Control> getControls() {
        return controls;
    }

    public void setControls(List<Control> controls) {
        this.controls = controls;
    }
}
