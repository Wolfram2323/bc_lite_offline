package com.bftcom.dbtools.utils;

import com.bftcom.dbtools.entity.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by k.nikitin on 27.09.2016.
 */
public class HibernateUtils {
    private static final Logger log = LoggerFactory.getLogger(HibernateUtils.class);
    private static SessionFactory sessionFactory;
    private static Configuration cfg;
    private static List<Class> annotatedClasses = new ArrayList<>();


    public static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_HIBER_DIALECT = "org.hibernate.dialect.DerbyTenSixDialect";
    public static final String DERBY_DB_URL = "jdbc:derby:bcOffLineDB";
    public static final String DERBY_USER_NAME = "bc";
    public static final String DERBY_PSWD = "sysdba";

    static{

        cfg = new Configuration();
        cfg.setProperty("hibernate.dialect",DERBY_HIBER_DIALECT);
        cfg.setProperty("hibernate.connection.driver_class",DERBY_DRIVER_CLASS);
        cfg.setProperty("hibernate.connection.url",DERBY_DB_URL);
        cfg.setProperty("hibernate.connection.username", DERBY_USER_NAME);
        cfg.setProperty("hibernate.connection.password", DERBY_PSWD);
        cfg.setProperty("hibernate.hbm2ddl.auto","update");
        cfg.addAnnotatedClass(ActResultsAuditDoc.class)
           .addAnnotatedClass(Appointment.class)
           .addAnnotatedClass(BrokenNPA.class)
           .addAnnotatedClass(Employee.class)
           .addAnnotatedClass(EmployeeGroup.class)
           .addAnnotatedClass(KBKDetail.class)
           .addAnnotatedClass(Questions.class)
           .addAnnotatedClass(SysUser.class)
           .addAnnotatedClass(Violation.class)
           .addAnnotatedClass(ViolationGroup.class)
           .addAnnotatedClass(ViolationGroupKBK.class)
           .addAnnotatedClass(ViolationType.class);
        annotatedClasses = Arrays.asList(ActResultsAuditDoc.class, Appointment.class, BrokenNPA.class, Employee.class, EmployeeGroup.class, KBKDetail.class,
                Questions.class, SysUser.class, Violation.class, ViolationGroup.class, ViolationGroupKBK.class, ViolationType.class);
    }

    public static Session getSession(Boolean rebuildFactory){
        if(sessionFactory == null || rebuildFactory){
            sessionFactory = cfg.buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static List<Class> getAnnotatedClasses (){
        return annotatedClasses;
    }


}
