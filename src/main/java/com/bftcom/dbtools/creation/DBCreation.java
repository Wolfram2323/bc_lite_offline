package com.bftcom.dbtools.creation;

import com.bftcom.dbtools.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by k.nikitin on 22.09.2016.
 */
public class DBCreation {

    Logger log = LoggerFactory.getLogger(DBCreation.class);

    private static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DERBY_HIBER_DIALECT = "org.hibernate.dialect.DerbyTenSixDialect";
    public static final String DERBY_DB_URL = "jdbc:derby:bcOffLineDB";
    public static final String DERBY_USER_NAME = "bc";
    public static final String DERBY_PSWD = "sysdba";

    private Configuration cfg;

    static{
        System.setProperty("derby.system.home","./derby");
    }

    private void init() {
        try{
            Class.forName(DERBY_DRIVER_CLASS).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException |InstantiationException e){
            log.error("Не найден драйвер для подключение к базе данных");
            throw new RuntimeException(e);
        }
        cfg = new Configuration();
        cfg.setProperty("hibernate.dialect",DERBY_HIBER_DIALECT);
        cfg.setProperty("hibernate.connection.driver_class",DERBY_DRIVER_CLASS);
        cfg.setProperty("hibernate.connection.url",DERBY_DB_URL);
        cfg.setProperty("hibernate.connection.username", DERBY_USER_NAME);
        cfg.setProperty("hibernate.connection.password", DERBY_PSWD);
        cfg.setProperty("hibernate.hbm2ddl.auto","update");
        cfg.addAnnotatedClass(ActResultsAuditDoc.class);
        cfg.addAnnotatedClass(Appointment.class);
        cfg.addAnnotatedClass(BrokenNPA.class);
        cfg.addAnnotatedClass(Employee.class);
        cfg.addAnnotatedClass(EmployeeGroup.class);
        cfg.addAnnotatedClass(KBKDetail.class);
        cfg.addAnnotatedClass(Questions.class);
        cfg.addAnnotatedClass(SysUser.class);
        cfg.addAnnotatedClass(Violation.class);
        cfg.addAnnotatedClass(ViolationGroup.class);
        cfg.addAnnotatedClass(ViolationGroupKBK.class);
        cfg.addAnnotatedClass(ViolationType.class);


    }

    public static DBCreation newInstance(){
        DBCreation dbCreation = new DBCreation();
        dbCreation.init();
        return dbCreation;
    }

    public void createDB(){
        try {
            DriverManager.getConnection(DERBY_DB_URL+";create=true;user="+DERBY_USER_NAME+";password="+DERBY_PSWD);
            SessionFactory sessionFactory = cfg.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.close();
            DriverManager.getConnection(DERBY_DB_URL+"; shutdown=true");
        } catch (SQLException e){
            if(e.getSQLState().equals("08006")){
                log.info("Подключение к базе данных разорвано");
            } else {
                log.error("Не удалось создать базу данных");
                throw new RuntimeException(e);
            }
        }
    }


}
