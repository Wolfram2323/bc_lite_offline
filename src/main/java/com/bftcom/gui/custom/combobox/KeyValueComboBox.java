package com.bftcom.gui.custom.combobox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by k.nikitin on 24.11.2016.
 */
public class KeyValueComboBox extends ComboBox<StoreObject> {
    public KeyValueComboBox(){
        super();
        upgradeCombobox();
    }

    public KeyValueComboBox(ObservableList<StoreObject> items){
        super(items);
        upgradeCombobox();
    }

    private void upgradeCombobox(){
        this.setCellFactory(new Callback<ListView<StoreObject>, ListCell<StoreObject>>() {
            @Override
            public ListCell call(ListView<StoreObject> param) {
                final ListCell<StoreObject> cell = new ListCell<StoreObject>(){
                    @Override
                    protected void updateItem(StoreObject item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item != null){
                            setText(item.getCaption());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }
}
