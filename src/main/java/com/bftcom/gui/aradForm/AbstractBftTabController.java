package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import javafx.scene.control.Tab;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public abstract class AbstractBftTabController extends Tab {
    public abstract void submitData(ActResultsAuditDoc arad);
}
