package com.bftcom.context;


import com.bftcom.customizator.Custom;
import org.hibernate.Session;


import java.math.BigInteger;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public class Context {

    private Session session;
    private boolean isAdmin;
    private Customization cust;
    private Custom custom;
    private BigInteger user_id;


    private static class LazyHolder{
        private static final Context INSTANCE = new Context();
    }

    public static synchronized Context getCurrentContext() {
        return LazyHolder.INSTANCE;
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

    public Customization getCust() {
        return cust;
    }

    public void setCust(Customization cust) {
        this.cust = cust;
    }

    public Custom getCustom() {
        return custom;
    }

    public void setCustom(Custom custom) {
        this.custom = custom;
    }
}
