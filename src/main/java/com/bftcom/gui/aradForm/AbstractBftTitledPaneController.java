package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import com.bftcom.gui.exception.ExceptionMessage;
import javafx.scene.control.TitledPane;
import org.apache.derby.iapi.util.StringUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.List;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public abstract class AbstractBftTitledPaneController extends TitledPane {
    public abstract void initialize(ActResultsAuditDoc arad);

    public abstract void submitData(ActResultsAuditDoc arad);
}
