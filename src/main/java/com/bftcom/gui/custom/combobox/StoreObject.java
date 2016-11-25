package com.bftcom.gui.custom.combobox;

import java.math.BigInteger;

/**
 * Created by k.nikitin on 24.11.2016.
 */
public class StoreObject {
    private BigInteger id;
    private String caption;

    public  StoreObject(BigInteger id, String caption){
        this.id = id;
        this.caption = caption;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
