package com.bftcom.gui.tableViewStoreObj;

import com.bftcom.dbtools.entity.Violation;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by k.nikitin on 14.11.2016.
 */
public class ViolationTVObject {
    private final SimpleLongProperty id;
    private final SimpleStringProperty caption;
    private final SimpleStringProperty code;
    private final SimpleStringProperty description;

    public ViolationTVObject(Violation viol){
        this.id = new SimpleLongProperty(viol.getId().longValue());
        this.caption = new SimpleStringProperty(viol.getCaption());
        this.code = new SimpleStringProperty(viol.getCode());
        this.description = new SimpleStringProperty(viol.getDescription());
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

    public String getCaption() {
        return caption.get();
    }

    public SimpleStringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public static List<TableColumn> createColumns() {
        List<TableColumn> result = new LinkedList<>();
        TableColumn id = new TableColumn();
        id.setCellValueFactory(new PropertyValueFactory<ViolationTVObject, Long>("id"));
        id.setEditable(false);
        id.setVisible(false);
        result.add(id);
        TableColumn code = new TableColumn("Код");
        code.setCellValueFactory(new PropertyValueFactory<ViolationTVObject,String>("code"));
        code.setEditable(false);
        result.add(code);
        TableColumn caption = new TableColumn("Наименование");
        caption.setCellValueFactory(new PropertyValueFactory<ViolationTVObject, String>("caption"));
        caption.setEditable(false);
        result.add(caption);
        TableColumn description = new TableColumn("Описание");
        description.setCellValueFactory(new PropertyValueFactory<ViolationTVObject,String>("description"));
        description.setEditable(false);
        result.add(description);


        return result;
    }
}
