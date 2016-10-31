package com.bftcom.dbtools.creation;


import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;
import com.bftcom.dbtools.entity.SysUser;
import com.bftcom.dbtools.utils.HibernateUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.hibernate.HibernateException;
import org.hibernate.QueryException;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.persistence.*;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by k.nikitin on 22.09.2016.
 */
public class DBCreation {

    private static final Logger log = LoggerFactory.getLogger(DBCreation.class);
    static EmbeddedDriver driver;


    private static EmbeddedDriver initJdbcDriver() {
        try{
            log.info("Derby driver initialization!");
            EmbeddedDriver driver = (EmbeddedDriver)Class.forName(HibernateUtils.DERBY_DRIVER_CLASS).newInstance();
            DriverManager.registerDriver(driver);
            return  driver;
        } catch (ClassNotFoundException | IllegalAccessException |InstantiationException |SQLException e){
            log.error("Derby jdbc driver not found or there was error during initialization!");
            throw new RuntimeException(e);
        }
    }


    public static void createDB(){
        driver = initJdbcDriver();
        try {
            log.info("DataBase creation started!");
            driver.connect(HibernateUtils.DERBY_DB_URL+";create=true;user="+HibernateUtils.DERBY_USER_NAME
                    +";password="+HibernateUtils.DERBY_PSWD, new Properties());
            log.info("Data base schema creation started!");
            Session session = HibernateUtils.getSession(null);
            HibernateUtils.closeConnection(session);


            log.info("DataBase successfully created!");

        } catch (SQLException | HibernateException e){
            log.error("DabaBase creation failed! See stack trace for more information!");
            e.printStackTrace();

        } finally {
           try{
               driver.connect(HibernateUtils.DERBY_DB_URL+"; shutdown=true", new Properties());
           } catch (SQLException e){
               if(e.getSQLState().equals("08006")){
                   log.info("DataBase connection is shutdown");
               } else {
                   log.error("DabaBase creation failed! See stack trace for more information!");
               }

           }
        }
    }

    public static Map<String,String> getUploadSql(){
        Map<String,String> result = new ConcurrentHashMap<>();
        List<Class> annotatedClasses = HibernateUtils.getAnnotatedClasses();
        annotatedClasses.stream().filter(entity-> !((Table)entity.getAnnotation(Table.class)).name().matches("SYSUSER|VIOLATIONGROUP|VIOLATIONGROUPKBK")).parallel().forEach(entity->{
            String tableName = ((Table)entity.getAnnotation(Table.class)).name();
            OnLineJoin onLineJoin = (OnLineJoin) entity.getAnnotation(OnLineJoin.class);
            Field[] fields = entity.getDeclaredFields();
            StringBuilder select = new StringBuilder("select ");
            select.append(tableName).append(".ID");
            StringBuilder from = new StringBuilder();
            from.append(" from ").append(tableName);
            Map<String, String> columns = new TreeMap<>();
            Map<String, String> links = new TreeMap<>();
            for(Field field: fields){
                if(field.getName().equals("id")){
                    continue;
                }
                OnLineColumnInfo onlineColInfo = field.getAnnotation(OnLineColumnInfo.class);
                if(onlineColInfo == null){
                    continue;
                }
                if(onlineColInfo.columnName().isEmpty()){
                    if(onlineColInfo.joinAlias().isEmpty()){
                        columns.put(field.getAnnotation(Column.class).name(), tableName + '.' + field.getAnnotation(Column.class).name());
                    } else {
                        columns.put(field.getAnnotation(Column.class).name(), onlineColInfo.joinAlias() + '.' + field.getAnnotation(Column.class).name());
                    }
                } else if(field.getAnnotation(JoinColumn.class)!= null){
                    mapSqlFields(links,onlineColInfo,tableName, field);
                } else {
                    mapSqlFields(columns, onlineColInfo, tableName, field);
                }
            }
            if(columns.size() > 0){
                select.append(", ").append(String.join(", ",columns.values()));
            }
            if(links.size() > 0){
                select.append(", ").append(String.join(", ",links.values()));
            }
            if(onLineJoin!=null && !onLineJoin.sqlExpression().isEmpty()){
                from.append(((OnLineJoin)entity.getAnnotation(OnLineJoin.class)).sqlExpression());
            }
            result.put(tableName,select.append(from).toString());

        });
        return result;
    }

    protected static void mapSqlFields(Map<String,String> map, OnLineColumnInfo onlineColInfo, String tableName, Field field){
        Column colAnn = field.getAnnotation(Column.class);
        if(onlineColInfo.joinAlias().isEmpty()){
            map.put(colAnn != null ? colAnn.name() : onlineColInfo.columnName(),tableName + '.' + onlineColInfo.columnName());
        } else {
            map.put(colAnn != null ? colAnn.name() : onlineColInfo.columnName(), onlineColInfo.joinAlias() + '.' + onlineColInfo.columnName());
        }
    }

