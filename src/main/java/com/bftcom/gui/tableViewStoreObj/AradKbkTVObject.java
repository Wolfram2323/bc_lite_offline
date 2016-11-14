package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.ARADkbk;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by k.nikitin on 11.11.2016.
 */
public class AradKbkTVObject {
    private final SimpleLongProperty id;
    private final SimpleLongProperty kbkdetail_id;
    private final SimpleStringProperty fsr_caption;
    private final SimpleDoubleProperty financeamt;
    private final SimpleDoubleProperty auditedamount;


    public  AradKbkTVObject(ARADkbk aradKbk){
        this.id = new SimpleLongProperty(aradKbk.getId().longValue());
        this.kbkdetail_id = new SimpleLongProperty(aradKbk.getKbkDetail().getId().longValue());
        this.fsr_caption = new SimpleStringProperty(aradKbk.getKbkDetail().getFsr_caption());
        this.financeamt = new SimpleDoubleProperty(aradKbk.getKbkDetail().getFinanceamt().doubleValue());
        this.auditedamount = new SimpleDoubleProperty(aradKbk.getAuditedamount().doubleValue());
    }

    public AradKbkTVObject(KbkDetailTVObject kbkDetail){
        this.id = new SimpleLongProperty();
        this.kbkdetail_id = kbkDetail.idProperty();
        this.fsr_caption = kbkDetail.fsr_captionProperty();
        this.financeamt = kbkDetail.financeamtProperty();
        this.auditedamount = new SimpleDoubleProperty();

    }


    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getKbkdetail_id() {
        return kbkdetail_id.get();
    }

    public SimpleLongProperty kbkdetail_idProperty() {
        return kbkdetail_id;
    }

    public void setKbkdetail_id(long kbkdetail_id) {
        this.kbkdetail_id.set(kbkdetail_id);
    }

    public String getFsr_caption() {
        return fsr_caption.get();
    }

    public SimpleStringProperty fsr_captionProperty() {
        return fsr_caption;
    }

    public void setFsr_caption(String fsr_caption) {
        this.fsr_caption.set(fsr_caption);
    }

    public double getFinanceamt() {
        return financeamt.get();
    }

    public SimpleDoubleProperty financeamtProperty() {
        return financeamt;
    }

    public void setFinanceamt(double financeamt) {
        this.financeamt.set(financeamt);
    }

    public double getAuditedamount() {
        return auditedamount.get();
    }

    public SimpleDoubleProperty auditedamountProperty() {
        return auditedamount;
    }

    public void setAuditedamount(double auditedamount) {
        this.auditedamount.set(auditedamount);
    }
}