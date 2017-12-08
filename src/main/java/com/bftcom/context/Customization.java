package com.bftcom.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by k.nikitin on 29.11.2017.
 */
public enum Customization {
    NONE(""),
    MON("bft.audit.mon"),
    TYUMEN("bft.audit.tyumen");

    private String module;

    private static class Holder {
        private static Map<String,Customization> MAP = new HashMap<>();
    }

    Customization(String module){
        this.module = module;
        Holder.MAP.put(module, this);
    }

    public String getModule() {
        return module;
    }

    public static Customization find(String module){
        Customization cust = Holder.MAP.get(module);
        if(cust == null){
            throw new IllegalStateException("Can't recognize customization name");
        }
        return cust;
    }
}