    public static void importCsvDataFiles(File csvFilesDir, String url){
        log.info("Obtain session");
        try(Session session = HibernateUtils.getSession(url)){
            List<Class> annotatedClasses = HibernateUtils.getAnnotatedClasses();
            List<File> csvFiles = (List<File>) FileUtils.listFiles(csvFilesDir, new String[] {"csv"}, false);
            annotatedClasses.stream().filter(entity-> !((Table)entity.getAnnotation(Table.class)).name().matches("SYSUSER|VIOLATIONGROUP|VIOLATIONGROUPKBK")).forEach(entity->{
                String tableName = ((Table)entity.getAnnotation(Table.class)).name();
                File csvFile = findFileFromList(csvFiles,tableName);
                if(csvFile == null){
                    throw new RuntimeException("Require csv file not found!");
                }
                ProcedureCall procedure = session.createStoredProcedureCall("SYSCS_UTIL.SYSCS_IMPORT_TABLE");
                log.info("Create procedure");
                procedure.registerParameter("SCHEMANAME",String.class, ParameterMode.IN);
                procedure.registerParameter("TABLENAME",String.class, ParameterMode.IN);
                procedure.registerParameter("FILENAME",String.class, ParameterMode.IN);
                procedure.registerParameter("COLUMNDELIMITER",Character.class, ParameterMode.IN);
                procedure.registerParameter("CHARACTERDELIMITER",Character.class, ParameterMode.IN);
                procedure.registerParameter("CODESET",String.class, ParameterMode.IN);
                procedure.registerParameter("REPLACE",Short.class, ParameterMode.IN);

                log.info("Set parametrs to procedure");
                procedure.setParameter("SCHEMANAME", HibernateUtils.DERBY_USER_NAME.toUpperCase());
                procedure.setParameter("COLUMNDELIMITER",';');
                procedure.setParameter("CHARACTERDELIMITER",'#');
                procedure.setParameter("CODESET","UTF-8");
                procedure.setParameter("REPLACE",new Short("0"));

                procedure.setParameter("TABLENAME",tableName);
                procedure.setParameter("FILENAME",csvFile.getAbsolutePath());
                Transaction transaction = session.beginTransaction();
                try {
                    log.info("Import " + tableName +" from csv");
                    procedure.execute();
                    transaction.commit();
                    log.info("Import " + tableName +" success!");
                } catch (PersistenceException e){
                    log.warn("Import " + tableName + " faild!");
                    transaction.rollback();
                    e.printStackTrace();
                    throw new RuntimeException("Import " + tableName + " to offline faild!");
                }
            });
        }
    }

    private static File findFileFromList(List<File> csvFiles, String tableName){
        for(File csvFile: csvFiles){
            if(FilenameUtils.removeExtension(csvFile.getName()).equals(tableName)){
                return csvFile;
            }
        }
        return null;
    }

    public static void createUser(BigInteger id, String login,boolean isAdmin){
        try(Session session = HibernateUtils.getSession(null)){
            SysUser user = new SysUser();
            user.setId(id);
            user.setLogin(login);
            user.setIs_admin(isAdmin);
            HibernateUtils.saveEntity(session,user);
        }
    }

    public static void setPassword(BigInteger id, String pass){
        try(Session session = HibernateUtils.getSession(null)){
            SysUser user = session.get(SysUser.class, id);
            user.setPsswd(pass);
            HibernateUtils.saveEntity(session,user);
        }
    }

    public static void importCsvDataFilesTest(){
        Session session = HibernateUtils.getSession(null);
        log.warn("Obtain session");
        ProcedureCall procedure = session.createStoredProcedureCall("SYSCS_UTIL.SYSCS_IMPORT_TABLE");
        log.warn("Create procedure");
        procedure.registerParameter("SCHEMANAME",String.class, ParameterMode.IN);
        procedure.registerParameter("TABLENAME",String.class, ParameterMode.IN);
        procedure.registerParameter("FILENAME",String.class, ParameterMode.IN);
        procedure.registerParameter("COLUMNDELIMITER",Character.class, ParameterMode.IN);
        procedure.registerParameter("CHARACTERDELIMITER",Character.class, ParameterMode.IN);
        procedure.registerParameter("CODESET",String.class, ParameterMode.IN);
        procedure.registerParameter("REPLACE",Short.class, ParameterMode.IN);

        log.warn("Set parametrs to procedure");
        procedure.setParameter("SCHEMANAME", HibernateUtils.DERBY_USER_NAME.toUpperCase());
        procedure.setParameter("COLUMNDELIMITER",';');
        procedure.setParameter("CHARACTERDELIMITER",'#');
        procedure.setParameter("CODESET","UTF-8");
        procedure.setParameter("REPLACE",new Short("0"));



            procedure.setParameter("TABLENAME","ACTRESULTSAUDITDOC");
            procedure.setParameter("FILENAME","D:\\Repositaries\\bc_lite_offline\\csv_data\\ACTRESULTSAUDITDOC.csv");
                procedure.execute();

    }


}
