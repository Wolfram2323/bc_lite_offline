package com.bftcom.context;

import org.hibernate.Session;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class Context {

    private Session session;
    private static Context instance;

    public static Context getCurrentContext() {
        if(instance == null) {
            return new Context();
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
