package com.bftcom.dbtools.utils;

import com.bftcom.context.Context;
import com.bftcom.dbtools.annotations.OnLineColumnInfo;
import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.dbtools.entity.Questions;
import com.bftcom.dbtools.entity.SysUser;
import com.bftcom.gui.utils.Message;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by k.nikitin on 27.09.2016.
 */
public class DataUploader {

    public void fullExport(BigInteger id){
        try(Session session = HibernateUtils.getSession(null)){
            ActResultsAuditDoc arad = session.get(ActResultsAuditDoc.class, id);
            Document xml = new DocumentImpl();
            Element offline = createElement(xml,"OFFLINE");
            Element doc = createChildElement(offline, "ACTRESULTSAUDITDOC");
            Field[] fields = arad.getClass().getDeclaredFields();
            fillElementByEntity(doc, fields, arad, true);
            File exportDir = createDirectory(".\\online_export");
            documentToFile(xml,new File(exportDir.getAbsolutePath()+"\\exportData.xml"), "windows-1251");
        }
    }

    public void exportForOtherOffline(){
        try(Session session = HibernateUtils.getSession(null)){
            CriteriaQuery<Questions> criteriaQuery = session.getCriteriaBuilder().createQuery(Questions.class);
            Root<Questions> root = criteriaQuery.from(Questions.class);
            criteriaQuery.select( root );
            List<Questions> questionsList = session.createQuery(criteriaQuery).getResultList();
            Document xml = new DocumentImpl();
            Element synch = createElement(xml, "SYNCHRONIZE");
            String questElName = Questions.class.getAnnotation(Table.class).name();
            Element questGroup = createChildElement(synch, questElName);
            questionsList.forEach(row -> {
                Element quest = createChildElement(questGroup, questElName);
                fillElementByEntity(quest,row.getClass().getDeclaredFields(),row, false);
            });
            SysUser sysUser = session.get(SysUser.class, Context.getCurrentContext().getUser_id());
            File exportDir = createDirectory(".\\questions_" + sysUser.getLogin());
            documentToFile(xml, new File(exportDir.getAbsolutePath()+"\\questions_" + sysUser.getLogin()+".xml"), "UTF-8");
        }
    }



    private Element createElement(Document doc, String nodeName) {
        Element result = doc.createElement(nodeName);
        doc.appendChild(result);
        return result;
    }

    private Element createChildElement(Element parent, String nodeName) {
        final Document doc = parent.getOwnerDocument();
        final Element result = doc.createElement(nodeName);
        parent.appendChild(result);
        return result;
    }



    private void fillElementByEntity( Element el,Field[] fields, Object currentObj, boolean atrNameByAnnotation) {
        for(Field field : fields){
            try{
                if(field.getName().equals("questInspectorsSet")){
                    continue;
                }
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), currentObj.getClass());
                Object value = pd.getReadMethod().invoke(currentObj);
                if(field.getType().equals(List.class)){
                    List<?> listValue = (List) value;
                    if(listValue.size() > 0){
                        String childElName = listValue.get(0).getClass().getAnnotation(Table.class).name();
                        Element childGroup = createChildElement(el, childElName);
                        listValue.forEach(row ->{
                            Element child = createChildElement(childGroup, childElName);
                            fillElementByEntity(child,row.getClass().getDeclaredFields(), row, atrNameByAnnotation);
                        });
                    }
                } else if (field.getAnnotation(JoinColumn.class)!= null && value!=null){
                    el.setAttribute(atrNameByAnnotation ? field.getAnnotation(JoinColumn.class).name() : field.getName()+"_links"
                            ,value.getClass().getDeclaredMethod("getId").invoke(value).toString());
                } else {
                    String strValue = value == null ? "" : value.toString();
                    if(field.getName().equals("id")){
                        el.setAttribute(atrNameByAnnotation ? "ID" : field.getName(), strValue);
                    } else  if(atrNameByAnnotation){
                        OnLineColumnInfo onLineColumnInfo = field.getAnnotation(OnLineColumnInfo.class);
                        if(onLineColumnInfo == null || !onLineColumnInfo.synch()){
                            continue;
                        }
                        if(onLineColumnInfo.columnName().isEmpty()){
                            el.setAttribute(field.getAnnotation(Column.class).name(), strValue );
                        } else {
                            el.setAttribute(onLineColumnInfo.columnName(), strValue);
                        }
                    }else{
                        el.setAttribute(field.getName(),strValue);
                    }
                }
            } catch (IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
                Message.throwExceptionForJavaFX(e,"Выгрузка данных завершилась с ошибкой", null, false);
                e.printStackTrace();
            }

        }
    }

    private void documentToFile(Document xml, File xmlFile, String encoding){
        try{
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();

            DOMImplementationLS impl =
                    (DOMImplementationLS)registry.getDOMImplementation("LS");
            LSOutput lsOutput = impl.createLSOutput();
            lsOutput.setEncoding(encoding);
            FileOutputStream fs = new FileOutputStream(xmlFile);
            lsOutput.setByteStream(fs);
            LSSerializer writer = impl.createLSSerializer();
            writer.write(xml,lsOutput);
            fs.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException("Output xml file not found!");
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
           throw new RuntimeException("Unable to convert data to xml!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createDirectory(String directoryName){
        File dir = new File(directoryName);
        if(dir.exists()){
            try {
                FileUtils.cleanDirectory(dir);
            } catch (IOException e) {
                throw new RuntimeException("Unable to clear export directory from old files! Please do it manually!");
            }
            return dir;
        } else if(dir.mkdir()){
            return dir;
        } else {
            throw new RuntimeException("Unable to create directory for export files!");
        }
    }
}
