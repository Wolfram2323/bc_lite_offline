package com.bftcom.context;

import org.hibernate.Session;

import java.math.BigInteger;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class Context {

    private Session session;
    private static Context instance;
    private boolean isAdmin;
    private BigInteger user_id;


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

    public BigInteger getUser_id() {
        return user_id;
    }

    public void setUser_id(BigInteger user_id) {
        this.user_id = user_id;
    }
}
