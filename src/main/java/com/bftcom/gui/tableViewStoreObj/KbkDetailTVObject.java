package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.KBKDetail;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 13.11.2016.
 */
public class KbkDetailTVObject {
    private final SimpleLongProperty id;
    private final SimpleStringProperty fsr_caption;
    private final SimpleLongProperty fsr_id;
    private final SimpleDoubleProperty financeamt;

    public KbkDetailTVObject(KBKDetail kbkDetail){
        this.id = new SimpleLongProperty(kbkDetail.getId().longValue());
        this.fsr_caption =  new SimpleStringProperty(kbkDetail.getFsr_caption());
        this.fsr_id = new SimpleLongProperty(kbkDetail.getFsr_id());
        this.financeamt = new SimpleDoubleProperty(kbkDetail.getFinanceamt().doubleValue());
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

    public Long getFsr_id() {
        return fsr_id.get();
    }

    public SimpleLongProperty fsr_idProperty() {
        return fsr_id;
    }

    public void setFsr_id(Long fsr_id) {
        this.fsr_id.set(fsr_id);
    }

    public static List<TableColumn> createColumns(){
        List<TableColumn> result = new LinkedList<>();
        TableColumn id  = new TableColumn();
        id.setCellValueFactory(new PropertyValueFactory<KbkDetailTVObject,Long>("id"));
        id.setEditable(false);
        id.setVisible(false);
        result.add(id);
        TableColumn fsr_caption = new TableColumn("КВФО");
        fsr_caption.setCellValueFactory(new PropertyValueFactory<KbkDetailTVObject,String>("fsr_caption"));
        fsr_caption.setEditable(false);
        result.add(fsr_caption);
        TableColumn fsr_id = new TableColumn();
        fsr_id.setCellValueFactory(new PropertyValueFactory<KbkDetailTVObject, Long>("fsr_id"));
        fsr_id.setEditable(false);
        fsr_id.setVisible(false);
        result.add(fsr_id);
        TableColumn financeamt = new TableColumn("Фиансирование (руб.)");
        financeamt.setCellValueFactory(new PropertyValueFactory<KbkDetailTVObject,Double>("financeamt"));
        financeamt.setEditable(false);
        result.add(financeamt);
        return result;
    }
}
