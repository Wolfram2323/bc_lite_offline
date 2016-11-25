package com.bftcom.gui.custom.cell;

import com.bftcom.dbtools.entity.KBKDetail;
import com.bftcom.dbtools.utils.HibernateUtils;
import com.bftcom.gui.custom.combobox.KeyValueComboBox;
import com.bftcom.gui.custom.combobox.StoreObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by k.nikitin on 24.11.2016.
 */
public class KeyValueComboBoxTableCell<S> extends TableCell<S,StoreObject> {
    private KeyValueComboBox keyValueComboBox;

    public KeyValueComboBoxTableCell(){

    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            setGraphic(keyValueComboBox);
            keyValueComboBox.requestFocus();
        }
    }

    @Override
    public void updateItem(StoreObject item, boolean empty)  {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (keyValueComboBox != null) {
                    keyValueComboBox.setValue(getItem());
                }
                setText(getItem().getCaption());
                setGraphic(keyValueComboBox);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getCaption());
        setGraphic(null);
    }

    private void createComboBoxField() {
        Session session = HibernateUtils.getCurrentSession();
        List<KBKDetail> kbkDetailList = session.createQuery("FROM KBKDetail").getResultList();
        ObservableList<StoreObject> items = FXCollections.observableArrayList();
        kbkDetailList.forEach(row -> {items.add(new StoreObject(row.getId(),row.getFsr_caption()));});
        items.add(new StoreObject(null,""));
        keyValueComboBox = new KeyValueComboBox (items);
        keyValueComboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        keyValueComboBox.focusedProperty().addListener((arg0, arg1, arg2) -> {
            if (!arg2) {
                if(keyValueComboBox.getSelectionModel().getSelectedItem() != null){
                    this.commitEdit(keyValueComboBox.getValue());
                } else {
                    this.cancelEdit();
                }


            }
        });
        keyValueComboBox.setOnAction(event -> {
            this.commitEdit(keyValueComboBox.getValue());
            event.consume();
        });
        keyValueComboBox.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                this.cancelEdit();
                t.consume();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getCaption();
    }

}
