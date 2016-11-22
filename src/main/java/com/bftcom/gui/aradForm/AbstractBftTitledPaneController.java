package com.bftcom.gui.aradForm;

import com.bftcom.dbtools.entity.ActResultsAuditDoc;
import javafx.scene.control.TitledPane;

/**
 * Created by k.nikitin on 07.11.2016.
 */
public abstract class AbstractBftTitledPaneController extends TitledPane {
    public abstract void initialize(ActResultsAuditDoc arad);

    public abstract void submitData(ActResultsAuditDoc arad);
}
