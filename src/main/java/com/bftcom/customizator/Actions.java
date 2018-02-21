package com.bftcom.customizator;

import com.bftcom.gui.Customizable;
import com.bftcom.gui.ReportPrint;
import com.bftcom.gui.utils.PropertiesAndParameters;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


/**
 * Created by k.nikitin on 19.02.2018.
 */
public enum Actions implements BiConsumer<Object, Map<String, Object>> {
    HIDE((control, params) -> {
        Object value = params.get("value");
        if (value != null) {
            Node node = (Node) control;
            if (params.containsKey("fullHide") && Boolean.valueOf(value.toString())) {
                GridPane grid = (GridPane) node.getParent();
                Integer rowIndex = GridPane.getRowIndex(node);
                if (grid == null || rowIndex == null) {
                    return;
                }
                for (Node ch : grid.getChildren()) {
                    Integer tempIndex = GridPane.getRowIndex(ch);
                    tempIndex = tempIndex == null ? 0 : tempIndex;
                    if (tempIndex.equals(rowIndex)) {
                        ch.setVisible(!Boolean.valueOf(value.toString()));
                        ch.setManaged(!Boolean.valueOf(value.toString()));
                    }
                }
                node.setVisible(!Boolean.valueOf(value.toString()));
                node.setManaged(!Boolean.valueOf(value.toString()));
                grid.getRowConstraints().get(rowIndex).setMinHeight(0);
                grid.getRowConstraints().get(rowIndex).setPrefHeight(0);
                grid.getRowConstraints().get(rowIndex).setMaxHeight(0);
            } else {
                node.setVisible(!Boolean.valueOf(value.toString()));
            }
        }

    }),
    RENAME((control, params) -> {
        Object value = params.get("value");
        if (value != null) {
            Labeled node = (Labeled) control;
            node.setText(value.toString());
        }
    }),
    ADD_REPORT((control, params) -> {
        if(params.containsKey("caption") && params.containsKey("template")){
            MenuButton print_btn = (MenuButton) control;
            MenuItem item = new MenuItem();
            item.setText(params.get("caption").toString());
            item.getProperties().put(PropertiesAndParameters.REPORT_TEMPLATE, params.get("template").toString());
            ReportPrint controller = (ReportPrint) params.get("controller");
            item.setOnAction(controller::printDoc);
            print_btn.getItems().add(item);
        }
    }),
    WRAP_TEXT((control, params)->{
        Object value = params.get("value");
        if(value != null){
            Labeled node = (Labeled) control;
            node.setWrapText(Boolean.valueOf(value.toString()));
        }
    });


    private BiConsumer<Object, Map<String, Object>> function;

    Actions(BiConsumer<Object, Map<String, Object>> function) {
        this.function = function;
    }

    @Override
    public void accept(Object control, Map<String, Object> params) {
        this.function.accept(control, params);
    }

}
