package com.bftcom.context;

import org.hibernate.Session;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class Context {

    private Session session;
    private static Context instance;
    private boolean isAdmin;


    public static synchronized Context getCurrentContext() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public  boolean isAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}
