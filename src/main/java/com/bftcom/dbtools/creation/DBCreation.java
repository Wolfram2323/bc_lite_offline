package com.bftcom.dbtools.creation;


import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.annotations.OnLineJoin;
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
        EmbeddedDriver driver = initJdbcDriver();
        try {
            log.info("DataBase creation started!");
            driver.connect(HibernateUtils.DERBY_DB_URL+";create=true;user="+HibernateUtils.DERBY_USER_NAME
                    +";password="+HibernateUtils.DERBY_PSWD, new Properties());
            log.info("Data base schema creation started!");
             Session session = HibernateUtils.getSession(true);
            session.close();

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
        annotatedClasses.stream().filter(entity-> !((Table)entity.getAnnotation(Table.class)).name().equals("SYSUSER")).parallel().forEach(entity->{
            String tableName = ((Table)entity.getAnnotation(Table.class)).name();
            OnLineJoin onLineJoin = (OnLineJoin) entity.getAnnotation(OnLineJoin.class);
            Field[] fields = entity.getDeclaredFields();
            StringBuilder select = new StringBuilder("select ");
            select.append(tableName).append(".ID, ");
            StringBuilder from = new StringBuilder();
            from.append(" from ").append(tableName);
            Map<String, String> columns = new TreeMap<>();
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
                } else if(onlineColInfo.joinAlias().isEmpty()){
                    columns.put(onlineColInfo.columnName(),tableName + '.' + onlineColInfo.columnName());
                } else {
                    columns.put(onlineColInfo.columnName(), onlineColInfo.joinAlias() + '.' + onlineColInfo.columnName());
                }
            }
            select.append(String.join(", ",columns.values()));
            if(onLineJoin!=null && !onLineJoin.sqlExpression().isEmpty()){
                from.append(((OnLineJoin)entity.getAnnotation(OnLineJoin.class)).sqlExpression());
            }
            result.put(tableName,select.append(from).toString());

        });
        return result;
    }

    public static void importCsvDataFiles(File csvFilesDir){
        Session session = HibernateUtils.getSession(true);
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


        List<File> csvFiles = (List<File>) FileUtils.listFiles(csvFilesDir, new String[] {"csv"}, false);
        csvFiles.forEach(csvFile->{
            String tableName = FilenameUtils.removeExtension(csvFile.getName());
            procedure.setParameter("TABLENAME",tableName);
            procedure.setParameter("FILENAME",csvFile.getAbsolutePath());
            Transaction transaction = session.beginTransaction();
            try {
                procedure.execute();
                transaction.commit();
            } catch (PersistenceException e){
                transaction.rollback();
            }
        });
    }


    public static void importCsvDataFilesTest(){
        Session session = HibernateUtils.getSession(true);
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
            procedure.setParameter("FILENAME","D:\\Repositaries\\bc_lite_offline\\ACTRESULTSAUDITDOC.csv");
                procedure.execute();

    }


}
