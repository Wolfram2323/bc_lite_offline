package com.bftcom.dbtools.utils;

import com.bftcom.context.Context;
import com.bftcom.dbtools.entity.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 27.09.2016.
 */
public class HibernateUtils {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory sessionFactory;
    private static Configuration cfg;
    private static List<Class> annotatedClasses = new LinkedList<>();


    public static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_HIBER_DIALECT = "org.hibernate.dialect.DerbyTenSixDialect";
    public static final String DERBY_DB_URL = "jdbc:derby:bcOffLineDB";
    public static final String DERBY_USER_NAME = "bc";
    public static final String DERBY_PSWD = "sysdba";

    private static void generateConfiguration(String url) {

        cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", DERBY_HIBER_DIALECT);
        cfg.setProperty("hibernate.connection.driver_class", DERBY_DRIVER_CLASS);
        if (url != null) {
            cfg.setProperty("hibernate.connection.url", url);
        } else {
            cfg.setProperty("hibernate.connection.url", DERBY_DB_URL);
        }
        cfg.setProperty("hibernate.connection.username", DERBY_USER_NAME);
        cfg.setProperty("hibernate.connection.password", DERBY_PSWD);
        cfg.setProperty("hibernate.hbm2ddl.auto", "update");
        cfg.setProperty("hibernate.c3p0.min_size", String.valueOf(5));
        cfg.setProperty("hibernate.c3p0.max_size", String.valueOf(20));
        cfg.setProperty("hibernate.c3p0.timeout", String.valueOf(300));
        cfg.setProperty("hibernate.c3p0.max_statements", String.valueOf(50));
        cfg.setProperty("hibernate.c3p0.idle_test_period", String.valueOf(3000));
        cfg.addAnnotatedClass(ActResultsAuditDoc.class)
                .addAnnotatedClass(ARADInspectors.class)
                .addAnnotatedClass(Appointment.class)
                .addAnnotatedClass(BrokenNPA.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(EmployeeGroup.class)
                .addAnnotatedClass(ARADkbk.class)
                .addAnnotatedClass(KBKDetail.class)
                .addAnnotatedClass(Questions.class)
                .addAnnotatedClass(SysUser.class)
                .addAnnotatedClass(Violation.class)
                .addAnnotatedClass(ViolationGroup.class)
                .addAnnotatedClass(ViolationGroupKBK.class)
                .addAnnotatedClass(ViolationType.class);
    }
    static {
        annotatedClasses = Arrays.asList(Appointment.class, BrokenNPA.class, KBKDetail.class,  Violation.class, ViolationType.class,
                 SysUser.class, Employee.class, EmployeeGroup.class, ActResultsAuditDoc.class, Questions.class, ARADInspectors.class, ARADkbk.class, ViolationGroup.class, ViolationGroupKBK.class);
    }

    public static Session getSession(String url){
        if(cfg == null || url != null){
            generateConfiguration(url);
        }

        if(sessionFactory == null || url != null){
            sessionFactory = cfg.buildSessionFactory();
        }
        Context con = Context.getCurrentContext();
        con.setSession(sessionFactory.openSession());
        return con.getSession();
    }

    public static Session getCurrentSession(){
        Context con = Context.getCurrentContext();
        if(con.getSession()  == null ){
            return getSession(null);
        }
        return con.getSession();
    }

    public static void closeSession(Session session){
        session.close();
        Context con = Context.getCurrentContext();
        con.setSession(null);
    }

    public static void closeConnection(Session session){
        SessionFactory sf = session.getSessionFactory();
        session.close();
        sf.close();
        Context con = Context.getCurrentContext();
        con.setSession(null);
    }

    public static void saveEntity(Session session,Object entity ){
        session.getTransaction().begin();
        try{
            session.saveOrUpdate(entity);
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("SysUser creation faild!");
            session.getTransaction().rollback();
        }
    }

    public static List<Class> getAnnotatedClasses (){
        return annotatedClasses;
    }


}
