package com.bftcom.dbtools.utils;

import com.bftcom.dbtools.entity.Questions;
import com.bftcom.dbtools.entity.ViolationGroup;
import com.bftcom.dbtools.entity.ViolationGroupKBK;
import org.apache.xerces.dom.AttributeMap;
import org.apache.xerces.dom.DeferredAttrImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.persistence.Table;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by k.nikitin on 28.10.2016.
 */
public class DataSynchronizer {

    private static final Logger log = LoggerFactory.getLogger(DataSynchronizer.class);

    private static Map<String, Class> entityForSynchronize = new HashMap<>();  //todo refzctoring with annotatedClass(HibernateUtils)

    static{
        entityForSynchronize.put(Questions.class.getAnnotation(Table.class).name().toUpperCase(), Questions.class);
        entityForSynchronize.put(ViolationGroup.class.getAnnotation(Table.class).name().toUpperCase(), ViolationGroup.class);
        entityForSynchronize.put(ViolationGroupKBK.class.getAnnotation(Table.class).name().toUpperCase(), ViolationGroupKBK.class);
    }

    public void synchronizeData(String xmlFileName){
        File xmlFile = new File(xmlFileName);
        if(!xmlFile.exists()){
            throw new RuntimeException("Xml file not found!");
        }
        Document xml = parseXml(xmlFile);
        Element el = (Element) xml.getFirstChild();
        if(!el.getTagName().equals("SYNCHRONIZE")){
            throw new RuntimeException("Bad xml file. Root tag name should be SYNCHRONIZE!");
        }
        String dataElName = Questions.class.getAnnotation(Table.class).name();
        Element dataBlock = (Element)el.getFirstChild();
        if(!dataBlock.getTagName().equals(dataElName)){
            throw new RuntimeException("Bad xml file. Data group tag name should be " + dataElName + "!");
        }
        try(Session session = HibernateUtils.getSession(null)){
            try{
                session.beginTransaction();
                fillEntityByElement(session, dataBlock);
                session.getTransaction().commit();
            } catch (HibernateException e){
                session.getTransaction().rollback();
            }
        }



    }

    private Document parseXml (File xmlFile) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(xmlFile);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException("Xml file parsing failed!");
        }
    }

    private void fillEntityByElement(Session session, Element dataGroupEl){
        NodeList nodeList = dataGroupEl.getChildNodes();
        for(int i = 0 ; i < nodeList.getLength(); i++){
            Element el = (Element) nodeList.item(i);
            if(entityForSynchronize.containsKey(el.getTagName().toUpperCase())){
                Class entity = entityForSynchronize.get(el.getTagName().toUpperCase());
                Object record = session.get(entity, new BigInteger(el.getAttribute("id")));
                AttributeMap attributes = (AttributeMap) el.getAttributes();
                for(int j = 0; j < attributes.getLength(); j++ ) {
                    DeferredAttrImpl attr = (DeferredAttrImpl) attributes.item(j);
                    if(record!= null && attr.getNodeName().equals("id")){
                        continue;
                    }
                    try{
                        if(record == null){
                            record = record.getClass().newInstance();
                        }
                        boolean link = attr.getNodeName().endsWith("_links");
                        String fieldName = link ? attr.getNodeName().substring(0, attr.getNodeName().indexOf("_links")) : attr.getNodeName();
                        Field field =entity.getDeclaredField(fieldName);
                        PropertyDescriptor pd = new PropertyDescriptor(fieldName,entity);
                        if(link){
                            Object linkValue = session.get(field.getType(),new BigInteger(attr.getNodeValue()));
                            pd.getWriteMethod().invoke(record, linkValue);
                        } else {
                            pd.getWriteMethod().invoke(record, attr.getNodeValue());
                        }
                        session.saveOrUpdate(record);
                    } catch (NoSuchFieldException e){
                        log.warn("Miss attribute " + attr.getNodeName() + ". Field with that name not found in dataBase entity!");
                    } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e){
                        log.warn("Miss attribute " + attr.getNodeName() + ". Setter for field with that name not found or not unavailable!");
                    } catch (InstantiationException e) {
                       log.warn("Entity object instance failed. Element has been passed.");
                    }
                }
            } else {
                log.warn("Element with tag " + el.getTagName() + "has been passed. Entity with that table name not found!" );
            }

        }
    }
}
