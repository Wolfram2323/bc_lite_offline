package com.bftcom.customizator;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * Created by k.nikitin on 14.02.2018.
 */
@JacksonXmlRootElement(localName = "custom")
public class Custom {
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    private String name;
    @JacksonXmlProperty(localName = "form")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Form> forms;

    public Custom() {
    }

    public Custom(String name, List<Form> forms) {
        this.name = name;
        this.forms = forms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public Form findForm(String controlClass){
        return forms.stream().filter(form -> form.getControlClass().equals(controlClass)).findFirst().orElse(null);
    }
}
